package logic.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import graphics.D2.rooms.Room;
import logic.characters.ExplosionPart.KIND;
import logic.characters.ExplosionPart.SIDE;
import main.Initialization;
import logic.Objeto;
import logic.collisions.BoundingBox;
import logic.collisions.Point2D;

public class ExplosionManager extends Objeto {

	private int radius;
	private final int DIRECTIONS = 4;
	private final int UP = 0;
	private final int RIGHT = 1;
	private final int DOWN = 2;
	private final int LEFT = 3;
	private LinkedList<BoundingBox> bbs;

	public ExplosionManager(int x, int y, Room r, int rad) {
		super(x, y, r);
		bbs = new LinkedList<BoundingBox>();
		this.radius = rad;
		x = calculateXPosition(x);
		y = calculateYPosition(y);
		boolean[] stop = new boolean[DIRECTIONS];
		for (int i = 0; i < DIRECTIONS; i++) {
			stop[i] = false;
		}
		// Crear core
		BoundingBox b = new BoundingBox(new Point2D(x, y), new Point2D(x + 32, y + 32));
		bbs.add(b);
		r.addObjeto(new ExplosionPart(x, y, r, ExplosionPart.KIND.CORE, ExplosionPart.SIDE.DOWN));
		// Crear branches
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
						yToCheck = yToCheck - (i * Initialization.TILE_HEIGHT) + (Initialization.TILE_HEIGHT / 2);
						break;
					case RIGHT:
						yToCheck = yToCheck + (Initialization.TILE_HEIGHT / 2);
						xToCheck = xToCheck + (i * Initialization.TILE_WIDTH) + (Initialization.TILE_WIDTH / 2);
						break;
					case DOWN:
						xToCheck = xToCheck + (Initialization.TILE_WIDTH / 2);
						yToCheck = yToCheck + (i * Initialization.TILE_HEIGHT) + (Initialization.TILE_HEIGHT / 2);
						break;
					case LEFT:
						yToCheck = yToCheck + (Initialization.TILE_HEIGHT / 2);
						xToCheck = xToCheck - (i * Initialization.TILE_WIDTH) + (Initialization.TILE_WIDTH / 2);
						break;
					default:
						break;
					}
					Objeto check = checkWallOrBlockCollision(xToCheck, yToCheck);

					// There has been collision
					if (check != null) {
						// No more in this direction
						stop[j] = true;
						// Warn destroyable block about destruction
						if (check instanceof DestroyableBlock) {
							DestroyableBlock db = (DestroyableBlock) check;
							db.callForDestruction();
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
							yToCreate = yToCreate - (i * Initialization.TILE_HEIGHT);
							break;
						case RIGHT:
							s = ExplosionPart.SIDE.RIGHT;
							xToCreate = xToCreate + (i * Initialization.TILE_WIDTH);
							break;
						case DOWN:
							s = ExplosionPart.SIDE.DOWN;
							yToCreate = yToCreate + (i * Initialization.TILE_HEIGHT);
							break;
						case LEFT:
							s = ExplosionPart.SIDE.LEFT;
							xToCreate = xToCreate - (i * Initialization.TILE_WIDTH);
							break;
						default:
							break;
						}

						// Create part
						r.addObjeto(new ExplosionPart(xToCreate, yToCreate, r, k, s));
					}
				} // End of if we have to continue
			} // End of for every direction
		} // Enf of for crear branches
	}

	@Override
	public void render(Graphics g) {
		for (BoundingBox b : bbs) {
			g.setColor(Color.red);
			g.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
			g.setColor(Color.black);
		}
	}

	public int calculateXPosition(int posx) {
		// Le sumamos la cuarta parte de la width para mejorar la ubicacion
		posx = posx + Initialization.TILE_WIDTH / 2;
		// Retiramos el offset
		posx = posx - Initialization.MAP_X_OFFSET;
		// Obtenemos la casilla en x en la que está
		posx = posx / Initialization.TILE_WIDTH;
		// Multiplicamos por la tile para obtener su posicion en x
		posx = posx * Initialization.TILE_WIDTH;
		// Sumamos el offset
		posx = posx + Initialization.MAP_X_OFFSET;
		// Quitamos la mitad de la tile para obtener la esquina de la tile
		posx = posx - Initialization.TILE_WIDTH/2;
		// Devolvemos
		return posx;
	}

	public int calculateYPosition(int posy) {
		// Le sumamos la height para mejorar la ubicacion
		posy = posy + Initialization.TILE_HEIGHT;
		// Retiramos el offset
		posy = posy - Initialization.MAP_Y_OFFSET;
		// Obtenemos la casilla en x en la que está
		posy = posy / Initialization.TILE_HEIGHT;
		// Multiplicamos por la tile para obtener su posicion en x
		posy = posy * Initialization.TILE_HEIGHT;
		// Sumamos el offset
		posy = posy + Initialization.MAP_Y_OFFSET;
		// Quitamos la mitad de la tile para obtener la esquina de la tile
		posy = posy - Initialization.TILE_HEIGHT/2;
		// Devolvemos
		return posy;
	}

	private Objeto checkWallOrBlockCollision(int x, int y) {
		Objeto returned = null;
		List<Objeto> objects = myRoom.objetos;
		BoundingBox bb = new BoundingBox(new Point2D(x - 2, y - 2), new Point2D(x + 2, y + 2));
		bbs.add(bb);

		for (Objeto obj : objects) {
			if (!obj.equals(this)) {
				boolean collision = BoundingBox.collision(bb, obj.boundingBox);
				if (collision && (obj instanceof Block || obj instanceof DestroyableBlock)) {
					returned = obj;
					break;
				}
			}
		}
		return returned;
	}
}
