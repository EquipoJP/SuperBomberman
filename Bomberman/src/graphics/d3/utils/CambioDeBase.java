package graphics.d3.utils;

import Jama.Matrix;
import graphics.d3.data.Vector4;

/**
 * <h1>CambioDeBase</h1>
 * Clase para realizar el cambio de base
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class CambioDeBase {

	/**
	 * @param transformar vector a transformar
	 * @param u sistema de ejes de la camara
	 * @param v sistema de ejes de la camara
	 * @param w sistema de ejes de la camara
	 * @param ojo posicion de la camara
	 * @return vector @param transformar en coordenadas del mundo
	 */
	public static Vector4 cambioDeBase(Vector4 transformar, 
			Vector4 u, Vector4 v, Vector4 w, Vector4 ojo) {
		double[][] matriz = {
				{u.getX(), u.getY(), u.getZ(), 0},
				{v.getX(), v.getY(), v.getZ(), 0},
				{w.getX(), w.getY(), w.getZ(), 0},
				{ojo.getX(), ojo.getY(), ojo.getZ(), 1}
		};
		
		double[][] vector = {
				{transformar.getX(), transformar.getY(), transformar.getZ(), transformar.getH()}
		};
		
		Matrix m = new Matrix(matriz);
		Matrix vm = new Matrix(vector);
		
		Matrix res = vm.times(m);	//vm * m
		
		double x = res.get(0, 0);
		double y = res.get(0, 1);
		double z = res.get(0, 2);
		int h = (int) res.get(0, 3);
		
		return new Vector4(x, y, z, h);
	}

}
