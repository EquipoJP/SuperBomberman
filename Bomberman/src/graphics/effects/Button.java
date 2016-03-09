package graphics.effects;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;

public class Button extends Objeto {

	public Button(int x, int y, Room r, Sprite sprite) {
		super(x, y, r);
		sprite_index = sprite;
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

}
