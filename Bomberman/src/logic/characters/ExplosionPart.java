/**
 * Class representing a single tile explosion
 */
package logic.characters;

import java.util.List;

import graphics.rooms.Room;
import graphics.rooms.game.Game;
import graphics.rooms.game.GameRepository;
import logic.Input.KEY;
import logic.Objeto;
import logic.collisions.BoundingBox;
import logic.collisions.Point2D;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class ExplosionPart extends Objeto {

	/* enumerations */
	public enum KIND {
		CORE, MID, EDGE
	};

	public enum SIDE {
		UP, DOWN, LEFT, RIGHT
	};

	/* attributes */
	private KIND kind;
	private SIDE side;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param r
	 *            room
	 * @param k
	 *            kind of explosion part
	 * @param s
	 *            side of explosion part
	 */
	public ExplosionPart(int x, int y, int z, Room r, KIND k, SIDE s, graphics.d3.objetos.Objeto d3Object) {
		super(x, y, z, r);
		super.d3Object = d3Object.clone();
		// traslacion 3d
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

		// centered on the left-up corner
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
					// System.out.println("Player hit");
				}
				if (obj instanceof Enemy) {
					Enemy en = (Enemy) obj;
					en.callForDestruction();
					// System.out.println("Enemy hit");
				}
				if (obj instanceof Item) {
					Item it = (Item) obj;
					it.callForDestruction();
					// System.out.println("Item hit");
				}
			}
		}

		boundingBox = null;
		if (animation_end) {
			this.destroy();
		}
	}
}
