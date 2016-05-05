package graphics.d3.multithreading;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import graphics.d3.data.Rayo;
import graphics.d3.objetos.Objeto;
import graphics.d3.trazador.Camara;
import graphics.d3.trazador.Foco;
import graphics.d3.utils.XMLFormatter;

/**
 * <h1>Escena</h1>
 * Clase que controla los pixeles a pintar en la imagen para
 * proveer de concurrencia a los trabajadores
 * 
 * @see graphics.d3.multithreading.Worker
 * @see Image
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Escena {
	
	/* Imagen */
	private int i;
	private int j;
	int ANTIALIASING;

	/* Variables escena */
	int MAX_REBOTES_RAYO;
	double LUZ_AMBIENTAL;
	double EPSILON;
	boolean CAJAS_ENVOLVENTES;

	/* Contenido de la escena */
	private BufferedImage img;
	List<Objeto> objetos;
	List<Foco> focos;
	public Camara camara;

	/* FLAGS DEBUG */
	boolean TERMINO_AMBIENTAL;
	boolean TERMINO_DIFUSO;
	boolean TERMINO_ESPECULAR;
	boolean TERMINO_REFLEJADO;
	boolean TERMINO_REFRACTADO;
	
	/* Variables workers */
	List<Pixel> trabajos = new ArrayList<Pixel>();
	
	/**
	 * Inicializa los parametros de la escena, incluyendo flags, 
	 * camara y objetos de la escena
	 * 
	 * @param xml ruta del ficheor XML donde se encuentra la 
	 * informacion
	 */
	public Escena(String xml) {
		this.i = 0;
		this.j = -1;
		
		camara = XMLFormatter.getCamara(xml);
		img = new BufferedImage(camara.getCols(), camara.getRows(), 
				BufferedImage.TYPE_INT_RGB);
		
		objetos = XMLFormatter.getObjetos(xml);
		focos = XMLFormatter.getFocos(xml);
		
		CAJAS_ENVOLVENTES = XMLFormatter.setCajas(xml);
		MAX_REBOTES_RAYO = XMLFormatter.getRebotes(xml);
		LUZ_AMBIENTAL = XMLFormatter.getLuzAmbiente(xml);
		ANTIALIASING = 1;	//XMLFormatter.getAntialiasing(xml);
		
		boolean[] flags = XMLFormatter.setFlags(xml);
		if (flags != null) {
			TERMINO_AMBIENTAL = flags[0];
			TERMINO_DIFUSO = flags[1];
			TERMINO_ESPECULAR = flags[2];
			TERMINO_REFLEJADO = false;	//flags[3];
			TERMINO_REFRACTADO = false;	//flags[4];
		}
		
		EPSILON = XMLFormatter.getEpsilon(xml);
	}
	
	/**
	 * @param id identificador del trabajador
	 */
	public synchronized void inicializarTrabajo(int id) {
		Pixel pixelVacio = new Pixel(-1,-1);
		trabajos.add(id, pixelVacio);
	}
	
	/**
	 * @param id identificador del trabajador
	 * @param color color con el que pintar el pixel en 
	 * el que trabaja actualmente el trabajador
	 */
	public synchronized void pintarPixel(int id, int color){
		Pixel pixel = trabajos.get(id);
		Pixel pixelVacio = new Pixel(-1,-1);
		trabajos.set(id, pixelVacio);
		int ii = pixel.getI();
		int jj = pixel.getJ();
		img.setRGB(jj, ii, color);
	}
	
	/**
	 * @param id identificador del trabajador
	 * @return lista de rayos asignados al trabajador
	 */
	public synchronized ArrayList<Rayo> getRayo(int id){
		ArrayList<Rayo> rayos = new ArrayList<Rayo>();
		
		/* Crea el rayo que pasa por el pixel j, i */
		if (j < camara.getCols() - 1) {
			j++;
		}
		else {
			if (i < camara.getRows() - 1) {
				i++;
				j = 0;
				
				if (i% (camara.getRows() / 10) == 0){
					double percentage = (double) i / (double) camara.getRows();
					percentage = percentage * 100.0;
					System.out.printf("%.0f%% completado%n", percentage);
				}
			}
			else {
				return null;
			}
		}
		
		int ii = i - camara.getRows() / 2;
		int jj = j - camara.getCols() / 2;
		
		/* Genera el rayo y actualiza el estado de los trabajadores */
		for (int k = 0; k < ANTIALIASING; k++) {
			rayos.add(camara.rayoToPixel(jj, ii));
		}

		Pixel workingPixel = new Pixel(i, j);
		trabajos.set(id, workingPixel);
		
		return rayos;
	}
	
	/**
	 * @return imagen
	 */
	public BufferedImage getImg(){
		return img;
	}
}
