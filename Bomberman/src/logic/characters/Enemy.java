package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.Game;

import java.util.List;
import java.util.Random;

import logic.Input.KEY;
import logic.Objeto;

public class Enemy extends Objeto{
	
	private int modX;
	private int modY;
	
	private KEY movActual;
	private Random randomizer;
	
	private boolean destruction;

	public Enemy(int x, int y, Room r) {
		super(x, y, r);
	}

	@Override
	public void create() {
		modX = 2;
		modY = 2;
		
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
			break;
		case UP:
			ymod = -modY;
			break;
		case LEFT:
			xmod = -modX;
			break;
		case RIGHT:
			xmod = modX;
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
		destruction = true;
		//sprite_index = ;
		image_index = 0;
		image_speed = 0.1;
	}
}
