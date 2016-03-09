package graphics.effects;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import main.Initialization;

public class Logo extends Objeto {

	public Logo(int x, int y, Room r) {
		super(x, y, r);
		sprite_index = Initialization.getSpriteFromSprites("LOGO");
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
