package graphics.d3.objetos;

import java.util.ArrayList;

import Jama.Matrix;
import graphics.d3.data.Par;
import graphics.d3.data.Rayo;
import graphics.d3.data.Vector4;
import graphics.d3.utils.TransformacionesAfines;

/**
 * <h1>Triangulo</h1>
 * Clase que implementa el comportamiento 
 * de los triangulos.
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Triangulo extends Objeto {

	/* Atributos privados */
	private Vector4 p1;
	private Vector4 p2;
	private Vector4 p3;
	private Vector4 lowerBound;
	private Vector4 upperBound;
	
	/**
	 * Los puntos estan definidos en sentido antihorario
	 * @param p1 punto 1
	 * @param p2 punto 2
	 * @param p3 punto 3
	 * @param m material
	 * @param T matriz de transformaciones
	 */
	public Triangulo(Vector4 p1, Vector4 p2, Vector4 p3, Material m, Matrix T) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		super.T = T;
		// Update material
		super.material = m;
		updateBounds();
	}

	/**
	 * Los puntos estan definidos en sentido antihorario
	 * @param p1 punto 1
	 * @param p2 punto 2
	 * @param p3 punto 3
	 * @param m material
	 */
	public Triangulo(Vector4 p1, Vector4 p2, Vector4 p3, Material m) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		// Update material
		super.material = m;
		
		updateBounds();
	}

	@Override
	public Par interseccion(Rayo ray) {
		Vector4 a = ray.getOrigen();
		Vector4 d = ray.getDireccion();
		Vector4 n = normal();
		Double lambda = null;

		double denominador = Vector4.dot(d, n);

		/* Comprobacion de si intersecciona */
		if (denominador == 0) {
			/* No intersecciona o no se ve */
			lambda = null;
		} else {
			/* Si que intersecciona */

			double numerador = Vector4.dot(Vector4.sub(p1, a), n);

			/* Interseccion */
			Vector4 p = Rayo.getInterseccion(ray, numerador / denominador);

			/*
			 * Comprobacion de que el punto de interseccion cae dentro del
			 * triangulo
			 */
			double s1 = Vector4.dot(Vector4.cross(Vector4.sub(p2, p1), Vector4.sub(p, p1)), n);
			double s2 = Vector4.dot(Vector4.cross(Vector4.sub(p3, p2), Vector4.sub(p, p2)), n);
			double s3 = Vector4.dot(Vector4.cross(Vector4.sub(p1, p3), Vector4.sub(p, p3)), n);

			/* Comprobar que las tres 's' tienen el mismo signo */
			if (Math.signum(s1) == Math.signum(s2) && Math.signum(s1) == Math.signum(s3)) {
				lambda = numerador / denominador;
			}

		}
		return new Par(lambda,this);
	}

	@Override
	public Par interseccionSombra(Rayo ray) {
		Vector4 a = ray.getOrigen();
		Vector4 d = ray.getDireccion();
		Vector4 n = normal();
		Double lambda = null;

		double denominador = Vector4.dot(d, n);

		/* Comprobacion de si intersecciona */
		if (denominador == 0) {
			/* No intersecciona o no se ve */
			lambda = null;
		} else {
			/* Si que intersecciona */

			double numerador = Vector4.dot(Vector4.sub(p1, a), n);

			/* Interseccion */
			Vector4 p = Rayo.getInterseccion(ray, numerador / denominador);

			/*
			 * Comprobacion de que el punto de interseccion cae dentro del
			 * triangulo
			 */
			double s1 = Vector4.dot(Vector4.cross(Vector4.sub(p2, p1), Vector4.sub(p, p1)), n);
			double s2 = Vector4.dot(Vector4.cross(Vector4.sub(p3, p2), Vector4.sub(p, p2)), n);
			double s3 = Vector4.dot(Vector4.cross(Vector4.sub(p1, p3), Vector4.sub(p, p3)), n);

			/* Comprobar que las tres 's' tienen el mismo signo */
			if (Math.signum(s1) == Math.signum(s2) && Math.signum(s1) == Math.signum(s3)) {
				lambda = numerador / denominador;

				if (lambda < 0) {
					return null;
				}

			}

		}

		return new Par(lambda,this);
	}

	/**
	 * @return normal del triangulo
	 */
	public Vector4 normal() {
		Vector4 term1 = Vector4.sub(p2, p1);
		Vector4 term2 = Vector4.sub(p3, p1);
		return Vector4.cross(term1, term2).normalise();
	}

	@Override
	public Vector4 normal(Vector4 interseccion, Rayo ray) {
		return normal();
	}

	@Override
	public Vector4 getLowerBound() {
		return lowerBound;
	}

	@Override
	public Vector4 getUpperBound() {
		return upperBound;
	}

	@Override
	public void updateBounds() {
		// Update lower bound
		double minX = Double.POSITIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double minZ = Double.POSITIVE_INFINITY;
		ArrayList<Vector4> temp = new ArrayList<Vector4>();
		
		this.p1 = TransformacionesAfines.multiplyVectorByMatrix(p1, T);
		this.p2 = TransformacionesAfines.multiplyVectorByMatrix(p2, T);
		this.p3 = TransformacionesAfines.multiplyVectorByMatrix(p3, T);
		
		temp.add(this.p1);
		temp.add(this.p2);
		temp.add(this.p3);
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
	public boolean estaDentro(Rayo r, Vector4 interseccion){
		return false;
	}
}
