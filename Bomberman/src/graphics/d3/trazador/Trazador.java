package graphics.d3.trazador;

import graphics.d3.multithreading.Escena;
import graphics.d3.multithreading.Worker;
import graphics.d3.utils.XMLFormatter;

/**
 * <h1>Trazador</h1>
 * Clase principal que implementa el trazador 
 * de rayos con multithread y cajas envolventes
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Trazador {
	
	/* atributos privados */
	//private static String IMAGE_FILE_NAME;
	private static int NUM_WORKERS;
	private static Worker[] workers = new Worker[NUM_WORKERS];
	private static Thread[] workersT = new Thread[NUM_WORKERS];
	public static Escena escena;
	
	public static void setup(String file){
		escena = new Escena(file);
		NUM_WORKERS = XMLFormatter.setMultiThreading(file);
		workers = new Worker[NUM_WORKERS];
		workersT = new Thread[NUM_WORKERS];
	}
	
	public static void work(){
		for (int id = 0; id < workers.length; id++) {
			Worker worker = new Worker(id, escena);
			Thread tW = new Thread(worker);
			workersT[id] = tW;
			tW.start();
		}
		
		/* Espera a que acaben los trabajadores */
		for (Thread workerT : workersT)  
	    {  
	      while (workerT.isAlive())  
	      {  
	        try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
	      }  
	    }
	}
}
