/**
 * Class for the bomb's management
 */
package logic.characters;

import java.util.List;

import graphics.rooms.Room;
import graphics.rooms.game.Game3DRepository;
import logic.Input.KEY;
import logic.Objeto;
import logic.characters.ExplosionPart.KIND;
import logic.characters.ExplosionPart.SIDE;
import logic.collisions.BoundingBox;
import logic.collisions.Point2D;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class ExplosionManager extends Objeto {

	private int radius;
	private final int DIRECTIONS = 4;
	private final int UP = 0;
	private final int RIGHT = 1;
	private final int DOWN = 2;
	private final int LEFT = 3;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordiante
	 * @param r
	 *            room
	 * @param rad
	 *            radius
	 */
	public ExplosionManager(int x, int y, int z, Room r, int rad) {
		super(x, y, z, r);
		this.radius = rad;
		x = calculateXPosition(x);
		y = calculateYPosition(y);
		boolean[] stop = new boolean[DIRECTIONS];
		for (int i = 0; i < DIRECTIONS; i++) {
			stop[i] = false;
		}
		// Create core
		r.addObjeto(new ExplosionPart(x, y, z, r, ExplosionPart.KIND.CORE,
				ExplosionPart.SIDE.DOWN, Game3DRepository.bloque));
		// Create branches
		for (int i = 1; i <= radius; i++) {

			// In every direction
			for (int j = 0; j < DIRECTIONS; j++) {
				// If we have to continue in this direction
				if (!stop[j]) {
					// Check collision with any type of block
					int xToCheck = x;
					int yToCheck = y;
					switch (j) {
					case UP:
						xToCheck = xToCheck + (Initialization.TILE_WIDTH / 2);
						yToCheck = yToCheck - (i * Initialization.TILE_HEIGHT)
								+ (Initialization.TILE_HEIGHT / 2);
						break;
					case RIGHT:
						yToCheck = yToCheck + (Initialization.TILE_HEIGHT / 2);
						xToCheck = xToCheck + (i * Initialization.TILE_WIDTH)
								+ (Initialization.TILE_WIDTH / 2);
						break;
					case DOWN:
						xToCheck = xToCheck + (Initialization.TILE_WIDTH / 2);
						yToCheck = yToCheck + (i * Initialization.TILE_HEIGHT)
								+ (Initialization.TILE_HEIGHT / 2);
						break;
					case LEFT:
						yToCheck = yToCheck + (Initialization.TILE_HEIGHT / 2);
						xToCheck = xToCheck - (i * Initialization.TILE_WIDTH)
								+ (Initialization.TILE_WIDTH / 2);
						break;
					default:
						break;
					}
					Objeto check = checkExplosionCollision(xToCheck, yToCheck);

					// There has been collision
					if (check != null) {
						// No more in this direction
						stop[j] = true;
						// Warn destroyable stuff about destruction
						if (check instanceof DestroyableBlock) {
							DestroyableBlock db = (DestroyableBlock) check;
							db.callForDestruction();
						} else if (check instanceof Bomb) {
							Bomb bmb = (Bomb) check;
							bmb.callForDestruction();
						}
					}
					// No collision, make explosion part
					else {
						KIND k = null;
						SIDE s = null;
						int xToCreate = x;
						int yToCreate = y;
						// Do edges
						if (i == radius) {
							k = ExplosionPart.KIND.EDGE;
						}
						// Do mids
						else {
							k = ExplosionPart.KIND.MID;
						}

						// Do the proper direction
						switch (j) {
						case UP:
							s = ExplosionPart.SIDE.UP;
							yToCreate = yToCreate
									- (i * Initialization.TILE_HEIGHT);
							break;
						case RIGHT:
							s = ExplosionPart.SIDE.RIGHT;
							xToCreate = xToCreate
									+ (i * Initialization.TILE_WIDTH);
							break;
						case DOWN:
							s = ExplosionPart.SIDE.DOWN;
							yToCreate = yToCreate
									+ (i * Initialization.TILE_HEIGHT);
							break;
						case LEFT:
							s = ExplosionPart.SIDE.LEFT;
							xToCreate = xToCreate
									- (i * Initialization.TILE_WIDTH);
							break;
						default:
							break;
						}

						// Create part
						r.addObjeto(new ExplosionPart(xToCreate, yToCreate, z,
								r, k, s, Game3DRepository.bloque));
					}
				} // End of if we have to continue
			} // End of for every direction
		} // Enf of for crear branches
	}

	@Override
	public void customStep(KEY input, KEY direction) {
		destroy();
	}

	/**
	 * Checks for other objects' collision
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return collided object or null
	 */
	private Objeto checkExplosionCollision(int x, int y) {
		Objeto returned = null;
		List<Objeto> objects = myRoom.objetos;
		BoundingBox bb = new BoundingBox(new Point2D(x - 2, y - 2),
				new Point2D(x + 2, y + 2));

		for (Objeto obj : objects) {
			if (!obj.equals(this)) {
				boolean collision = BoundingBox.collision(bb, obj.boundingBox);
				if (collision
						&& (obj instanceof Block
								|| obj instanceof DestroyableBlock || obj instanceof Bomb)) {
					returned = obj;
					break;
				}
			}
		}
		return returned;
	}

	/**
	 * @param posx
	 *            x coordinate
	 * @return center of the tile (x)
	 */
	public int calculateXPosition(int posx) {
		return posx - Initialization.TILE_WIDTH / 2;
	}

	/**
	 * @param posy
	 *            y coordinate
	 * @return center of the tile (y)
	 */
	public int calculateYPosition(int posy) {
		return posy - Initialization.TILE_HEIGHT / 2;
	}
}
