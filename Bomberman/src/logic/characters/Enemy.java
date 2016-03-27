package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.Game;
import graphics.D2.rooms.game.GameRepository;

import java.util.List;
import java.util.Map;
import java.util.Random;

import logic.Input.KEY;
import logic.collisions.PerspectiveBoundingBox;
import logic.Objeto;
import logic.Sprite;
import main.Initialization;
import main.Initialization.STAGE;

public class Enemy extends Objeto{
	
	private int modX;
	private int modY;
	
	private KEY movActual;
	private Random randomizer;
	
	private boolean destruction;
	
	Map<String, Sprite> sprites;

	public Enemy(int x, int y, Room r, STAGE stage) {
		super(x, y, r);
		sprites = GameRepository.enemy;
		image_speed = 0.1;
		
		sprite_index = sprites.get(Initialization.ENEMIES_SPRS[2]);
		boundingBox = PerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
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
		if(destruction){
			if(animation_end){
				destroy();
			}
			return ;
		}

		if(movActual == KEY.NO_KEY){
			movActual = randomizeKey();
		}
		
		int xmod = 0;
		int ymod = 0;
		
		switch(movActual){
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
		
		if(!tryToMove(xmod, ymod)){
			movActual = KEY.NO_KEY;
		}
	}
	
	private KEY randomizeKey() {
		/*
		 * 0. DOWN
		 * 1. UP
		 * 2. LEFT
		 * 3. RIGHT
		 */
		
		switch(randomizer.nextInt(4)){
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

	private void checkCollision(int modx, int mody) {
		boundingBox.update(modx, mody);
		List<Objeto> collisions = collision();
		boundingBox.update(-modx, -mody);
		if (collisions != null) {
			for (Objeto obj : collisions) {
				if (obj instanceof Player) {
					Game g = (Game) myRoom;
					g.callForDestruction();
					System.out.println("Player hit");
				}
			}
		}		
	}

	public void callForDestruction(){
		if(!destruction){
			resetAnimationEnd();
			destruction = true;
			sprite_index = sprites.get(Initialization.ENEMIES_SPRS[0]);
			image_index = 0;
			image_speed = 0.1;
		}
	}
}
