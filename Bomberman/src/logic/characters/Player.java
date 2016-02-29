package logic.characters;

import java.util.Map;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
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
		sprites = Initialization.getSprites(Initialization.SPRITES[0]);
		sprite_index = sprites.get(Initialization.SPRITE_NAMES[0]);	// idle
		
		modX = 5;
		modY = 5;
		
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
	}

	@Override
	public void alarm(int alarmNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processKey(KEY key) {
		switch(key){
		case DOWN:
			y = tryToMoveY(modY);
			break;
		case UP:
			y = tryToMoveY(-modY);
			break;
		case LEFT:
			x = tryToMoveX(-modX);
			break;
		case RIGHT:
			x = tryToMoveX(modX);
			break;
		case SPACE:
			putBomb();
			break;
		default:
			break;
		}
	}
	
	private int tryToMoveY(int distance){
		int _y = y + distance;
		// TODO check collision
		
		return _y;
	}
	
	private int tryToMoveX (int distance){
		int _x = x + distance;
		// TODO check collision
		
		return _x;
	}
	
	private void putBomb(){
		if(bombs < bombsLimit){
			//TODO create a bomb if there is free space
		}
	}


}
