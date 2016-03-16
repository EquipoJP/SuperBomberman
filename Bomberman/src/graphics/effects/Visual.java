package graphics.effects;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;

public class Visual extends Objeto {

	public Visual(int x, int y, Room r, Sprite spr) {
		super(x, y, r);
		
		sprite_index = spr;
		image_speed = 0;
	}

	@Override
	public void create() {
	}

	@Override
	public void customStep(KEY key) {
	}

	@Override
	public void alarm(int alarmNo) {
	}

	@Override
	public void customDestroy() {
	}

	@Override
	public void processKey(KEY key) {
	}

	@Override
	public void customCollision(Objeto colision) {
		// TODO Auto-generated method stub
		
	}

}
