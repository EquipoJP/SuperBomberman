package logic.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import graphics.rooms.Room;
import logic.characters.Item.TYPE;
import logic.Objeto;
import logic.characters.Block;
import logic.characters.DestroyableBlock;
import logic.characters.Enemy;
import logic.characters.Player;
import main.Initialization;
import main.Initialization.STAGE;

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

	public static List<Objeto> getMap(String file, Room room, Initialization.STAGE stage) {
		List<Objeto> objetos = new LinkedList<Objeto>();

		int width = 0;
		int height = 0;

		try {
			Scanner s = new Scanner(new File(System.getProperty("user.dir") + "/resources/" + file));

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
						objetos.add(createDestroyable(row, col, room, stage, g.nextInt(4)));
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(width + " " + height);
		objetos.add(new Level(Initialization.MAP_X_OFFSET, Initialization.MAP_Y_OFFSET,
				width * Initialization.TILE_WIDTH, height * Initialization.TILE_HEIGHT));

		return objetos;
	}

	public static List<Objeto> generateMap(Room room, Initialization.STAGE stage) {
		List<Objeto> objetos = new LinkedList<Objeto>();

		// Choose random file
		long seed = System.nanoTime();
		Random g = new Random(seed);
		String folder = "/resources/maps/templates";
		int numberOfFiles = new File(System.getProperty("user.dir") + folder).listFiles().length;
		String file = folder + "/n" + g.nextInt(numberOfFiles) + ".txt";
		int widthMap = Initialization.MAP_WIDTH;
		int heightMap = Initialization.MAP_HEIGHT;
		int playerx = 0;
		int playery = 0;

		try {

			// Find bomberman
			Scanner b = new Scanner(new File(System.getProperty("user.dir") + file));

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

			Scanner s = new Scanner(new File(System.getProperty("user.dir") + file));

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
								|| (result >= prob && validDistanceToPlayer(playerx, playery, col, row) && put != 0)) {
							prob = decreaseProb(prob);
							Objeto obj = generateRandomObject(row, col, room, stage, list);
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(widthMap + " " + heightMap);
		objetos.add(new Level(Initialization.MAP_X_OFFSET, Initialization.MAP_Y_OFFSET,
				widthMap * Initialization.TILE_WIDTH, heightMap * Initialization.TILE_HEIGHT));

		return objetos;
	}

	private static Objeto createDestroyable(int row, int col, Room room, STAGE stage, int kind) {
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

	private static Objeto createBlock(int row, int col, Room room) {
		int x = getX(col);
		int y = getY(row);

		return new Block(x, y, 0, room);
	}

	private static Objeto createEnemy(int row, int col, Room room, STAGE stage) {
		int x = getX(col);
		int y = getY(row) - fixpos;

		return new Enemy(x, y, 0, room, stage);
	}

	private static Objeto createBomberman(int row, int col, Room room) {
		int x = getX(col);
		int y = getY(row) - fixpos;

		return new Player(x, y, 0, room, 0);
	}

	public static int getX(int col) {
		return Initialization.MAP_X_OFFSET + col * Initialization.TILE_WIDTH;
	}

	public static int getY(int row) {
		return Initialization.MAP_Y_OFFSET + row * Initialization.TILE_HEIGHT;
	}

	public static int getRow(int y) {
		return (y - Initialization.MAP_Y_OFFSET + Initialization.TILE_HEIGHT / 2) / Initialization.TILE_HEIGHT;
	}

	public static int getCol(int x) {
		return (x - Initialization.MAP_X_OFFSET + Initialization.TILE_WIDTH / 2) / Initialization.TILE_WIDTH;
	}

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

	private static Objeto generateRandomObject(int row, int col, Room room, STAGE stage, List<Character> list) {
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

	private static boolean validDistanceToPlayer(int playerx, int playery, int col, int row) {
		boolean valid = true;
		if (col >= playerx - 3 && col <= playerx + 3 && row >= playery - 3 && row <= playery + 3) {
			valid = false;
		}
		return valid;
	}

	private static double increaseProb(double input) {
		double returned = input - 2;
		if (input <= 0) {
			returned = 0;
		}
		return returned;
	}

	private static double decreaseProb(double input) {
		double returned = input + 2;
		if (input >= 1) {
			returned = 1;
		}
		return returned;
	}
}
