package graphics.d3.trazador;

import java.awt.Color;

import graphics.d3.objetos.Objeto;

/**
 * <h1>ColorOperations</h1>
 * Clase que implementa operaciones con colores  
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class ColorOperations {
	
	/**
	 * @param obj objeto sobre el que aplicar el color
	 * @param f foco de luz
	 * @param angulo angulo de la luz
	 * @return color de la componente difusa
	 */
	public static Color difuso(Objeto obj, Foco f, double angulo){
		Color c = obj.getMaterial().getColor();
		double kd = obj.getMaterial().getKd();
		int intensidad = f.getIntensidad();
		
		double r = f.getColor().getRed() / 255;
		double g = f.getColor().getGreen() / 255;
		double b = f.getColor().getBlue() / 255;
		
		c = new Color(
				clamp((int) (c.getRed() * angulo * r * kd * intensidad), 0, 255),
				clamp((int) (c.getGreen() * angulo * g * kd * intensidad), 0, 255),
				clamp((int) (c.getBlue() * angulo * b * kd * intensidad), 0, 255)
				);
		return c;
	}
	
	/**
	 * @param c1 primer color a mezclar
	 * @param c2 segundo color a mezclar
	 * @return color resultante de mezclar @param c1 y @param c2
	 */
	public static Color add (Color c1, Color c2){
		int red = clamp((c1.getRed() + c2.getRed()), 0, 255);
		int green = clamp((c1.getGreen() + c2.getGreen()), 0, 255);
		int blue = clamp((c1.getBlue() + c2.getBlue()), 0, 255);
		
		return new Color(red, green, blue);
	}
	
	public static Color escalar (Color c, double k){
		int red = clamp((int)(c.getRed()*k), 0, 255);
		int green = clamp((int)(c.getGreen()*k), 0, 255);
		int blue = clamp((int)(c.getBlue()*k), 0, 255);
		
		return new Color(red, green, blue);
	}

	/**
	 * @param c1 primer color
	 * @param c2 segundo color
	 * @param kd coeficiente
	 * @return
	 */
	public static Color fresnel(Color c1, Color c2, double kd){
		return add(escalar(c1, kd), escalar(c2, (1-kd)));
	}
	
	/**
	 * @param color color a acotar
	 * @param min minimo
	 * @param max maximo
	 * @return color acotado entre minimo y maximo
	 */
	private static int clamp(int color, int min, int max){
		// set minimo
		color = Math.max(color, min);	// maximo entre el color y 0
		// set maximo
		color = Math.min(color, max);	// minimo entre el color y 255
		
		return color;
	}

	/**
	 * @param c1 primer color
	 * @param c2 segundo color
	 * @return color mezcla de @param c1 y @param c2
	 */
	public static Color addMedios(Color c1, Color c2) {
		int red = clamp((c1.getRed() + c2.getRed())/2, 0, 255);
		int green = clamp((c1.getGreen() + c2.getGreen())/2, 0, 255);
		int blue = clamp((c1.getBlue() + c2.getBlue())/2, 0, 255);
		
		return new Color(red, green, blue);
	}
}
