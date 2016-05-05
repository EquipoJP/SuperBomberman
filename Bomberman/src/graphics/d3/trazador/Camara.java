package graphics.d3.trazador;

import java.util.Random;

import graphics.d3.data.Rayo;
import graphics.d3.data.Vector4;
import graphics.d3.utils.CambioDeBase;

/**
 * <h1>Camara</h1>
 * Clase que implementa el comportamiento 
 * de la camara.
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Camara {
	
	/* atributos privados */
	private Vector4 posicion;	// posicion del ojo
	private Vector4 direccion;	// direccion del ojo
	private Vector4 origen;		//origen al que mira el ojo
	
	private Vector4 u, v, w;	// sistema de la camara
	private double f;	// separacion ojo-pantalla
	
	private int anchura, altura;	// anchura y altura de la pantalla
	
	private int filas, columnas;	// pixeles de anchura y altura

	/**
	 * @param posicion posicion inicial de la camara
	 * @param origen origen al que mira la camara
	 * @param f distancia focal
	 * @param columnas columnas
	 * @param filas filas
	 * @param anchura anchura de la pantalla
	 * @param altura altura de la pantalla
	 */
	public Camara(Vector4 posicion, Vector4 origen, double f, 
			int columnas, int filas, int anchura, int altura){
		this.posicion = posicion;
		this.origen = origen;
		this.f = f;
		
		this.filas = filas;
		this.columnas = columnas;
		
		this.anchura = anchura;
		this.altura = altura;
		
		setup();
	}
	
	/**
	 * Setea la direccion en la que mira la camara, asi como u, v y w
	 */
	private void setup(){
		Vector4 g = Vector4.sub(origen, posicion).normalise();
		this.direccion = g;
		
		/* u, v, w */
		Vector4 up = new Vector4(0, 1, 0, 0);
		
		w = Vector4.negate(g);
		u = Vector4.cross(up, w);
		u = u.normalise();
		v = Vector4.cross(w, u);
	}
	
	public void step(double movX, double movY, double movZ){
		this.posicion = Vector4.add(posicion, new Vector4(movX, movY, movZ, 0));
		setup();
	}
	
	/**
	 * @param i columna
	 * @param j fila
	 * @return el rayo que va desde el ojo al 
	 * pixel (@param i, @param j) de la pantalla
	 */
	public Rayo rayoToPixel(int i, int j){
		double diffu = (double) anchura / (double) (columnas - 1);
		double diffv = (double) altura / (double) (filas - 1);

		// antialiasing por supermestreo random
		Random r = new Random();
		double varu = r.nextDouble() - 0.5;	// [-0.5, 0.5]
		double varv = r.nextDouble() - 0.5;	// [-0.5, 0.5]
		
		Vector4 local = new Vector4(i*diffu + varu*diffu, -j*diffv + varv*diffv, -f, 1);
		Vector4 mundo = CambioDeBase.cambioDeBase(local, u, v, w, posicion);
		
		return new Rayo(mundo, Vector4.sub(mundo, posicion).normalise());
	}

	/**
	 * @return filas
	 */
	public int getRows() {
		return filas;
	}

	/**
	 * @return columas
	 */
	public int getCols() {
		return columnas;
	}

	/**
	 * @return direccion
	 */
	public Vector4 getDireccion() {
		return direccion;
	}
}