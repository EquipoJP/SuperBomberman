package graphics.d3.objetos;

import Jama.Matrix;
import graphics.d3.data.Par;
import graphics.d3.data.Rayo;
import graphics.d3.data.Vector4;
import graphics.d3.utils.TransformacionesAfines;

/**
 * <h1>Esfera</h1>
 * Clase que implementa el comportamiento de las esferas. 
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Esfera extends Objeto {

	/* Atributos privados */
	private Vector4 centro;
	private double radio;
	private Vector4 lowerBound;
	private Vector4 upperBound;

	/**
	 * @param material material de la esfera
	 * @param radio radio de la esfera
	 */
	public Esfera(Material m, double radio) {
		this.centro = new Vector4(0, 0, 0, 1);
		this.radio = radio;
		super.material = m;
		updateBounds();
	}

	/**
	 * @param m material de la esfera
	 * @param T matriz de transformaciones de la esfera
	 * @param radio radio de la esfera
	 */
	public Esfera(Material m, Matrix T, double radio) {
		this.centro = new Vector4(0, 0, 0, 1);
		this.centro = TransformacionesAfines.multiplyVectorByMatrix(this.centro, T);
		this.radio = radio;
		
		super.material = m;
		super.T = T;
		updateBounds();
	}

	@Override
	public Par interseccion(Rayo ray) {
		return interseccionSombra(ray);
	}

	@Override
	public Par interseccionSombra(Rayo ray) {

		double A = Vector4.dot(ray.getDireccion(), ray.getDireccion()); // A = d.d
		double B = 2 * Vector4.dot(Vector4.sub(ray.getOrigen(), this.centro), 
				ray.getDireccion()); // B
		double C = Vector4.dot(Vector4.sub(ray.getOrigen(), this.centro), 
				Vector4.sub(ray.getOrigen(), this.centro))
				- Math.pow(this.radio, 2); // C = (a-c).(a-c) - r^2
		double D = Math.pow(B, 2) - 4 * A * C; // D = B^2 - AC

		if (D < 0) {
			return null;
		} else {
			double lambda1, lambda2;
			// l = [-B +- raiz(B^2-4AC)] / [2A]
			lambda1 = (-B + Math.pow(Math.pow(B, 2) - 4 * A * C, 0.5)) / (2 * A);
			lambda2 = (-B - Math.pow(Math.pow(B, 2) - 4 * A * C, 0.5)) / (2 * A);

			double min = Math.min(lambda1, lambda2);
			double max = Math.max(lambda1, lambda2);

			if (min < 0 && max >= 0)
				return new Par(max, this);
			else if (min < 0 && max < 0)
				return null;
			else
				return new Par(min, this);
		}
	}

	/**
	 * @param interseccion punto 3D de interseccion 
	 * en coordenadas homogeneas
	 * @return un vector en coordenadas homogeneas que 
	 * representa la normal en el punto de interseccion 
	 */
	public Vector4 normal(Vector4 interseccion) {
		return Vector4.sub(interseccion, centro).normalise();
	}

	@Override
	public Vector4 normal(Vector4 interseccion, Rayo ray) {
		return normal(interseccion);
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
		lowerBound = new Vector4(centro.getX() - radio, centro.getY() - radio, centro.getZ() - radio, 1);
		upperBound = new Vector4(centro.getX() + radio, centro.getY() + radio, centro.getZ() + radio, 1);
	}

	@Override
	public boolean estaDentro(Rayo r, Vector4 interseccion) {
		Vector4 normal = this.normal(interseccion);
		return Vector4.dot(normal, r.getDireccion()) >= 0;
	}
	
	@Override
	public Objeto clone() {
		Matrix tt = T.copy();
		Material m = new Material(material.getColor(), material.getKd(), material.getKs(), material.getKr(), material.getKt(), material.getIr(), material.getShiny());
		
		return new Esfera(m, tt, radio);
	}
}
