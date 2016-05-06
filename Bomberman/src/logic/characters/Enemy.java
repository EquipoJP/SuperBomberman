/**
 * Class representing an enemy
 */
package logic.characters;

import graphics.rooms.Room;
import graphics.rooms.game.GameRepository;

import java.util.List;
import java.util.Map;
import java.util.Random;

import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.collisions.PerspectiveBoundingBox;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Enemy extends Objeto {

	private int modX;
	private int modY;

	private KEY movActual;
	private Random randomizer;

	private boolean destruction;

	Map<String, Sprite> sprites;

	/**
	 * Creates a new enemy
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param r
	 *            room
	 */
	public Enemy(int x, int y, int z, Room r, graphics.d3.objetos.Objeto d3Object) {
		super(x, y, z, r);
		sprites = GameRepository.enemy;
		image_speed = 0.1;

		sprite_index = sprites.get(Initialization.ENEMIES_SPRS[2]);
		boundingBox = PerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		super.d3Object = d3Object;
	}

	@Override
	public void create() {
		modX = 1;
		modY = 1;

		destruction = false;

		movActual = KEY.NO_KEY;
		randomizer = new Random();
	}

	@Override
	public void processKey(KEY key, KEY direction) {
		if (destruction) {
			if (animation_end) {
				destroy();
			}
			return;
		}

		if (movActual == KEY.NO_KEY) {
			movActual = randomizeKey();
		}

		int xmod = 0;
		int ymod = 0;

		switch (movActual) {
		case DOWN:
			ymod = modY;
			sprite_index = sprites.get(Initialization.ENEMIES_SPRS[2]);
			break;
		case UP:
			ymod = -modY;
			sprite_index = sprites.get(Initialization.ENEMIES_SPRS[1]);
			break;
		case LEFT:
			xmod = -modX;
			sprite_index = sprites.get(Initialization.ENEMIES_SPRS[2]);
			break;
		case RIGHT:
			xmod = modX;
			sprite_index = sprites.get(Initialization.ENEMIES_SPRS[1]);
			break;
		case NO_KEY:
			break;
		default:
			break;
		}

		checkCollision(xmod, ymod);

		if (!tryToMove(xmod, ymod)) {
			movActual = KEY.NO_KEY;
		}
	}

	/**
	 * @return random key (L, R, U, D)
	 */
	private KEY randomizeKey() {
		/*
		 * 0. DOWN 1. UP 2. LEFT 3. RIGHT
		 */

		switch (randomizer.nextInt(4)) {
		case 0:
			return KEY.DOWN;
		case 1:
			return KEY.UP;
		case 2:
			return KEY.LEFT;
		case 3:
			return KEY.RIGHT;
		}

		return null;
	}

	/**
	 * Checks for collisions with player
	 * 
	 * @param modx
	 *            x update
	 * @param mody
	 *            y update
	 */
	private void checkCollision(int modx, int mody) {
		boundingBox.update(modx, mody);
		List<Objeto> collisions = collision();
		boundingBox.update(-modx, -mody);
		if (collisions != null) {
			for (Objeto obj : collisions) {
				if (obj instanceof Player) {
					Player player = (Player) obj;
					player.callForDestruction();
					// System.out.println("Player hit");
				}
			}
		}
	}

	/**
	 * Call for the destruction of the enemy
	 */
	public void callForDestruction() {
		if (!destruction) {
			resetAnimationEnd();
			destruction = true;
			sprite_index = sprites.get(Initialization.ENEMIES_SPRS[0]);
			image_index = 0;
			image_speed = 0.1;
		}
	}

	@Override
	public boolean tryToMove(int modX, int modY) {
		boundingBox.update(modX, modY);
		List<Objeto> collisions = collision();
		if (collisions != null) {
			boolean returned = true;
			for (int i = 0; i < collisions.size() && returned; i++) {
				if (collisions.get(i) instanceof Block
						|| collisions.get(i) instanceof DestroyableBlock
						|| collisions.get(i) instanceof Bomb) {
					returned = false;
				}
			}
			if (!returned) {
				boundingBox.update(-modX, -modY);
			} else {
				x = x + modX;
				y = y + modY;
				// TODO modificar traslacion 3d
			}
			return returned;
		} else {
			x = x + modX;
			y = y + modY;
			// TODO modificar traslacion 3d
			return true;
		}
	}
}
