package graphics.d3.objetos;

import java.util.ArrayList;

import Jama.Matrix;
import graphics.d3.data.Par;
import graphics.d3.data.Rayo;
import graphics.d3.data.Vector4;
import graphics.d3.utils.TransformacionesAfines;

/**
 * <h1>Figura</h1>
 * Clase que implementa el comportamiento de las figuras. 
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Figura extends Objeto {

	/* Atributos privados */
	private ArrayList<Objeto> lista;
	private Vector4 lowerBound; // Tiene las minimas variables
	private Vector4 upperBound; // Tiene las maximas variables
	private Objeto lastObjetoIntersectado = null;
	private Rayo lastRayoUsado = null;

	/**
	 * Figura vacia
	 */
	public Figura() {
		lista = new ArrayList<Objeto>();
		lowerBound = null;
		upperBound = null;
		this.T = TransformacionesAfines.getIdentity();
	}

	/**
	 * Figura que contiene un objeto @param o
	 * @param o objeto a ser contenido en la figura
	 */
	public Figura(Objeto o) {
		lista = new ArrayList<Objeto>();
		lista.add(o);
		lowerBound = o.getLowerBound();
		upperBound = o.getUpperBound();
		this.T = TransformacionesAfines.getIdentity();
	}

	/**
	 * Figura que contiene una lista de objetos @param l
	 * @param l lista de objetos a ser contenidos en la figura
	 */
	public Figura(ArrayList<Objeto> l) {
		lista = l;
		this.T = TransformacionesAfines.getIdentity();
		updateBounds();
	}

	/**
	 * @param o nuevo objeto de la figura
	 */
	public void addObjeto(Objeto o) {
		lista.add(o);
		o.T = T;
		o.updateBounds();
		updateBounds();
	}

	@Override
	public Par interseccion(Rayo ray) {
		if (lista.size() == 0) {
			return null;
		} else {
			Par returned = null;
			for (Objeto o : lista) {
				Par ret = o.interseccion(ray);
				if (ret != null) {
					Double iterIntersecciong = ret.getInterseccion();
					// Comprobar si hay interseccion con ese objeto
					if (iterIntersecciong != null) {
						double iterInterseccion = (double) iterIntersecciong;
						// Comprobar si la landa es mayor o igual que 0
						if (iterInterseccion >= 0) {
							// Si no tenemos valor para devolver guardado,
							// almacenamos el nuevo
							if (returned == null) {
								returned = ret;
							} else {
								// Si tenemos valor guardado y el nuevo es menor
								// guardar el nuevo
								if (iterInterseccion < returned.getInterseccion()) {
									returned = ret;
								}
							}
						}
					}
				}
			}
			if(returned!=null){
				lastObjetoIntersectado = returned.getObjeto();
				lastRayoUsado = ray;
			}
			return returned;
		}
	}

	@Override
	public Par interseccionSombra(Rayo ray) {
		if (lista.size() == 0) {
			return null;
		} else {
			Par returned = null;
			for (Objeto o : lista) {
				Par ret = o.interseccionSombra(ray);
				if (ret != null) {
					Double iterIntersecciong = ret.getInterseccion();
					// Comprobar si hay interseccion con ese objeto
					if (iterIntersecciong != null) {
						double iterInterseccion = (double) iterIntersecciong;
						// Comprobar si la landa es mayor o igual que 0
						if (iterInterseccion >= 0) {
							// Si no tenemos valor para devolver guardado,
							// almacenamos el nuevo
							if (returned == null) {
								if(!ret.getObjeto().getMaterial().isTransparente()){
									returned = ret;
								}
							} else {
								// Si tenemos valor guardado y el nuevo es menor
								// guardar el nuevo
								if (iterInterseccion < returned.getInterseccion()) {
									if(!ret.getObjeto().getMaterial().isTransparente()){
										returned = ret;
									}
								}
							}
						}
					}
				}
			}
			if(returned!=null){
				lastObjetoIntersectado = returned.getObjeto();
				lastRayoUsado = ray;
			}
			return returned;
		}
	}

	@Override
	public Vector4 normal(Vector4 in, Rayo ray) {
		/* Comprobar si lo que nos hemos guardado nos sirve */
		if (lastRayoUsado != null && ray.equals(lastRayoUsado)) {
			// Si es el mismo rayo, podemos devolver la normal
			// del ultimo objeto que nos guardamos
			return lastObjetoIntersectado.normal(in, ray);
		} else {
			// Si no coincide pues habra que buscarlo
			if (lista.size() == 0 || ray == null) {
				return null;
			} else {
				Objeto ourObject = null;
				for (Objeto obj : lista) {
					Par p = obj.interseccion(ray);
					if (p != null) {
						Double iterInterseccion = p.getInterseccion();
						if (iterInterseccion != null) {
							if (in.equals(iterInterseccion)) {
								ourObject = p.getObjeto();
							}
						}
					}
				}
				if (ourObject == null) {
					return null;
				} else {
					return ourObject.normal(in, ray);
				}
			}
		}
	}

	@Override
	public Vector4 getLowerBound() {
		if (lowerBound == null) {
			try {
				throw new Exception("Can't get bounds from empty figure");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lowerBound;
	}

	@Override
	public Vector4 getUpperBound() {
		if (upperBound == null) {
			try {
				throw new Exception("Can't get bounds from empty figure");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return upperBound;
	}

	@Override
	public void updateBounds() {
		// Update lower bound
		double minX = Double.POSITIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double minZ = Double.POSITIVE_INFINITY;
		ArrayList<Vector4> temp = new ArrayList<Vector4>();
		for (Objeto o : lista) {
			temp.add(o.getLowerBound());
			temp.add(o.getUpperBound());
		}

		for (Vector4 e : temp) {
			if (e.getX() < minX) {
				minX = e.getX();
			}
			if (e.getY() < minY) {
				minY = e.getY();
			}
			if (e.getZ() < minZ) {
				minZ = e.getZ();
			}
		}
		lowerBound = new Vector4(minX, minY, minZ, 1);

		// Update upperBound
		double maxX = Double.NEGATIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double maxZ = Double.NEGATIVE_INFINITY;
		for (Vector4 e : temp) {
			if (e.getX() > maxX) {
				maxX = e.getX();
			}
			if (e.getY() > maxY) {
				maxY = e.getY();
			}
			if (e.getZ() > maxZ) {
				maxZ = e.getZ();
			}
		}
		upperBound = new Vector4(maxX, maxY, maxZ, 1);
	}

	@Override
	public void setT(Matrix T) {
		this.T = T;
		for (Objeto o : lista){
			o.setT(T);
		}
		updateBounds();
	}

	@Override
	public void addTransformation(Matrix m) {
		for (Objeto a : lista) {
			a.addTransformation(m);
		}
		;
	}

	@Override
	public boolean estaDentro(Rayo r, Vector4 interseccion) {
		if (lista.size() == 0 || r == null) {
			return false;
		} else {
			boolean returned = false;
			for (Objeto obj : lista) {
				if(obj instanceof Triangulo || obj instanceof Plano){
					//do nothing
				}
				else{
					Par p = obj.interseccion(r);
					if (p != null) {
						if(p.getObjeto()!=null){
							returned = p.getObjeto().estaDentro(r, interseccion);
						}
					}
				}
			}
			return returned;
		}
	}
	
	public String toString(){
		String ret = "";
		for(Objeto o : lista){
			ret = ret + o.getMaterial().getColor().getRed()+" "+o.getMaterial().getColor().getGreen()+" "+o.getMaterial().getColor().getBlue()+" ";
		}
		return ret;
	}
}
