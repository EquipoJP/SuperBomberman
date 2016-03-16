package logic.characters;

import graphics.D2.rooms.Room;
import logic.Objeto;
import main.Initialization;
import main.Initialization.STAGE;

public class Tile extends Objeto{

	public Tile(int x, int y, Room r, STAGE c) {
		super(x, y, r);
		
		sprite_index = Initialization.getSpriteFromMap(c.toString() + "_TILE");
		image_speed = 0;
	}
}
