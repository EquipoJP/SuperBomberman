package graphics.d3.objetos;

import java.util.ArrayList;

import Jama.Matrix;
import graphics.d3.data.Par;
import graphics.d3.data.Rayo;
import graphics.d3.data.Vector4;
import graphics.d3.utils.TransformacionesAfines;

/**
 * <h1>Objeto</h1>
 * Clase generica que implementa el comportamiento 
 * de los objetos.
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public abstract class Objeto {
	
	/* atributos protegidos */
	protected Material material;
	protected Matrix T = TransformacionesAfines.getIdentity();
	
	/**
	 * @return matriz de transformacion
	 */
	public Matrix getT(){
		return T;
	}
	
	/**
	 * @param T matriz de transformacion
	 */
	public void setT(Matrix T){
		this.T = T;
		updateBounds();
	}

	/**
	 * Agrega la matriz de transformacion 
	 * @param m a las ya existentes
	 * @param m matriz de transformacion
	 */
	public void addTransformation(Matrix m) {
		ArrayList<Matrix> comb = new ArrayList<Matrix>();
		comb.add(this.T);
		comb.add(m);
		this.setT(TransformacionesAfines.combine(comb));
	}
	
	/**
	 * @return material del objeto
	 */
	public Material getMaterial(){
		return material;
	}
	
	/**
	 * @param ray rayo con el que intersectar
	 * @return null si no intersecta o el par 
	 * (lambda, objeto) con el que intersecta
	 */
	public abstract Par interseccion(Rayo ray);
	
	/**
	 * @param interseccion punto 3D de la interseccion con el objeto
	 * @return normal del objeto
	 */
	public abstract Vector4 normal(Vector4 interseccion, Rayo ray);

	/**
	 * @param ray rayo con el que intersectar
	 * @return null si no intersecta o el par 
	 * (lambda, objeto) con el que intersecta
	 */
	public abstract Par interseccionSombra(Rayo ray);
	
	/**
	 * @return lower bound del objeto
	 */
	public abstract Vector4 getLowerBound();
	
	/**
	 * @return upper bound del objeto
	 */
	public abstract Vector4 getUpperBound();
	
	/**
	 * @param r rayo intersectante
	 * @param interseccion punto de interseccion
	 * @return <b>true</b> si esta dentro del objeto o
	 * <b>false</b> si no lo esta
	 */
	public abstract boolean estaDentro(Rayo r, Vector4 interseccion);
	
	/**
	 * Actualiza las bounds del objeto
	 */
	public abstract void updateBounds();
	
	public abstract Objeto clone();
}
