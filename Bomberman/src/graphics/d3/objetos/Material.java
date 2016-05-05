package graphics.d3.objetos;

import java.awt.Color;

/**
 * <h1>Material</h1>
 * Clase que implementa el comportamiento del material
 * de los objetos. 
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Material {

	/* atributos privados */
	private Color color; // color del material
	private double kd, ks; // ks = 1 - kd
	private double kr, kt;	// kreflejado, ktransmitido
	private double ir;
	private int shiny;

	/**
	 * @param color color del material
	 * @param kd coeficiente de difusion
	 * @param ks coeficiente especula
	 * @param kr coeficiente de reflexion
	 * @param kt coeficiente de transmision
	 * @param ir indice de refraccion
	 * @param shiny dispersion del reflejo especular
	 */
	public Material(Color color, double kd, double ks, 
			double kr, double kt, 
			double ir, int shiny) {
		this.color = color;
		this.kd = kd;
		this.ks = ks;
		this.kr = kr;
		this.kt = kt;
		this.ir = ir;
		this.shiny = shiny;
	}

	/**
	 * @return color del material
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @param color color del material 
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * @return coeficiente de difusion
	 */
	public double getKd() {
		return kd;
	}

	/**
	 * @param kd coeficiente de difusion
	 */
	public void setKd(double kd) {
		this.kd = kd;
	}

	/**
	 * @return coeficiente especular
	 */
	public double getKs() {
		return ks;
	}

	/**
	 * @param ks coeficiente especular
	 */
	public void setKs(double ks) {
		this.ks = ks;
	}

	/**
	 * @return coeficiente de reflexion
	 */
	public double getKr() {
		return kr;
	}

	/**
	 * @param kr coeficiente de reflexion
	 */
	public void setKr(double kr) {
		this.kr = kr;
	}

	/**
	 * @return coeficiente de transmision
	 */
	public double getKt() {
		return kt;
	}

	/**
	 * @param kt coeficiente de transmision
	 */
	public void setKt(double kt) {
		this.kt = kt;
	}

	/**
	 * @return <b>true</b> si es transparente, 
	 * <b>false</b> en caso contrario
	 */
	public boolean isTransparente() {
		return kt > 0;
	}

	/**
	 * @return <b>true</b> si es reflectante, 
	 * <b>false</b> en caso contrario
	 */
	public boolean isReflectante() {
		return kr > 0;
	}

	/**
	 * @return dispersion del reflejo especular
	 */
	public int getShiny() {
		return shiny;
	}

	/**
	 * @param shiny dispersion del reflejo especular
	 */
	public void setShiny(int shiny) {
		this.shiny = shiny;
	}

	/**
	 * @return indice de refraccion
	 */
	public double getIr() {
		return ir;
	}

	/**
	 * @param ir indice de refraccion
	 */
	public void setIr(double ir) {
		this.ir = ir;
	}
}
