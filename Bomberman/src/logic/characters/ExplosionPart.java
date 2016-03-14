package logic.characters;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;

public class ExplosionPart extends Objeto {

	/* Enums publicas */
	public enum KIND {
		CORE, MID, EDGE
	};

	public enum SIDE {
		UP, DOWN, LEFT, RIGHT
	};

	/* Atributos */
	private KIND kind;
	private SIDE side;

	public ExplosionPart(int x, int y, Room r, KIND k, SIDE s) {
		super(x, y, r);
		kind = k;
		side = s;
	}

	@Override
	public void create() {
		

	}

	@Override
	public void customStep(KEY key) {
		if(image_index>=sprite_index.getSubimages()){
			destroy();
		}
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
