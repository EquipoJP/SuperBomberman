package graphics.d3.objetos;

import java.util.ArrayList;

import Jama.Matrix;
import graphics.d3.data.Par;
import graphics.d3.data.Rayo;
import graphics.d3.data.Vector4;
import graphics.d3.utils.TransformacionesAfines;

/**
 * <h1>Plano</h1>
 * Clase que implementa el comportamiento 
 * de los planos.
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Plano extends Objeto {

	/* Atributos de la clase plano */
	private Vector4 normal;
	private Vector4 p1; // top left corner
	private Vector4 p2; // top right corner
	private Vector4 p3; // bottom left corner
	private Vector4 p4; // bottom right corner
	private Vector4 lowerBound;
	private Vector4 upperBound;

	/**
	 * Los cuatro puntos del plano se encuentran definidos 
	 * en sentido contrario a las agujas del reloj.
	 * 
	 * @param p1 punto 1 del plano
	 * @param p2 punto 2 del plano
	 * @param p3 punto 3 del plano
	 * @param p4 punto 4 del plano
	 * @param m material del plano
	 * @param T matriz de transformaciones
	 */
	public Plano(Vector4 p1, Vector4 p2, Vector4 p3, Vector4 p4, Material m, Matrix T) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.normal = normal();
		super.T = T;
		super.material = m;
		
		updateBounds();
	}

	/**
	 * Los cuatro puntos del plano se encuentran definidos 
	 * como (arriba-izquierda, arriba-derecha, abajo-izquierda, abajo-derecha)
	 * 
	 * @param p1 punto 1 del plano
	 * @param p2 punto 2 del plano
	 * @param p3 punto 3 del plano
	 * @param p4 punto 4 del plano
	 * @param m material del plano
	 */
	public Plano(Vector4 p1, Vector4 p2, Vector4 p3, Vector4 p4, Material m) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.normal = normal();
		super.material = m;
		
		updateBounds();
	}

	@Override
	public Par interseccion(Rayo ray) {
		Vector4 d = ray.getDireccion();
		Vector4 n = normal;
		Vector4 a = ray.getOrigen();

		double numerador = Vector4.dot(Vector4.sub(p1, a), n); // (p1 - a) * n
		double denominador = Vector4.dot(d, n); // (d * n)
		Double lambda = null;

		if (denominador == 0) {
			// no hay interseccion
			lambda = null;
		} else {
			// hay interseccion

			/*
			 * Comprueba si la interseccion esta dentro de los limites del plano
			 * (cuadrado)
			 */
			Vector4 pI = Rayo.getInterseccion(ray, numerador / denominador);

			double s1 = Vector4.dot(Vector4.cross(Vector4.sub(p3, p1), Vector4.sub(pI, p1)), n);
			double s2 = Vector4.dot(Vector4.cross(Vector4.sub(p1, p2), Vector4.sub(pI, p2)), n);
			double s3 = Vector4.dot(Vector4.cross(Vector4.sub(p4, p3), Vector4.sub(pI, p3)), n);
			double s4 = Vector4.dot(Vector4.cross(Vector4.sub(p2, p4), Vector4.sub(pI, p4)), n);

			if (Math.signum(s1) == Math.signum(s2) && Math.signum(s1) == Math.signum(s3)
					&& Math.signum(s1) == Math.signum(s4)) {

				/*
				 * Calcula lambda solo si la interseccion esta dentro del plano
				 */
				lambda = numerador / denominador;
			}
		}

		return new Par(lambda,this);
	}

	@Override
	public Par interseccionSombra(Rayo ray) {
		Vector4 d = ray.getDireccion();
		Vector4 n = normal;
		Vector4 a = ray.getOrigen();

		double numerador = Vector4.dot(Vector4.sub(p1, a), n); // (p1 - a) * n
		double denominador = Vector4.dot(d, n); // (d * n)
		Double lambda = null;

		if (denominador == 0) {
			// no hay interseccion
			lambda = null;
		} else {
			// hay interseccion

			/*
			 * Comprueba si la interseccion esta dentro de los limites del plano
			 * (cuadrado)
			 */
			Vector4 pI = Rayo.getInterseccion(ray, numerador / denominador);

			double s1 = Vector4.dot(Vector4.cross(Vector4.sub(p3, p1), Vector4.sub(pI, p1)), n);
			double s2 = Vector4.dot(Vector4.cross(Vector4.sub(p1, p2), Vector4.sub(pI, p2)), n);
			double s3 = Vector4.dot(Vector4.cross(Vector4.sub(p4, p3), Vector4.sub(pI, p3)), n);
			double s4 = Vector4.dot(Vector4.cross(Vector4.sub(p2, p4), Vector4.sub(pI, p4)), n);

			if (Math.signum(s1) == Math.signum(s2) && Math.signum(s1) == Math.signum(s3)
					&& Math.signum(s1) == Math.signum(s4)) {

				/*
				 * Calcula lambda solo si la interseccion esta dentro del plano
				 */
				lambda = numerador / denominador;

				if (lambda < 0) {
					return null;
				}
			}
		}

		return new Par(lambda,this);
	}

	/**
	 * @return normal del plano
	 */
	public Vector4 normal() {
		Vector4 term1 = Vector4.sub(p2, p1);
		Vector4 term2 = Vector4.sub(p3, p1);
		return Vector4.cross(term1, term2).normalise();
	}

	@Override
	public Vector4 normal(Vector4 interseccion, Rayo ray) {
		double res = Vector4.dot(ray.getDireccion(), normal);
		if (res < 0) {
			return normal;
		} else {
			return Vector4.negate(normal);
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
	
	@Override
	public boolean estaDentro(Rayo r, Vector4 interseccion){
		return false;
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
		this.p4 = TransformacionesAfines.multiplyVectorByMatrix(p4, T);
		
		temp.add(this.p1);
		temp.add(this.p2);
		temp.add(this.p3);
		temp.add(this.p4);
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
	public Objeto clone() {
		Matrix tt = T.copy();
		Material m = new Material(material.getColor(), material.getKd(), material.getKs(), material.getKr(), material.getKt(), material.getIr(), material.getShiny());
		Vector4 p11 = new Vector4(p1.getX(), p1.getY(), p1.getZ(), p1.getH());
		Vector4 p21 = new Vector4(p2.getX(), p2.getY(), p2.getZ(), p2.getH());
		Vector4 p31 = new Vector4(p3.getX(), p3.getY(), p3.getZ(), p3.getH());
		Vector4 p41 = new Vector4(p4.getX(), p4.getY(), p4.getZ(), p4.getH());
		
		return new Plano(p11, p21, p31, p41, m, tt);
	}

}
