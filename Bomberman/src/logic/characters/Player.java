package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;

import java.util.List;
import java.util.Map;

import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.collisions.BoundingBox;
import logic.collisions.PerspectiveBoundingBox;
import logic.collisions.Point2D;
import main.Game;
import main.Initialization;

public class Player extends Objeto {

	/* Info to get Sprites */
	private Map<String, Sprite> sprites;

	private int modX;
	private int modY;
	
	private boolean destruction;

	public int bombs;
	public int bombRadius = 1;
	public int bombsLimit;

	public Player(int x, int y, Room r, int depth) {
		super(x, y, r);
	}

	@Override
	public void create() {
		sprites = GameRepository.player;
		sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[0]); // idle
		
		boundingBox = PerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		System.out.println("PLAYER " + boundingBox);

		modX = 2;
		modY = 2;

		bombs = 0;
		bombsLimit = 1;
		
		destruction = false;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if(!destruction){
			boolean keyed = false;
			switch (key) {
			case SPACE:
				keyed = true;
				unsetAlarm(0);
				break;
			default:
				break;
			}
	
			boolean stop = false;
	
			if (!keyed) {
				switch (direction) {
				case DOWN:
					sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[1]);
					unsetAlarm(0);
					break;
				case UP:
					sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[4]);
					unsetAlarm(0);
					break;
				case LEFT:
					sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[3]);
					unsetAlarm(0);
					break;
				case RIGHT:
					sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[2]);
					unsetAlarm(0);
					break;
				case NO_KEY:
					stop = true;
					if(!isAlarmSet(0) && sprite_index != sprites.get(Initialization.BOMBERMAN_SPRS[0])){
						int seconds = 5;
						setAlarm(0, seconds * (int) Game.FPS);
					}
					break;
				default:
					break;
				}
			}
	
			// change speed
			if (sprite_index.equals(sprites.get(Initialization.BOMBERMAN_SPRS[0]))) {
				image_speed = 0.1;
			} else if (stop) {
				image_speed = 0;
				image_index = 0;
			} else {
				image_speed = 0.2;
			}
		}
		else{
			if(animation_end){
				destroy();
			}
		}
	}

	@Override
	public void processKey(KEY key, KEY direction) {
		destroyCollisions();
		if(!destruction){
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
	}
	
	private void destroyCollisions(){
		List<Objeto> collision = collision();
		if(collision != null){
			for(Objeto obj : collision){
				if(obj instanceof Block || obj instanceof DestroyableBlock || obj instanceof Bomb){
					int row = logic.misc.Map.getRow(y);
					int col = logic.misc.Map.getCol(x);
					
					boolean up = collisionUp(row, col);
					if(up){
						return ;
					}
					
					boolean down = collisionDown(row, col);
					if(down){
						return ;
					}
					
					boolean right = collisionRight(row, col);
					if(right){
						return ;
					}
					
					boolean left = collisionLeft(row, col);
					if(left){
						return ;
					}
					
					boolean noMove = collisionNoMove(row, col);
					if(noMove){
						return ;
					}
				}
			}
		}
	}

	private boolean collisionNoMove(int row, int col) {
		int _x = logic.misc.Map.getX(col);
		int _y = logic.misc.Map.getY(row);
		return tryToMove(_x - x, _y - y);
	}

	private boolean collisionUp(int row, int col) {
		row--;
		return collisionNoMove(row, col);
	}

	private boolean collisionDown(int row, int col) {
		row++;
		return collisionNoMove(row, col);
	}

	private boolean collisionRight(int row, int col) {
		col++;
		return collisionNoMove(row, col);
	}

	private boolean collisionLeft(int row, int col) {
		col--;
		return collisionNoMove(row, col);
	}

	@Override
	public void alarm(int alarmNo) {
		switch (alarmNo) {
		case 0:
			sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[0]);
			image_index = 0;
			image_speed = 0.1;
			break;
		default:
			break;
		}
	}

	private void putBomb() {
		if (bombs < bombsLimit) {
			KEY key = getDirectionFromSprite();

			// center of the tile in which the player is standing
			int row = logic.misc.Map.getRow(y);
			int col = logic.misc.Map.getCol(x);

			switch (key) {
			case UP:
				System.out.println("UP");
				row--;
				break;
			case DOWN:
				System.out.println("DOWN");
				row++;
				break;
			case RIGHT:
				System.out.println("RIGHT");
				col++;
				break;
			case LEFT:
				System.out.println("LEFT");
				col--;
				break;
			default:
				return;
			}

			int _x = logic.misc.Map.getX(col);
			int _y = logic.misc.Map.getY(row);

			if (!checkCollision(_x, _y)) {
				Bomb bomb = new Bomb(_x, _y, myRoom, bombRadius, this);
				myRoom.addObjeto(bomb);
				bombs++;
			}
		}
	}

	private boolean checkCollision(int x, int y) {
		BoundingBox bb = new BoundingBox(new Point2D(x
				- Initialization.TILE_HEIGHT / 4, y - Initialization.TILE_WIDTH
				/ 4), new Point2D(x + Initialization.TILE_HEIGHT / 4, y
				+ Initialization.TILE_WIDTH / 4));
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
		if (sprite_index == sprites.get(Initialization.BOMBERMAN_SPRS[1])) {
			return KEY.DOWN;
		}
		if (sprite_index == sprites.get(Initialization.BOMBERMAN_SPRS[4])) {
			return KEY.UP;
		}
		if (sprite_index == sprites.get(Initialization.BOMBERMAN_SPRS[3])) {
			return KEY.LEFT;
		}
		if (sprite_index == sprites.get(Initialization.BOMBERMAN_SPRS[2])) {
			return KEY.RIGHT;
		}
		if (sprite_index == sprites.get(Initialization.BOMBERMAN_SPRS[0])) {
			return KEY.DOWN;
		}

		return KEY.NO_KEY;
	}
	
	public void callForDestruction(){
		destruction = true;
		sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[0]);
		image_index = 0;
		image_speed = 0.1;
	}
}
