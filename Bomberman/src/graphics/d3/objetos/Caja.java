package graphics.d3.objetos;

import java.util.ArrayList;

import Jama.Matrix;
import graphics.d3.data.Par;
import graphics.d3.data.Rayo;
import graphics.d3.data.Vector4;
import graphics.d3.utils.TransformacionesAfines;

/**
 * <h1>Caja</h1>
 * Clase que implementa el comportamiento de las cajas
 * envolventes alineadas con los ejes. 
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Caja extends Objeto {

	/* Atributos privados */
	private Vector4 lowerBound;
	private Vector4 upperBound;
	private Figura conjuntoObjetos;

	/**
	 * Caja vacia
	 */
	public Caja(){
		lowerBound = null;
		upperBound = null;
		conjuntoObjetos = new Figura();
	}
	
	/**
	 * Caja con una figura @param f
	 * @param f figura contenida en la caja
	 */
	public Caja(Figura f) {
		lowerBound = f.getLowerBound();
		upperBound = f.getUpperBound();
		conjuntoObjetos = f;
	}

	/**
	 * Caja con una lista de objetos
	 * @param l lista de objetos contenidos en la caja
	 */
	public Caja(ArrayList<Objeto> l) {
		conjuntoObjetos = new Figura(l);
		lowerBound = conjuntoObjetos.getLowerBound();
		upperBound = conjuntoObjetos.getUpperBound();
	}
	
	/**
	 * Caja con un objeto
	 * @param a objeto contenido en la caja
	 */
	public Caja(Objeto a){
		conjuntoObjetos = new Figura(a);
		lowerBound = conjuntoObjetos.getLowerBound();
		upperBound = conjuntoObjetos.getUpperBound();
	}
	
	/**
	 * @param a objeto que agregar a la caja
	 */
	public void addObjeto(Objeto a){
		conjuntoObjetos.addObjeto(a);
		lowerBound = conjuntoObjetos.getLowerBound();
		upperBound = conjuntoObjetos.getUpperBound();
	}

	@Override
	public Par interseccion(Rayo ray) {
		boolean h = false;
		Vector4 p = ray.getOrigen();
		if (estaDentroDeLaCaja(p)) {
			// Do nothing, si que intersecta
		} else {
			Vector4 m = ray.getDireccion();
			// Si cumple cualquiera de las 6 condiciones no intersecta
			// Condicion 1
			if (p.getX() < lowerBound.getX() && m.getX() <= 0) {
				h = true;
			}
			// Condicion 2
			if (p.getY() < lowerBound.getY() && m.getY() <= 0) {
				h = true;
			}
			// Condicion 3
			if (p.getZ() < lowerBound.getZ() && m.getZ() <= 0) {
				h = true;
			}
			// Condicion 4
			if (p.getX() > upperBound.getX() && m.getX() >= 0) {
				h = true;
			}
			// Condicion 5
			if (p.getY() > upperBound.getY() && m.getY() >= 0) {
				h = true;
			}
			// Condicion 6
			if (p.getZ() > upperBound.getZ() && m.getZ() >= 0) {
				h = true;
			}
		}
		if (h) {
			return null;
		} else {
			return conjuntoObjetos.interseccion(ray);
		}
	}

	@Override
	public Vector4 normal(Vector4 interseccion, Rayo ray) {
		return conjuntoObjetos.normal(interseccion, ray);
	}

	@Override
	public Par interseccionSombra(Rayo ray) {
		boolean h = false;
		Vector4 p = ray.getOrigen();
		if (estaDentroDeLaCaja(p)) {
			// Do nothing, si que intersecta
		} else {
			Vector4 m = ray.getDireccion();
			// Si cumple cualquiera de las 6 condiciones no intersecta
			// Condicion 1
			if (p.getX() < lowerBound.getX() && m.getX() <= 0) {
				h = true;
			}
			// Condicion 2
			if (p.getY() < lowerBound.getY() && m.getY() <= 0) {
				h = true;
			}
			// Condicion 3
			if (p.getZ() < lowerBound.getZ() && m.getZ() <= 0) {
				h = true;
			}
			// Condicion 4
			if (p.getX() > upperBound.getX() && m.getX() >= 0) {
				h = true;
			}
			// Condicion 5
			if (p.getY() > upperBound.getY() && m.getY() >= 0) {
				h = true;
			}
			// Condicion 6
			if (p.getZ() > upperBound.getZ() && m.getZ() >= 0) {
				h = true;
			}
		}
		if (h) {
			return null;
		} else {
			return conjuntoObjetos.interseccionSombra(ray);
		}
	}

	@Override
	public Vector4 getLowerBound() {
		return lowerBound;
	}

	@Override
	public Vector4 getUpperBound() {
		return upperBound;
	}

	/**
	 * @param p punto 3D en coordenadas homogeneas 
	 * del que se quiere comprobar si esta dentro de la caja
	 * 
	 * @return <b>true</b> si el punto esta dentro, <b>false</b>
	 * en caso contrario
	 */
	private boolean estaDentroDeLaCaja(Vector4 p) {
		boolean returned = false;
		// Check X
		if (lowerBound.getX() <= p.getX() && upperBound.getX() >= p.getX()) {
			// Check Y
			if (lowerBound.getY() <= p.getY() && upperBound.getY() >= p.getY()) {
				// Check Z
				if (lowerBound.getX() <= p.getZ() && upperBound.getZ() >= p.getZ()) {
					returned = true;
				}
			}
		}

		return returned;
	}

	@Override
	public void updateBounds() {
		conjuntoObjetos.updateBounds();
		lowerBound = conjuntoObjetos.getLowerBound();
		upperBound = conjuntoObjetos.getUpperBound();
	}
	
	@Override
	public void setT(Matrix T){
		// Don't do anything
		this.T = TransformacionesAfines.getIdentity();
	}

	@Override
	public void addTransformation(Matrix m) {
		conjuntoObjetos.addTransformation(m);
	}

	@Override
	public boolean estaDentro(Rayo r, Vector4 interseccion) {
		return conjuntoObjetos.estaDentro(r, interseccion);
	}
	
	@Override
	public Objeto clone() {
		Objeto f = conjuntoObjetos.clone();
		return new Caja(f);
	}
}
