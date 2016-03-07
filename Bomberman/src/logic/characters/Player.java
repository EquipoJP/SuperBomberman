package logic.characters;

import java.util.Map;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.collisions.PerspectiveBoundingBox;
import main.Initialization;

public class Player extends Objeto{
	
	/* Info to get Sprites */
	private Map<String, Sprite> sprites;
	
	private int modX;
	private int modY;
	
	private int bombs;
	private int bombsLimit;
	
	public Player(int x, int y, Room r, int depth) {
		super(x, y, r);
	}
	
	@Override
	public void create() {
		sprites = Initialization.getSprites(Initialization.SPRITES.WHITE_BOMBER.toString());
		sprite_index = sprites.get(Initialization.SPRITE_NAMES[0]);	// idle
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
	public void customStep(KEY key) {
		switch(key){
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
		case SPACE:
			sprite_index = sprites.get(Initialization.SPRITE_NAMES[0]);
			break;
		case NO_KEY:
			sprite_index = sprites.get(Initialization.SPRITE_NAMES[0]);
			break;
		default:
			break;
		}
		
		// change speed
		if(sprite_index.equals(sprites.get(Initialization.SPRITE_NAMES[0]))){
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
	public void processKey(KEY key) {
		switch(key){
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
		case SPACE:
			putBomb();
			break;
		case NO_KEY:
			break;
		default:
			break;
		}
	}
	
	private void putBomb(){
		if(bombs < bombsLimit){
			//TODO create a bomb if there is free space
		}
	}


}
