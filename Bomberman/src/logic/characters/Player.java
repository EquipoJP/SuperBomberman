package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;

import java.util.Map;

import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.collisions.BoundingBox;
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
		sprites = GameRepository.player;
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

		if (!keyed) {
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
			System.out.println("SPACE");
			putBomb();
			break;
		default:
			break;
		}
	}

	private void putBomb() {
		if (bombs < bombsLimit) {
			KEY key = getDirectionFromSprite();
			
			//center of the tile in which the player is standing
			int row = logic.misc.Map.getRow(x);
			int col = logic.misc.Map.getRow(y);
			
			System.out.println("Initial: " + row + " " + col);
			
			switch(key){
			case UP:
				row--;
				break;
			case DOWN:
				row++;
				break;
			case RIGHT:
				col++;
				break;
			case LEFT:
				col--;
				break;
			default:
				return ;
			}
			
			System.out.println("After: " + row + " " + col);
			
			int _x = logic.misc.Map.getX(row);
			int _y = logic.misc.Map.getX(col);
			
			System.out.println("X-X'': " + x + "-" + _x + " <--> Y-Y'': " + y + "-" + _y);
			
			if(!checkCollision(_x, _y)){
				Bomb bomb = new Bomb(_x, _y, myRoom,
						bombRadius, this);
				myRoom.addObjeto(bomb);
				bombs++;
			}
		}
	}
	
	private boolean checkCollision(int x, int y){
		BoundingBox bb = new BoundingBox(new Point2D(x - Initialization.TILE_HEIGHT/2, y - Initialization.TILE_WIDTH/2), 
				new Point2D(x + Initialization.TILE_HEIGHT/2, y + Initialization.TILE_WIDTH/2));
		for (Objeto obj : myRoom.objetos) {
			if (!obj.equals(this)) {
				boolean collision = BoundingBox.collision(bb, obj.boundingBox);
				if (collision) {
					return true;
				}
			}
		}
		return false;
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
