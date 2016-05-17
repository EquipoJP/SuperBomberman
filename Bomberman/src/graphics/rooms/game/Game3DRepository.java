package graphics.rooms.game;

import java.util.LinkedList;
import java.util.List;

import graphics.d3.objetos.Objeto;
import graphics.d3.trazador.Trazador;
import graphics.d3.utils.XMLFormatter;

public class Game3DRepository {
	
	public static Objeto bomberman = null;
	public static Objeto bloque = null;
	public static Objeto enemigo = null;
	public static Objeto plano = null;
	
	private static String xml = "iniFiles/d3file.xml";
	
	public static void load(){
		loadPlano();
		loadBomberman();
		loadEnemigo();
		loadBloque();
		Trazador.setup(xml);
	}
	
	public static List<Objeto> getObjetos(){
		List<Objeto> objetos = new LinkedList<Objeto>();
		
		objetos.add(bloque);
		objetos.add(bomberman);
		objetos.add(enemigo);
		objetos.add(plano);
		
		return objetos;
	}

	private static void loadBomberman() {
		if(bomberman == null){
			bomberman = XMLFormatter.getBomberman(xml);
		}
	}

	private static void loadEnemigo() {
		if(enemigo == null){
			enemigo = XMLFormatter.getEnemigo(xml);
		}
	}

	private static void loadBloque() {
		if(bloque == null){
			bloque = XMLFormatter.getBloque(xml);
		}
	}

	private static void loadPlano() {
		if(plano == null){
			plano = XMLFormatter.getPlano(xml);
		}
	}

}
