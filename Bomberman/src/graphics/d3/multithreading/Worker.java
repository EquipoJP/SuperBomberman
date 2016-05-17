package graphics.d3.multithreading;

import graphics.d3.data.Par;
import graphics.d3.data.Rayo;
import graphics.d3.data.Vector4;
import graphics.d3.objetos.Objeto;
import graphics.d3.trazador.ColorOperations;
import graphics.d3.trazador.Foco;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Worker</h1>
 * Clase trabajador que sigue la traza de un rayo para
 * obtener el color final del pixel por el que pasa dicho
 * rayo.
 * <p>
 * Los trabajadores trabajan de forma concurrente pidiendo
 * los rayos a trazar a la escena.
 * 
 * @see graphics.d3.multithreading.Escena
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Worker implements Runnable {

	/* atributos privados */
	private int id;
	private Escena escena;

	/**
	 * @param id identificador del trabajador
	 * @param escena Escena a la que referirse para la 
	 * obtencion de datos
	 */
	public Worker(int id, Escena escena) {
		this.id = id;
		this.escena = escena;
		escena.inicializarTrabajo(id);
	}

	@Override
	/**
	 * Obtiene rayos de la escena hasta que ya no queden mas
	 */
	public void run() {
		boolean terminar = false;
		
		while (!terminar) {
			Color pixel = null;
			List<Rayo> rayos = escena.getRayo(id);
			
			/* Traza los rayos de un pixel */
			for (int k = 0; k < escena.ANTIALIASING; k++) {
				
				/* Se crea el rayo que sale del ojo hacia el pixel(j,i) */
				terminar = (rayos == null);
	
				if (rayos != null) {
					
					/* Pinta el pixel(i,j) del color devuelto por el rayo */
					Color colorPixel = trazar(rayos.get(k), 0, new ArrayList<Objeto>());
					if (pixel != null && colorPixel != null) {
						pixel = ColorOperations.addMedios(pixel, colorPixel);
					} else {
						pixel = colorPixel;
					}
				}
			}
			if (!terminar) {
				int color = pixel.getRGB();
				escena.pintarPixel(id, color);
			}				
		}
	}
	
	/**
	 * 
	 * @param rayo rayo lanzado
	 * @param rebotes numero de rebotes actuales
	 * @param refractadoItems lista de objetos que contienen al rayo
	 * @return color calculado para el punto desde el que se lanza el rayo rayo
	 */
	private Color trazar(Rayo rayo, int rebotes, ArrayList<Objeto> refractadoItems) {

		Objeto objeto = null;
		double minDistancia = Double.POSITIVE_INFINITY;
		double lambda = -1;
		Vector4 pIntersec = null;
		Vector4 pIntersecFinal = null;

		/* Para cada objeto de la escena, se intenta interseccionar */
		for (int k = 0; k < escena.objetos.size(); k++) {

			Par returned = escena.objetos.get(k).interseccion(rayo);
			if (returned != null) {

				Double landa = returned.getInterseccion();
				if (landa != null) {

					/* Se ha producido una interseccion */
					lambda = (double) landa;

					if (lambda >= 0) {

						/* Se comprueba cual es el objeto visible mas cercano */
						pIntersec = Rayo.getInterseccion(rayo, lambda);
						double distance = Vector4.distancia(rayo.getOrigen(), pIntersec);

						/* Se extrae el objeto mas cercano */
						if (distance < minDistancia) {
							objeto = returned.getObjeto();
							pIntersecFinal = pIntersec;
							minDistancia = distance;
						}
					}
				}
			}
		}

		/*
		 * pIntersecFinl contiene el punto de interseccion objeto contiene el
		 * objeto con el que se ha intersectado
		 */

		Color finalColor = Color.BLACK;
		/*
		 * Si existe al menos un objeto visible, objeto sera distinto de null, y
		 * se lanzan los rayos correspondientes
		 */
		if (objeto != null) {
			if (!objeto.estaDentro(rayo, pIntersecFinal) && escena.TERMINO_AMBIENTAL) {

				/* Termino ambiental */
				finalColor = luzAmbiental(escena.LUZ_AMBIENTAL, objeto); // ka*ia
				finalColor = ColorOperations.escalar(finalColor, objeto.getMaterial().getKd());
			}

			for (Foco f : escena.focos) {

				/*
				 * Comprueba si el objeto recibe luz en el punto de interseccion
				 */
				double epsilon = escena.EPSILON;
				Vector4 direccion = Vector4.sub(f.getPosicion(), pIntersecFinal).normalise();
				Vector4 origen = Vector4.add(pIntersecFinal, Vector4.mulEscalar(direccion, epsilon));
				Rayo sombra = new Rayo(origen, direccion);

				double maxDistancia = Vector4.distancia(sombra.getOrigen(), f.getPosicion());
				boolean shadow = false;

				for (Objeto o : escena.objetos) {
					Par returned = o.interseccionSombra(sombra);
					if (returned != null) {
						if (!returned.getObjeto().getMaterial().isTransparente()) {
							Double landa = returned.getInterseccion();
							if (landa != null) {

								Vector4 aux = Rayo.getInterseccion(sombra, landa);
								double dist = Vector4.distancia(sombra.getOrigen(), aux);
								if (dist < maxDistancia) {
									shadow = true;
									break;
								}
							}
						}
					}
				}
				if (!shadow) {
					if (!objeto.estaDentro(rayo, pIntersecFinal) && escena.TERMINO_DIFUSO) {

						/* Reflexion difusa */
						Vector4 normal = objeto.normal(pIntersecFinal, rayo);

						double angulo = Math.max(Vector4.dot(sombra.getDireccion(), normal), 0);
						Color difusa = ColorOperations.difuso(objeto, f, angulo);
						finalColor = ColorOperations.add(finalColor, difusa);
					}
					if (!objeto.estaDentro(rayo, pIntersecFinal) && escena.TERMINO_ESPECULAR) {

						/* Reflexion especular */
						Rayo especular = Rayo.rayoReflejado(sombra, objeto, pIntersecFinal, escena.EPSILON);
						Vector4 R = especular.getDireccion().normalise();
						Vector4 V = Vector4.negate(rayo.getDireccion()).normalise();

						double coseno = Vector4.dot(R, V);
						if (coseno < 0)
							coseno = 0;

						double n = objeto.getMaterial().getShiny();
						double terminoEspecular = Math.abs(Math.pow(coseno, n));

						Color specular = ColorOperations.escalar(f.getColor(), terminoEspecular);
						specular = ColorOperations.escalar(specular, objeto.getMaterial().getKs());
						finalColor = ColorOperations.add(finalColor, specular);
					}
				}
			}
		}
		return finalColor;

	}

	/**
	 * @param luzAmbiental
	 * @param objeto
	 * @return color al aplicar luz ambiental al objeto
	 */
	private Color luzAmbiental(double luzAmbiental, Objeto objeto) {
		Color c = objeto.getMaterial().getColor();
		return ColorOperations.escalar(c, luzAmbiental);
	}
	
}
