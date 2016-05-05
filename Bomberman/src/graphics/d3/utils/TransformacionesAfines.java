package graphics.d3.utils;

import java.util.ArrayList;

import Jama.Matrix;
import graphics.d3.data.Vector4;

/**
 * <h1>TransformacionesAfines</h1>
 * Clase para realizar las transformaciones afines
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class TransformacionesAfines {

	/* atributos */
	public static final double DEFAULT_TRASLATION = 0;
	public static final double DEFAULT_SCALE = 1;
	public static final boolean DEFAULT_SYMMETRY = false;
	public static final double DEFAULT_ROTATION = 0;
	public static final double DEFAULT_SHEAR = 0;

	/**
	 * @param v vector
	 * @param m matriz
	 * @return el resultado de multiplicar el 
	 * vector @param v por la matriz @param m
	 */
	public static Vector4 multiplyVectorByMatrix(Vector4 v, Matrix m) {
		double[][] t = { { v.getX(), v.getY(), v.getZ(), v.getH() } };
		Matrix temp = new Matrix(t);
		Matrix c = temp.times(m);
		return Vector4.matrixToVector4(c);
	}

	/**
	 * @param matArray lista de matrices
	 * @return combinacion de las matrices
	 */
	public static Matrix combine(ArrayList<Matrix> matArray) {
		Matrix returned = null;
		if (matArray.size() >= 2) {
			returned = matArray.get(0);
			for (int i = 1; i < matArray.size(); i++) {
				returned = returned.times(matArray.get(i));
			}
		} else {
			try {
				throw new Exception("There must be at least two matrices in matArray!");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		return returned;
	}

	/**
	 * @param xtraslation traslacion en el eje X (default 0)
	 * @param ytraslation tralascion en el eje Y (default 0)
	 * @param ztraslation traslacion en el eje Z (default 0)
	 * @param xscale escalado en el eje X (default 1)
	 * @param yscale escalado en el eje Y (default 1)
	 * @param zscale escalado en el eje Z (default 1)
	 * @param gscale escalado global (default 1)
	 * @param xsym simetria en el eje X (default false)
	 * @param ysym simetria en el eje Y (default false)
	 * @param zsym simetria en el eje Z (default false)
	 * @param xrot rotacion en el eje X (default 0)
	 * @param yrot rotacion en el eje Y (default 0)
	 * @param zrot rotacion en el eje Z (default 0)
	 * @param xshear cizalla en el eje X (default 0)
	 * @param yshear cizalla en el eje Y (default 0)
	 * @param zshear cizalla en el eje Z (default 0)
	 * @return matriz de transformaciones generica
	 */
	public static Matrix affineMatrix(double xtraslation, double ytraslation, double ztraslation, 
			double xscale, double yscale, double zscale, 
			double gscale, 
			boolean xsym, boolean ysym, boolean zsym, 
			double xrot, double yrot, double zrot, 
			double xshear, double yshear, double zshear) {
		ArrayList<Matrix> total = new ArrayList<Matrix>();

		// Add traslation matrix
		total.add(getGeneralTraslation(xtraslation, ytraslation, ztraslation));

		// Add scale matrix
		if (xscale > 0 || yscale > 0 || zscale > 0) {
			total.add(getGeneralScale(xscale, yscale, zscale));
		}

		// Add global scale matrix
		if (gscale > 0) {
			total.add(getGlobalScale(gscale));
		}

		// Add symmetric matrices
		if (xsym) {
			if (ysym) {
				if (zsym) {
					// Symmetry over the origin
					total.add(getOriginSymmetry());
				} else {
					// Symmetry over XY plane
					total.add(getXYSymmetry());
				}
			} else if (zsym) {
				// Symmetry over XZ plane
				total.add(getXZSymmetry());
			} else {
				// Symmetry over X axis
				total.add(getXSymmetry());
			}
		} else if (ysym) {
			if (zsym) {
				// Symmetry over YZ axis
				total.add(getYZSymmetry());
			} else {
				// Symmetry over Y axis
				total.add(getYSymmetry());
			}
		} else if (zsym) {
			// Symmetry over Z axis
			total.add(getZSymmetry());
		} else {
			// Do nothing buddy
		}

		// Add rotation matrices
		total.add(getXRotation(xrot));
		total.add(getYRotation(yrot));
		total.add(getZRotation(zrot));

		// Add shear matrices
		total.add(getGeneralShear(xshear, yshear, zshear));

		// Return value
		return combine(total);
	}

	/**
	 * @param xtraslation traslacion en el eje X (default 0)
	 * @param ytraslation tralascion en el eje Y (default 0)
	 * @param ztraslation traslacion en el eje Z (default 0)
	 * @param xscale escalado en el eje X (default 1)
	 * @param yscale escalado en el eje Y (default 1)
	 * @param zscale escalado en el eje Z (default 1)
	 * @param gscale escalado global (default 1)
	 * @param xsym simetria en el eje X (default false)
	 * @param ysym simetria en el eje Y (default false)
	 * @param zsym simetria en el eje Z (default false)
	 * @param xrot rotacion en el eje X (default 0)
	 * @param yrot rotacion en el eje Y (default 0)
	 * @param zrot rotacion en el eje Z (default 0)
	 * @param xshear cizalla en el eje X (default 0)
	 * @param yshear cizalla en el eje Y (default 0)
	 * @param zshear cizalla en el eje Z (default 0)
	 * @param order orden de aplicacion de las matrices
	 * @return matriz de transformaciones generica
	 */
	public static Matrix affineMatrix(double xtraslation, double ytraslation, double ztraslation, 
			double xscale, double yscale, double zscale, 
			double gscale, 
			boolean xsym, boolean ysym, boolean zsym, 
			double xrot, double yrot, double zrot, 
			double xshear, double yshear, double zshear, 
			int order) {

		ArrayList<Matrix> total = new ArrayList<Matrix>();

		// Add traslation matrix - 1
		total.add(getGeneralTraslation(xtraslation, ytraslation, ztraslation));

		// Add scale matrix - 2
		total.add(getGeneralScale(xscale, yscale, zscale));

		// Add global scale matrix - 3
		total.add(getGlobalScale(gscale));

		// Add symmetric matrices - 4
		if (xsym) {
			if (ysym) {
				if (zsym) {
					// Symmetry over the origin
					total.add(getOriginSymmetry());
				} else {
					// Symmetry over XY plane
					total.add(getXYSymmetry());
				}
			} else if (zsym) {
				// Symmetry over XZ plane
				total.add(getXZSymmetry());
			} else {
				// Symmetry over X axis
				total.add(getXSymmetry());
			}
		} else if (ysym) {
			if (zsym) {
				// Symmetry over YZ axis
				total.add(getYZSymmetry());
			} else {
				// Symmetry over Y axis
				total.add(getYSymmetry());
			}
		} else if (zsym) {
			// Symmetry over Z axis
			total.add(getZSymmetry());
		} else {
			// Do nothing buddy
			total.add(getIdentity());
		}

		// Add rotation matrices - 5, 6, 7
		total.add(getXRotation(xrot));
		total.add(getYRotation(yrot));
		total.add(getZRotation(zrot));

		// Add shear matrices - 8
		total.add(getGeneralShear(xshear, yshear, zshear));

		ArrayList<Matrix> total2 = new ArrayList<Matrix>();
		total2.addAll(total);

		int end = 7;
		while (end >= 0) {
			int temp = order % 10;
			total2.set(end, total.get(temp-1));
			end--;
			order = order/10;
		}
		// Return value
		return combine(total2);
	}

	/**
	 * @return matriz identidad
	 */
	public static Matrix getIdentity() {
		double[][] values = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param x traslacion en el eje X
	 * @return matriz de transformaciones
	 */
	public static Matrix getXTraslation(double x) {
		double[][] values = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { x, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param y traslacion en el eje Y
	 * @return matriz de transformaciones
	 */
	public static Matrix getYTraslation(double y) {
		double[][] values = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, y, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param z traslacion en el eje Z
	 * @return matriz de transformaciones
	 */
	public static Matrix getZTraslation(double z) {
		double[][] values = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, z, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param x traslacion en el eje X
	 * @param y tralascion en el eje Y
	 * @param z traslacion en el eje Z
	 * @return matriz de transformaciones
	 */
	public static Matrix getGeneralTraslation(double x, double y, double z) {
		double[][] values = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { x, y, z, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param k traslacion
	 * @return matriz de transformaciones
	 */
	public static Matrix getGeneralTraslation(double k) {
		double[][] values = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { k, k, k, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param a escalado en X
	 * @return matriz de transformaciones
	 */
	public static Matrix getXScale(double a) {
		double[][] values = { { a, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param b escalado en Y
	 * @return matriz de transformaciones
	 */
	public static Matrix getYScale(double b) {
		double[][] values = { { 1, 0, 0, 0 }, { 0, b, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param c escalado en Z
	 * @return matriz de transformaciones
	 */
	public static Matrix getZScale(double c) {
		double[][] values = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, c, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param a escalado en X
	 * @param b escalado en Y
	 * @param c escalado en Z
	 * @return matriz de transformaciones
	 */
	public static Matrix getGeneralScale(double a, double b, double c) {
		double[][] values = { { a, 0, 0, 0 }, { 0, b, 0, 0 }, { 0, 0, c, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param k escalado
	 * @return matriz de transformaciones
	 */
	public static Matrix getGeneralScale(double k) {
		double[][] values = { { k, 0, 0, 0 }, { 0, k, 0, 0 }, { 0, 0, k, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @return matriz de transformaciones
	 */
	public static Matrix getXSymmetry() {
		double[][] values = { { 1, 0, 0, 0 }, { 0, -1, 0, 0 }, { 0, 0, -1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @return matriz de transformaciones
	 */
	public static Matrix getYSymmetry() {
		double[][] values = { { -1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, -1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @return matriz de transformaciones
	 */
	public static Matrix getZSymmetry() {
		double[][] values = { { -1, 0, 0, 0 }, { 0, -1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @return matriz de transformaciones
	 */
	public static Matrix getXYSymmetry() {
		double[][] values = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, -1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @return matriz de transformaciones
	 */
	public static Matrix getYZSymmetry() {
		double[][] values = { { -1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @return matriz de transformaciones
	 */
	public static Matrix getXZSymmetry() {
		double[][] values = { { 1, 0, 0, 0 }, { 0, -1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @return matriz de transformaciones
	 */
	public static Matrix getOriginSymmetry() {
		double[][] values = { { -1, 0, 0, 0 }, { 0, -1, 0, 0 }, { 0, 0, -1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param radian radianes (rotacion en X)
	 * @return matriz de transformaciones
	 */
	public static Matrix getXRotation(double radian) {
		double[][] values = { { 1, 0, 0, 0 }, { 0, Math.cos(radian), Math.sin(radian), 0 },
				{ 0, -1 * Math.sin(radian), Math.cos(radian), 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param radian radianes (rotacion en Y)
	 * @return matriz de transformaciones
	 */
	public static Matrix getYRotation(double radian) {
		double[][] values = { { Math.cos(radian), 0, -1 * Math.sin(radian), 0 }, { 0, 1, 0, 0 },
				{ Math.sin(radian), 0, Math.cos(radian), 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param radian radianes (rotacion en Z)
	 * @return matriz de transformaciones
	 */
	public static Matrix getZRotation(double radian) {
		double[][] values = { { Math.cos(radian), Math.sin(radian), 0, 0 },
				{ -1 * Math.sin(radian), Math.cos(radian), 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param sxy cizalla en XY
	 * @param sxz cizalla en XZ
	 * @return matriz de transformaciones
	 */
	public static Matrix getXShear(double sxy, double sxz) {
		double[][] values = { { 1, sxy, sxz, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param syx cizalla en YX
	 * @param syz cizalla YZ
	 * @return matriz de transformaciones
	 */
	public static Matrix getYShear(double syx, double syz) {
		double[][] values = { { 1, 0, 0, 0 }, { syx, 1, syz, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param szx cizalla en ZX
	 * @param szy cizalla en ZY
	 * @return matriz de transformaciones
	 */
	public static Matrix getZShear(double szx, double szy) {
		double[][] values = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { szx, szy, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param sxy cizalla en XY
	 * @param sxz cizalla en XZ
	 * @param syx cizalla en YX
	 * @param syz cizalla en YZ
	 * @param szx cizalla en ZX
	 * @param szy cizalla en ZY
	 * @return matriz de transformaciones
	 */
	public static Matrix getGeneralShear(double sxy, double sxz, double syx, double syz, double szx, double szy) {
		double[][] values = { { 1, sxy, sxz, 0 }, { syx, 1, syz, 0 }, { szx, szy, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param kx cizalla en X
	 * @param ky cizalla en Y
	 * @param kz cizalla en Z
	 * @return matriz de transformaciones
	 */
	public static Matrix getGeneralShear(double kx, double ky, double kz) {
		double[][] values = { { 1, kx, kx, 0 }, { ky, 1, ky, 0 }, { kz, kz, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param k cizalla
	 * @return matriz de transformaciones
	 */
	public static Matrix getGeneralShear(double k) {
		double[][] values = { { 1, k, k, 0 }, { k, 1, k, 0 }, { k, k, 1, 0 }, { 0, 0, 0, 1 } };
		return new Matrix(values);
	}

	/**
	 * @param h escala global
	 * @return matriz de transformaciones
	 */
	public static Matrix getGlobalScale(double h) {
		return getGeneralScale(h);
	}
}
