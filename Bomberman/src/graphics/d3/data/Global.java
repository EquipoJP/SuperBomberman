package graphics.d3.data;

import graphics.d3.multithreading.Escena;
import graphics.d3.trazador.Camara;
import graphics.d3.trazador.Trazador;

public class Global {
	
	public static Camara camara;
	public static Escena escena;
	
	public static void setup(String file){
		Trazador.setup(file);
		escena = Trazador.escena;
		camara = escena.camara;
	}
	
	public static void work(double camaraMovX, double camaraMovY, double camaraMovZ){
		camara.step(camaraMovX, camaraMovY, camaraMovZ);
		Trazador.work();
	}
}
