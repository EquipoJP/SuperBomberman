package logic.misc;

import graphics.D2.rooms.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import logic.Objeto;
import logic.characters.Block;
import logic.characters.DestroyableBlock;
import logic.characters.Enemy;
import logic.characters.Player;
import main.Initialization;
import main.Initialization.STAGE;

public class Map {

	public static final char DESTROYABLE_BLOCK = '0';
	public static final char BLOCK = '1';
	public static final char ENEMY = '2';
	public static final char BOMBERMAN = '4';

	public static List<Objeto> getMap(String file, Room room,
			Initialization.STAGE stage) {
		List<Objeto> objetos = new LinkedList<Objeto>();

		int width = 0;
		int height = 0;

		try {
			Scanner s = new Scanner(new File(System.getProperty("user.dir")
					+ "/resources/" + file));

			int row = 0;
			while (s.hasNextLine()) {
				String str = s.nextLine();

				for (int col = 0; col < str.length(); col++) {
					char c = str.charAt(col);
					switch (c) {
					case DESTROYABLE_BLOCK:
						objetos.add(createDestroyable(row, col, room, stage));
						break;
					case BLOCK:
						objetos.add(createBlock(row, col, room));
						break;
					case ENEMY:
						objetos.add(createEnemy(row, col, room, stage));
						break;
					case BOMBERMAN:
						objetos.add(createBomberman(row, col, room));
						break;
					}
				}

				width = str.length();

				row++;
			}

			height = row;

			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(width + " " + height);
		objetos.add(new Level(Initialization.MAP_X_OFFSET,
				Initialization.MAP_Y_OFFSET, width * Initialization.TILE_WIDTH,
				height * Initialization.TILE_HEIGHT));

		return objetos;
	}

	private static Objeto createDestroyable(int row, int col, Room room,
			STAGE stage) {
		int x = getX(col);
		int y = getY(row);

		return new DestroyableBlock(x, y, room, stage);
	}

	private static Objeto createBlock(int row, int col, Room room) {
		int x = getX(col);
		int y = getY(row);

		return new Block(x, y, room);
	}

	private static Objeto createEnemy(int row, int col, Room room, STAGE stage) {
		int x = getX(col);
		int y = getY(row);

		return new Enemy(x, y, room, stage);
	}

	private static Objeto createBomberman(int row, int col, Room room) {
		int x = getX(col);
		int y = getY(row);

		return new Player(x, y, room, 0);
	}

	public static int getX(int col) {
		return Initialization.MAP_X_OFFSET + col * Initialization.TILE_WIDTH;
	}

	public static int getY(int row) {
		return Initialization.MAP_Y_OFFSET + row * Initialization.TILE_HEIGHT;
	}

	public static int getRow(int y) {
		return (y - Initialization.MAP_Y_OFFSET + Initialization.TILE_HEIGHT/2) / Initialization.TILE_HEIGHT;
	}

	public static int getCol(int x) {
		return (x - Initialization.MAP_X_OFFSET + Initialization.TILE_WIDTH/2) / Initialization.TILE_WIDTH;
	}
}
