package graphics.d3.multithreading;

/**
 * <h1>Pixel</h1>
 * Estructura de datos que representa un <b>pixel</b> de la 
 * imagen. 
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Pixel {

	/* atributos privados */
	private int i;
	private int j;
	
	/**
	 * @param i fila de la imagen
	 * @param j columna de la imagen
	 */
	public Pixel(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	/**
	 * @return fila de la imagen
	 */
	public int getI() {
		return i;
	}
	
	/**
	 * @param i fila de la imagen
	 */
	public void setI(int i) {
		this.i = i;
	}
	
	/**
	 * @return columna de la imagen
	 */
	public int getJ() {
		return j;
	}
	
	/**
	 * @param j columna de la imagen
	 */
	public void setJ(int j) {
		this.j = j;
	}
}
