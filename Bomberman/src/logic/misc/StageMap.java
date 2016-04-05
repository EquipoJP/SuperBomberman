package logic.misc;

import logic.Objeto;
import logic.characters.Player;

public class StageMap {

	private Objeto[][] map;
	private int px;
	private int py;
	
	public StageMap(int w, int h, int playerx, int playery){
		map = new Objeto[w][h];
		px = playerx;
		py = playery;
	}
	
}
