package logic.misc;

import logic.Objeto;

public class Level extends Objeto{
	
	public int mapInitX, mapInitY;
	public int mapWidth, mapHeight;
	
	public Level(int x, int y, int widthPX, int heightPX){
		super(x, y, 0, null);
		mapInitX = x;
		mapInitY = y;
		
		mapWidth = widthPX;
		mapHeight = heightPX;
	}
}
