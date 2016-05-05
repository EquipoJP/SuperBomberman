package graphics.d3.data;

import Jama.Matrix;

/**
 * <h1>Vector4</h1>
 * Estructura de datos que representa un <b>vector 3D</b> 
 * en coordenadas homogeneas, asi como las operaciones
 * asociadas a dichos vectores.
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Vector4 {

	/* Atributos de la clase PuntoVectorH */
	private double x;
	private double y;
	private double z;
	private int h; // h == 1 -> punto, h == 0 -> vector

	/**
	 * @return un Vector4 que representa un vector
	 * con coordendas (x=0, y=0, z=0)
	 */
	public Vector4() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.h = 0;
	}

	/**
	 * @param x coordenada X
	 * @param y coordenada Y
	 * @param z coordenada Z
	 * @param h indica si es punto (@param h = 1) o
	 * si es vector (@param h = 0)
	 */
	public Vector4(double x, double y, double z, int h) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.h = h;
	}

	/**
	 * @return coordenada X
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x coordenada X
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return coordenada Y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y coordenada Y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return coordenada Z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @param z coordenada Z
	 */
	public void setZ(double z) {
		this.z = z;
	}
	
	/**
	 * @return 1 si es punto, 0 si es vector
	 */
	public int getH() {
		return h;
	}

	/**
	 * @param h indica si es vector o punto
	 */
	public void setZ(int h) {
		this.h = h;
	}

	/**
	 * @param v primer vector|punto a sumar
	 * @param w segundo vector|punto a sumar
	 * @return un nuevo vector|punto suma de ambos
	 */
	public static Vector4 add(Vector4 v, Vector4 w) {
		double x = v.x + w.x;
		double y = v.y + w.y;
		double z = v.z + w.z;
		int h = 0;

		if ((esVector(v) && esPunto(w)) || (esPunto(v) && esVector(w))) {
			h = 1;
		} else if (esPunto(v) && esPunto(w)) {
			h = 1;
		}
		return new Vector4(x, y, z, h);
	}

	/**
	 * @param v vector|punto del que restar
	 * @param w vector|punto que resta
	 * @return @param v - @param w
	 */
	public static Vector4 sub(Vector4 v, Vector4 w) {
		double x = v.x - w.x;
		double y = v.y - w.y;
		double z = v.z - w.z;
		int h = 0;

		return new Vector4(x, y, z, h);
	}

	/**
	 * @param v vector|punto
	 * @param escalar numero
	 * @return @param v * @param escalar
	 */
	public static Vector4 mulEscalar(Vector4 v, double escalar) {
		return new Vector4(v.x * escalar, v.y * escalar, v.z * escalar, v.h);
	}

	/**
	 * @param v origen
	 * @param w fin
	 * @return distancia de origen (@param v) a fin (@param w)
	 */
	public static double distancia(Vector4 v, Vector4 w) {
		return Vector4.modulo(Vector4.sub(w, v));
	}

	/**
	 * @return raiz(x^2 + y^2 + z^2)
	 */
	public static double modulo(Vector4 v) {
		return (Math.pow((Math.pow(v.x, 2) + Math.pow(v.y, 2) + Math.pow(v.z, 2)), 0.5));
	}

	/**
	 * Producto escalar
	 * 
	 * @return producto escalar de @param v y @param w
	 */
	public static double dot(Vector4 v, Vector4 w) {
		return (v.x * w.x + v.y * w.y + v.z * w.z);
	}

	/**
	 * Producto vectorial
	 * 
	 * @return @param v x @param w
	 */
	public static Vector4 cross(Vector4 v, Vector4 w) {
		return new Vector4(v.y * w.z - v.z * w.y, 
						w.x * v.z - v.x * w.z, 
						v.x * w.y - v.y * w.x, 
						0);
	}

	/**
	 * @return -@param v
	 */
	public static Vector4 negate(Vector4 v) {
		return new Vector4(-v.x, -v.y, -v.z, v.h);
	}

	/**
	 * @return @param v / @param k
	 */
	public static Vector4 div(Vector4 v, double k) {
		return new Vector4(v.x / k, v.y / k, v.z / k, v.h);
	}

	/**
	 * @return normaL2 del vector
	 */
	public double normaL2() {
		return Vector4.modulo(this);
	}

	/**
	 * @return vector4 normalizado
	 */
	public Vector4 normalise() {
		return Vector4.div(this, this.normaL2());
	}

	/**
	 * @return el angulo entre @param v y @param w
	 */
	public static double angulo(Vector4 v, Vector4 w) {
		return Math.acos((Vector4.dot(v, w) / (Vector4.modulo(v) * Vector4.modulo(w))));
	}

	/**
	 * @return <b>true</b> si @param v es vector
	 */
	public static boolean esVector(Vector4 v) {
		return v.h == 0;
	}

	/**
	 * @return <b>true</b> si @param v es un punto
	 */
	public static boolean esPunto(Vector4 v) {
		return !esVector(v);
	}

	/**
	 * @param m matriz que transformar en vector4
	 * @return Vector4 de una matriz
	 */
	public static Vector4 matrixToVector4(Matrix m){
		return new Vector4(m.get(0, 0),m.get(0, 1),m.get(0, 2),(int)(m.get(0, 3)));
	}
	
	@Override
	public String toString() {
		return this.x + " " + this.y + " " + this.z + " " + this.h;
	}
}
