package graphics.d3.trazador;

import java.awt.Color;

import graphics.d3.data.Vector4;

/**
 * <h1>Camara</h1>
 * Clase que implementa el comportamiento 
 * del foco.
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Foco {
	
	/* atributos privados */
	private Vector4 posicion;
	private Color color;
	private int intensidad;
	
	/**
	 * @param posicion posicion del foco
	 * @param color color de la luz
	 * @param intensidad intensidad de la luz
	 */
	public Foco(Vector4 posicion, Color color, int intensidad){
		this.posicion = posicion;
		this.color = color;
		this.intensidad = intensidad;
	}
	
	/**
	 * @return color
	 */
	public Color getColor(){
		return color;
	}
	
	/**
	 * @return posicion
	 */
	public Vector4 getPosicion(){
		return posicion;
	}

	/**
	 * @return intensidad
	 */
	public int getIntensidad(){
		return intensidad;
	}
}
