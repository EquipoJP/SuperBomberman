/**
 * Class to generate maps for the levels of the game
 */
package logic.misc;

import graphics.rooms.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import logic.Objeto;
import logic.characters.Block;
import logic.characters.DestroyableBlock;
import logic.characters.Enemy;
import logic.characters.Item.TYPE;
import logic.characters.Player;
import logic.collisions.BoundingBox;
import logic.collisions.Point2D;
import main.Initialization;
import main.Initialization.STAGE;
import utils.FileUtils;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Map {

	public static final char BLOCK = '0';
	public static final char DESTROYABLE_BLOCK = '1';
	public static final char DESTROYABLE_BLOCK_SPEED = '2';
	public static final char DESTROYABLE_BLOCK_POWER = '3';
	public static final char DESTROYABLE_BLOCK_BOMBS = '4';
	public static final char ENEMY = 'E';
	public static final char BOMBERMAN = 'B';
	public static final char NOTHING = '-';

	private static final int fixpos = 5;

	/**
	 * Creates a map from a file
	 * 
	 * @param file
	 *            file to read the map from
	 * @param room
	 *            room in which create the map
	 * @param stage
	 *            theme of the map
	 * @return list of objects result of creating the map
	 */
	public static List<Objeto> getMap(String file, Room room,
			Initialization.STAGE stage) {
		List<Objeto> objetos = new LinkedList<Objeto>();

		int width = 0;
		int height = 0;

		Scanner s = new Scanner(Map.class.getClassLoader().getResourceAsStream(
				file));

		Random g = new Random();
		int row = 0;

		// Read file
		while (s.hasNextLine()) {
			String str = s.nextLine();

			for (int col = 0; col < str.length(); col++) {
				if (col == 0) {
					objetos.add(createBlock(row, -1, room));
				}
				if (col == str.length() - 1) {
					objetos.add(createBlock(row, str.length(), room));
				}

				char c = str.charAt(col);
				switch (c) {
				case DESTROYABLE_BLOCK:
					objetos.add(createDestroyable(row, col, room, stage,
							g.nextInt(4)));
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

		for (int col = 0; col < width; col++) {
			if (col == 0) {
				objetos.add(createBlock(row, -1, room));
			}
			if (col == width - 1) {
				objetos.add(createBlock(row, width, room));
			}
			objetos.add(createBlock(row, col, room));
		}

		height = row;

		s.close();

		objetos.add(new Level(Initialization.MAP_X_OFFSET,
				Initialization.MAP_Y_OFFSET, width * Initialization.TILE_WIDTH,
				height * Initialization.TILE_HEIGHT));

		return objetos;
	}

	/**
	 * Generates a random map from various templates
	 * 
	 * @param room
	 *            room to create the map in
	 * @param stage
	 *            theme of the map
	 * @return list of object result of creating the map
	 */
	public static List<Objeto> generateMap(Room room, Initialization.STAGE stage) {
		List<Objeto> objetos = new LinkedList<Objeto>();

		// Choose random file
		long seed = System.nanoTime();
		Random g = new Random(seed);
		String folder = "maps/templates/";
		int numberOfFiles = FileUtils.numberOfFiles(folder, "n", ".txt");
		String file = folder + "n" + g.nextInt(numberOfFiles) + ".txt";
		int widthMap = Initialization.MAP_WIDTH;
		int heightMap = Initialization.MAP_HEIGHT;
		int playerx = 0;
		int playery = 0;

		// Find bomberman
		Scanner b = new Scanner(Map.class.getClassLoader().getResourceAsStream(
				file));

		int row = 0;
		int blockCount = 0;
		int enemyCount = 0;

		// Read file
		while (b.hasNextLine()) {
			String str = b.nextLine();

			for (int col = 0; col < str.length(); col++) {
				char c = str.charAt(col);
				switch (c) {
				case BOMBERMAN:
					playerx = col;
					playery = row;
					break;
				case BLOCK:
					blockCount++;
					break;
				default:
					break;
				}
			}

			row++;
		}

		b.close();

		Scanner s = new Scanner(Map.class.getClassLoader().getResourceAsStream(
				file));

		List<Character> list = generateListOfObjects();

		row = 0;
		int count = 0;
		int originalPut = list.size() / 8;
		int put = originalPut;
		int iterCount = 0;
		int total = (widthMap * heightMap) - blockCount - 1;
		double prob = 0.5;
		// Read file
		while (s.hasNextLine()) {
			String str = s.nextLine();

			for (int col = 0; col < str.length(); col++) {
				if (col == 0) {
					objetos.add(createBlock(row, -1, room));
				}
				if (col == str.length() - 1) {
					objetos.add(createBlock(row, str.length(), room));
				}

				char c = str.charAt(col);
				switch (c) {
				case BLOCK:
					objetos.add(createBlock(row, col, room));
					break;
				case NOTHING:
					iterCount++;
					double result = g.nextDouble();
					if (enemyCount == 0 && total - iterCount <= 3) {
						// Assure 1 enemy
						list.add(0, '?');
						list.add(0, '?');
						enemyCount++;
						Objeto obj = createEnemy(row, col, room, stage);
						objetos.add(obj);
					} else if (list.size() == (total - count)
							|| (result >= prob
									&& validDistanceToPlayer(playerx, playery,
											col, row) && put != 0)) {
						prob = decreaseProb(prob);
						Objeto obj = generateRandomObject(row, col, room,
								stage, list);
						if (obj != null) {
							objetos.add(obj);
							put--;
						}
						if (obj instanceof Enemy) {
							list.add(0, '?');
							list.add(0, '?');
							enemyCount++;
						}
					}

					if (result < prob) {
						prob = increaseProb(prob);
					}

					if (row % (widthMap / 5) == 0) {
						// Reset prob
						prob = 0.5;

						// Reset put
						put = originalPut;
					}

					break;
				case BOMBERMAN:
					objetos.add(createBomberman(row, col, room));
					break;
				}
				count++;
			}

			row++;
		}

		for (int col = 0; col < widthMap; col++) {
			if (col == 0) {
				objetos.add(createBlock(row, -1, room));
			}
			if (col == widthMap - 1) {
				objetos.add(createBlock(row, widthMap, room));
			}
			objetos.add(createBlock(row, col, room));
		}

		s.close();

		objetos.add(new Level(Initialization.MAP_X_OFFSET,
				Initialization.MAP_Y_OFFSET, widthMap
						* Initialization.TILE_WIDTH, heightMap
						* Initialization.TILE_HEIGHT));

		return objetos;
	}

	/**
	 * Create destroyable block
	 * 
	 * @param row
	 *            row in which create the destroyable block
	 * @param col
	 *            column in which create the destroyable block
	 * @param room
	 *            room in which create the destroyable block
	 * @param stage
	 *            them of the destroyable block
	 * @param kind
	 *            kind of powerup it will drop when destroyed
	 * @return
	 */
	private static Objeto createDestroyable(int row, int col, Room room,
			STAGE stage, int kind) {
		int x = getX(col);
		int y = getY(row);

		TYPE tipo = null;

		switch (kind) {
		case 0:
			tipo = null;
			break;
		case 1:
			// Drops bomb
			tipo = TYPE.BOMB;
			break;
		case 2:
			// Drops bomb
			tipo = TYPE.POWER;
			break;
		case 3:
			// Drops bomb
			tipo = TYPE.SPEED;
			break;
		default:
			break;
		}

		return new DestroyableBlock(x, y, 0, room, stage, tipo);
	}

	/**
	 * Creates a block
	 * 
	 * @param row
	 *            row in which create the block
	 * @param col
	 *            column in which create the block
	 * @param room
	 *            room in which create the block
	 * @return a block
	 */
	private static Objeto createBlock(int row, int col, Room room) {
		int x = getX(col);
		int y = getY(row);

		return new Block(x, y, 0, room);
	}

	/**
	 * Creates an enemy
	 * 
	 * @param row
	 *            row in which create the enemy
	 * @param col
	 *            column in which create the enemy
	 * @param room
	 *            room in which create the enemy
	 * @param stage
	 *            theme of the enemy
	 * @return an enemy
	 */
	private static Objeto createEnemy(int row, int col, Room room, STAGE stage) {
		int x = getX(col);
		int y = getY(row) - fixpos;

		return new Enemy(x, y, 0, room, stage);
	}

	/**
	 * Creates the player
	 * 
	 * @param row
	 *            row in which create the player
	 * @param col
	 *            column in which create the player
	 * @param room
	 *            room in which create the player
	 * @return the player
	 */
	private static Objeto createBomberman(int row, int col, Room room) {
		int x = getX(col);
		int y = getY(row) - fixpos;

		return new Player(x, y, 0, room, 0);
	}

	/**
	 * @param col
	 *            column
	 * @return x coordinate
	 */
	public static int getX(int col) {
		return Initialization.MAP_X_OFFSET + col * Initialization.TILE_WIDTH;
	}

	/**
	 * @param row
	 *            row
	 * @return y coordinate
	 */
	public static int getY(int row) {
		return Initialization.MAP_Y_OFFSET + row * Initialization.TILE_HEIGHT;
	}

	/**
	 * @param y
	 *            y coordinate
	 * @return row
	 */
	public static int getRow(int y) {
		return (y - Initialization.MAP_Y_OFFSET + Initialization.TILE_HEIGHT / 2)
				/ Initialization.TILE_HEIGHT;
	}

	/**
	 * @param x
	 *            x coordinate
	 * @return column
	 */
	public static int getCol(int x) {
		return (x - Initialization.MAP_X_OFFSET + Initialization.TILE_WIDTH / 2)
				/ Initialization.TILE_WIDTH;
	}

	/**
	 * Generates a list of types of destroyable blocks to use when generating
	 * random maps
	 * 
	 * @return a list of types of destroyable blocks
	 */
	private static List<Character> generateListOfObjects() {
		List<Character> c = new ArrayList<Character>();
		for (int i = 0; i < 25; i++) {
			c.add(DESTROYABLE_BLOCK);
		}
		for (int i = 0; i < 6; i++) {
			c.add(DESTROYABLE_BLOCK_SPEED);
		}
		for (int i = 0; i < 15; i++) {
			c.add(DESTROYABLE_BLOCK_POWER);
		}
		for (int i = 0; i < 6; i++) {
			c.add(DESTROYABLE_BLOCK_BOMBS);
		}
		for (int i = 0; i < 3; i++) {
			c.add(ENEMY);
		}
		long seed = System.nanoTime();
		Collections.shuffle(c, new Random(seed));
		return c;
	}

	/**
	 * Generates a random object
	 * 
	 * @param row
	 *            row
	 * @param col
	 *            column
	 * @param room
	 *            room
	 * @param stage
	 *            theme
	 * @param list
	 *            list of types of destroyable blocks
	 * @return a random object
	 */
	private static Objeto generateRandomObject(int row, int col, Room room,
			STAGE stage, List<Character> list) {
		if (list.size() == 0) {
			return null;
		} else {
			char c = list.get(0);
			list.remove(0);
			Objeto obj = null;
			switch (c) {
			case DESTROYABLE_BLOCK:
				obj = createDestroyable(row, col, room, stage, 0);
				break;
			case DESTROYABLE_BLOCK_SPEED:
				obj = createDestroyable(row, col, room, stage, 3);
				break;
			case DESTROYABLE_BLOCK_POWER:
				obj = createDestroyable(row, col, room, stage, 2);
				break;
			case DESTROYABLE_BLOCK_BOMBS:
				obj = createDestroyable(row, col, room, stage, 1);
				break;
			case ENEMY:
				obj = createEnemy(row, col, room, stage);
				break;
			default:
				break;
			}
			return obj;
		}
	}

	/**
	 * Check if the distance to the player is valid
	 * 
	 * @param playerx
	 *            column of the player
	 * @param playery
	 *            row of the player
	 * @param col
	 *            column
	 * @param row
	 *            row
	 * @return
	 */
	private static boolean validDistanceToPlayer(int playerx, int playery,
			int col, int row) {
		boolean valid = true;
		if (col >= playerx - 3 && col <= playerx + 3 && row >= playery - 3
				&& row <= playery + 3) {
			valid = false;
		}
		return valid;
	}

	/**
	 * Increases probability
	 * 
	 * @param input
	 *            input probability
	 * @return increased probability
	 */
	private static double increaseProb(double input) {
		double returned = input - 2;
		if (input <= 0) {
			returned = 0;
		}
		return returned;
	}

	/**
	 * Decreases probability
	 * 
	 * @param input
	 *            input probability
	 * @return decreased probability
	 */
	private static double decreaseProb(double input) {
		double returned = input + 2;
		if (input >= 1) {
			returned = 1;
		}
		return returned;
	}

	/**
	 * Generates a random position on the map
	 * 
	 * @param room
	 *            room in which generate a random position
	 * @return a random position, unoccupied
	 */
	public static Point2D randomPosition(Room room) {
		Point2D position = null;

		int widthMap = Initialization.MAP_WIDTH;
		int heightMap = Initialization.MAP_HEIGHT;

		BoundingBox bb = new BoundingBox(
				new Point2D(-Initialization.TILE_WIDTH / 2,
						-Initialization.TILE_HEIGHT / 2), new Point2D(
						Initialization.TILE_WIDTH / 2,
						Initialization.TILE_HEIGHT / 2));

		Random random = new Random(System.nanoTime());

		while (position == null) {
			boolean free = true;

			int col = random.nextInt(widthMap);
			int row = random.nextInt(heightMap);

			int x = getX(col);
			int y = getY(row);

			bb.update(x, y);
			for (Objeto obj : room.objetos) {
				if (BoundingBox.collision(bb, obj.boundingBox)) {
					free = false;
					break;
				}
			}

			if (free) {
				position = new Point2D(x, y);
			} else {
				bb.update(-x, -y);
			}
		}

		return position;
	}
}
