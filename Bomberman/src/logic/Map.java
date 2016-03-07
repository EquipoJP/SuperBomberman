package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import graphics.D2.rooms.Room;

public class Map {
	
	public static final char DESTROYABLE_BLOCK = '0';
	public static final char BLOCK = '1';
	public static final char ENEMY_BLUE = '2';
	public static final char ENEMY_PINK = '3';
	public static final char BOMBERMAN = '4';
	
	public static List<Objeto> getMap(String file, Room room){
		List<Objeto> objetos = new LinkedList<Objeto>();
		// TODO leer el file y parsearlo para crear los objetos donde toca
		// (en Initialization hay variables para tile width, height y map width...)
		
		try {
			Scanner s = new Scanner(new File(System.getProperty("user.dir") + "/resources/" + file));
			
			int row = 0;
			while(s.hasNextLine()){
				String str = s.nextLine();
				
				for(int col = 0; col < str.length(); col++){
					char c = str.charAt(col);
					switch(c){
					case DESTROYABLE_BLOCK:
						objetos.add(createDestroyable(row, col, room));
						break;
					case BLOCK:
						objetos.add(createBlock(row, col, room));
						break;
					case ENEMY_BLUE:
						objetos.add(createBlueEnemy(row, col, room));
						break;
					case ENEMY_PINK:
						objetos.add(createPinkEnemy(row, col, room));
						break;
					case BOMBERMAN:
						objetos.add(createBomberman(row, col, room));
						break;
					}
				}
				
				row++;
			}
			
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objetos;
	}

	private static Objeto createDestroyable(int row, int col, Room room) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Objeto createBlock(int row, int col, Room room) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Objeto createBlueEnemy(int row, int col, Room room) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Objeto createPinkEnemy(int row, int col, Room room) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Objeto createBomberman(int row, int col, Room room) {
		// TODO Auto-generated method stub
		return null;
	}

}
