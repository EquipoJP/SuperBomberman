package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.Game;
import graphics.D2.rooms.game.GameRepository;

import java.util.List;

import logic.Input.KEY;
import logic.Objeto;
import logic.collisions.BoundingBox;
import logic.collisions.Point2D;

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
		depth = 10;
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
		image_speed = 0.23;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		boundingBox = new BoundingBox(new Point2D(sprite_index.getCenterX(),
				sprite_index.getCenterY()), new Point2D(
				sprite_index.getCenterX() + sprite_index.getWidth(),
				sprite_index.getCenterY() + sprite_index.getHeight()));
		
		boundingBox.update(x, y);

		List<Objeto> collisions = collision();
		if (collisions != null) {
			for (Objeto obj : collisions) {
				if (obj instanceof Player) {
					Game g = (Game) myRoom;
					g.callForDestruction();
					System.out.println("Player hit");
				}
				if (obj instanceof Enemy) {
					Enemy en = (Enemy) obj;
					en.callForDestruction();
					System.out.println("Enemy hit");
				}
			}
		}

		boundingBox = null;
		if (animation_end) {
			this.destroy();
		}
	}
}
