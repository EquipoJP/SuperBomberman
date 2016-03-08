package logic;

import java.util.List;

import logic.characters.Block;
import logic.characters.BlueDoll;
import logic.characters.PinkDoll;
import logic.characters.Player;
import main.Initialization;

public class TileMap {

	private char[][] map = null;

	public TileMap(List<Objeto> objects, Level level) {
		map = new char[level.mapWidth / Initialization.TILE_WIDTH][level.mapHeight / Initialization.TILE_HEIGHT];
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = '-';
			}
		}

		for (Objeto obj : objects) {
			int x = obj.x;
			int y = obj.y;
			
			int row = getRow(x);
			int col = getCol(y);
			
			char type = getType(obj);
			
			map[row][col] = type;
		}
	}

	private static int getRow(int x) {
		return (x - Initialization.MAP_X_OFFSET) / Initialization.TILE_WIDTH;
	}

	private static int getCol(int y) {
		return (y - Initialization.MAP_Y_OFFSET) / Initialization.TILE_HEIGHT;
	}
	
	private static int getX(int row) {
		return Initialization.MAP_X_OFFSET + row * Initialization.TILE_WIDTH;
	}

	private static int getY(int col) {
		return Initialization.MAP_Y_OFFSET + col * Initialization.TILE_HEIGHT;
	}
	
	private static char getType(Objeto obj){
		if(obj instanceof Block){
			return Map.BLOCK;
		}
		if(obj instanceof Player){
			return Map.BOMBERMAN;
		}
		if(obj instanceof BlueDoll){
			return Map.ENEMY_BLUE;
		}
		if(obj instanceof PinkDoll){
			return Map.ENEMY_PINK;
		}
		return '-';
	}

	public void updatePosition(int x, int y, Objeto obj) {
		char type = '-';
		if(obj != null){
			type = getType(obj);
		}
		
		int row = getX(x);
		int col = getY(y);
		
		map[row][col] = type;
	}

}
