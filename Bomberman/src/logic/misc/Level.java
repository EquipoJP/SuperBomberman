package logic.misc;

import logic.Objeto;

public class Level extends Objeto{
	
	public int mapInitX, mapInitY;
	public int mapWidth, mapHeight;
	
	public Level(int x, int y, int w, int h){
		super(x, y, null);
		mapInitX = x;
		mapInitY = y;
		
		mapWidth = w;
		mapHeight = h;
	}
}
