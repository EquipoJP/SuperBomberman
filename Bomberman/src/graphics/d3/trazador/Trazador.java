package graphics.d3.trazador;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	private static String IMAGE_FILE_NAME;
	private static int NUM_WORKERS;
	private static Worker[] workers = new Worker[NUM_WORKERS];
	private static Thread[] workersT = new Thread[NUM_WORKERS];
	
	/**
	 * Metodo principal para lanzar el trazador de rayos.
	 * No toma ningun parametro.
	 * @param args argumentos
	 */
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		//String nameFile = "escenaGiroide.xml";
		String nameFile = "escenaGiroide2.xml";
		if(args.length>=1){
			nameFile = args[0];
		}
		mainWork(nameFile);
		long endTime = System.nanoTime();

		long duration = (endTime - startTime) / (long) (1000000.0);
		System.out.println("Execution time: " + duration + " miliseconds");
	}

	/**
	 * Lanza los diferentes trabajadores y la escena
	 * 
	 * @param file fichero donde se encuentra la escena
	 * a renderizar
	 */
	public static void mainWork(String file) {
		String xml = file;
		
		IMAGE_FILE_NAME = XMLFormatter.getFile(xml);
		NUM_WORKERS = XMLFormatter.setMultiThreading(xml);
		workers = new Worker[NUM_WORKERS];
		workersT = new Thread[NUM_WORKERS];
		
		/* Crea la escena */
		System.out.printf("Preparando escena...");
		Escena escena = new Escena(xml);
		
		/* Lanza los trabajadores */
		System.out.println("OK");

		for (int id = 0; id < workers.length; id++) {
			Worker worker = new Worker(id, escena);
			Thread tW = new Thread(worker);
			workersT[id] = tW;
			tW.start();
		}
		
		System.out.printf("Lanzando rayos...");

		/* Espera a que acaben los trabajadores y guarda la imagen */
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
		
		System.out.println("OK");
		System.out.printf("Guardando imagen...");

		/* Escribe la imagen resultante en un fichero */
		try {

			File f = new File(IMAGE_FILE_NAME);
			if (f.exists()) {
				String strong = IMAGE_FILE_NAME + "_" + System.currentTimeMillis() + ".png";
				f = new File(strong);
			}
			if (!ImageIO.write(escena.getImg(), "PNG", f)) {
				throw new RuntimeException("ERROR. Unexpected error writing image");
			} else {
				System.out.println("OK");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
