package logic.characters;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import main.Initialization;
import main.Initialization.STAGE;

public class Tile extends Objeto{

	public Tile(int x, int y, Room r, STAGE c) {
		super(x, y, r);
		
		sprite_index = Initialization.getSpriteFromMap(c.toString() + "_TILE");
		image_speed = 0;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alarm(int alarmNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processKey(KEY key, KEY direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customCollision(Objeto colision) {
		// TODO Auto-generated method stub
		
	}

}
