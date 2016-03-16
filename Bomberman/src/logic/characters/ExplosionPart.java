package logic.characters;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import main.Initialization;

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
		animation_end = false;
		kind = k;
		side = s;
		String name = "EXPLOSION_CORE";
		switch (kind) {

		case CORE:
			name = "EXPLOSION_CORE";
			break;

		case MID:
			switch (side) {
			case UP:
			case DOWN:
				name = "EXPLOSION_MID_VER";
				break;
			case LEFT:
			case RIGHT:
				name = "EXPLOSION_MID_HOR";
				break;
			default:
				break;
			}
			break;

		case EDGE:
			switch (side) {
			case UP:
				name = "EXPLOSION_EDGE_UP";
				break;
			case DOWN:
				name = "EXPLOSION_EDGE_DOWN";
				break;
			case RIGHT:
				name = "EXPLOSION_EDGE_RIGHT";
				break;
			case LEFT:
				name = "EXPLOSION_EDGE_LEFT";
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}

		sprite_index = Initialization.getSpriteFromSprites(name);
		// Centro en la esquina superior izquierda
		sprite_index.setCenterX(0);
		sprite_index.setCenterY(0);
		image_speed = 0.3;
	}

	@Override
	public void create() {
	}

	@Override
<<<<<<< HEAD
	public void customStep(KEY key) {
		if (animation_end) {
			this.destroy();
=======
	public void customStep(KEY key, KEY direction) {
		if(image_index>=sprite_index.getSubimages()){
			destroy();
>>>>>>> branch 'master' of https://github.com/EquipoJP/SuperBomberman.git
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
	public void processKey(KEY key, KEY direction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void customCollision(Objeto colision) {
		// TODO Auto-generated method stub

	}

}
