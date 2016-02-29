package graphics.effects;

import java.awt.Graphics;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import main.Initialization;
import logic.Objeto;

public class Logo extends Objeto {

	public Logo(int x, int y, Room r) {
		super(x, y, r);
		sprite_index = Initialization.getSprite("LOGO");
	}

	@Override
	public void create() {
	}

	@Override
	public void customStep() {
	}

	@Override
	public void alarm(int alarmNo) {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(sprite_index.getSubsprites()[0], x - sprite_index.getCenterX(), y - sprite_index.getCenterY(),
				null);
	}

	@Override
	public void customDestroy() {
	}

	@Override
	public void processKey(KEY key) {
	}

}
