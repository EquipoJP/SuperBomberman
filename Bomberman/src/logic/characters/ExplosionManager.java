package logic.characters;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;

public class ExplosionManager extends Objeto {
	
	private int radius;

	public ExplosionManager(int x, int y, Room r, int radius) {
		super(x, y, r);
		this.radius = radius;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void customStep(KEY key, KEY direction) {
		// TODO Auto-generated method stub
		this.destroy();
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

}
