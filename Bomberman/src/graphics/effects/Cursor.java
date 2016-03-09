package graphics.effects;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;

public class Cursor extends Objeto {

	public Cursor(int x, int y, Room r, Sprite sprite) {
		super(x, y, r);
		sprite_index = sprite;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void customStep(KEY key) {
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
	public void processKey(KEY key) {
		// TODO Auto-generated method stub

	}

}
