package logic.characters;

import graphics.D2.rooms.Room;

import java.util.Map;

import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.collisions.PerspectiveBoundingBox;
import logic.collisions.Point2D;
import main.Initialization;

public class Player extends Objeto {

	/* Info to get Sprites */
	private Map<String, Sprite> sprites;

	private int modX;
	private int modY;

	public int bombs;
	public int bombRadius = 1;
	public int bombsLimit;

	public Player(int x, int y, Room r, int depth) {
		super(x, y, r);
	}

	@Override
	public void create() {
		sprites = Initialization.getSprites(Initialization.SPRITES.WHITE_BOMBER
				.toString());
		sprite_index = sprites.get(Initialization.SPRITE_NAMES[0]); // idle
		boundingBox = PerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		System.out.println("PLAYER " + boundingBox);

		modX = 2;
		modY = 2;

		bombs = 0;
		bombsLimit = 1;
	}

	@Override
	public void customDestroy() {

	}

	@Override
	public void customStep(KEY key, KEY direction) {
		boolean keyed = false;
		switch (key) {
		case SPACE:
			keyed = true;
			sprite_index = sprites.get(Initialization.SPRITE_NAMES[0]);
			break;
		default:
			break;
		}

		if(!keyed){
			switch (direction) {
			case DOWN:
				sprite_index = sprites.get(Initialization.SPRITE_NAMES[1]);
				break;
			case UP:
				sprite_index = sprites.get(Initialization.SPRITE_NAMES[4]);
				break;
			case LEFT:
				sprite_index = sprites.get(Initialization.SPRITE_NAMES[3]);
				break;
			case RIGHT:
				sprite_index = sprites.get(Initialization.SPRITE_NAMES[2]);
				break;
			case NO_KEY:
				sprite_index = sprites.get(Initialization.SPRITE_NAMES[0]);
				break;
			default:
				break;
			}
		}

		// change speed
		if (sprite_index.equals(sprites.get(Initialization.SPRITE_NAMES[0]))) {
			image_speed = 0.1;
		} else {
			image_speed = 0.2;
		}
	}

	@Override
	public void alarm(int alarmNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processKey(KEY key, KEY direction) {
		switch (direction) {
		case DOWN:
			tryToMove(0, modY);
			break;
		case UP:
			tryToMove(0, -modY);
			break;
		case LEFT:
			tryToMove(-modX, 0);
			break;
		case RIGHT:
			tryToMove(modX, 0);
			break;
		case NO_KEY:
			break;
		default:
			break;
		}

		switch (key) {
		case SPACE:
			putBomb();
			break;
		default:
			break;
		}
	}

	private void putBomb() {
		if (bombs < bombsLimit) {
			KEY key = getDirectionFromSprite();
			Point2D position = null;

			int row = logic.misc.Map.getRow(x);
			int col = logic.misc.Map.getCol(y);

			switch (key) {
			case DOWN:
				position = new Point2D(logic.misc.Map.getX(row),
						logic.misc.Map.getY(col + 1));
				break;
			case UP:
				position = new Point2D(logic.misc.Map.getX(row),
						logic.misc.Map.getY(col - 1));
				break;
			case LEFT:
				position = new Point2D(logic.misc.Map.getX(row - 1),
						logic.misc.Map.getY(col));
				break;
			case RIGHT:
				position = new Point2D(logic.misc.Map.getX(row + 1),
						logic.misc.Map.getY(col + 1));
				break;
			default:
				break;
			}

			if (position == null) {
				return;
			}

			Point2D min = new Point2D(position.getX()
					- Initialization.TILE_WIDTH / 2, position.getY()
					- Initialization.TILE_HEIGHT / 2);
			Point2D max = new Point2D(position.getX()
					+ Initialization.TILE_WIDTH / 2, position.getY()
					+ Initialization.TILE_HEIGHT / 2);

			for (Objeto obj : myRoom.objetos) {
				if (obj.depth == this.depth) {
					int x = obj.x;
					int y = obj.y;

					if (x >= min.getX() && x <= max.getX() && y >= min.getY()
							&& y <= max.getY()) {
						// the object is on the tile
						System.out.println(x + ", " + y + " - " + min + " - "
								+ max + " - " + obj);
						return;
					}
				}
			}

			Bomb bomb = new Bomb(position.getX(), position.getY(), myRoom,
					bombRadius, this);
			myRoom.addObjeto(bomb);
			bombs++;
		}
	}

	private KEY getDirectionFromSprite() {
		if (sprite_index == sprites.get(Initialization.SPRITE_NAMES[1])
				|| sprite_index == sprites.get(Initialization.SPRITE_NAMES[0])) {
			return KEY.DOWN;
		}
		if (sprite_index == sprites.get(Initialization.SPRITE_NAMES[4])) {
			return KEY.UP;
		}
		if (sprite_index == sprites.get(Initialization.SPRITE_NAMES[3])) {
			return KEY.LEFT;
		}
		if (sprite_index == sprites.get(Initialization.SPRITE_NAMES[2])) {
			return KEY.RIGHT;
		}

		return KEY.NO_KEY;
	}

}
