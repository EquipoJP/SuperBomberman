package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;
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
		animation_end = false;
		kind = k;
		side = s;
		switch (kind) {

		case CORE:
			sprite_index = GameRepository.coreExplosion;
			break;

		case MID:
			switch (side) {
			case UP:
			case DOWN:
				sprite_index = GameRepository.midVerExplosion;
				break;
			case LEFT:
			case RIGHT:
				sprite_index = GameRepository.midHorExplosion;
				break;
			default:
				break;
			}
			break;

		case EDGE:
			switch (side) {
			case UP:
				sprite_index = GameRepository.edgeUpExplosion;
				break;
			case DOWN:
				sprite_index = GameRepository.edgeDownExplosion;
				break;
			case RIGHT:
				sprite_index = GameRepository.edgeRightExplosion;
				break;
			case LEFT:
				sprite_index = GameRepository.edgeLeftExplosion;
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}

		// Centro en la esquina superior izquierda
		sprite_index.setCenterX(0);
		sprite_index.setCenterY(0);
		image_speed = 0.2;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if (animation_end) {
			this.destroy();
		}
	}
}
