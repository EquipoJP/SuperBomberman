package logic.characters;

import java.util.List;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.Game;
import logic.Input.KEY;
import logic.Objeto;

public class Enemy extends Objeto{
	
	private int modX;
	private int modY;
	
	protected List<KEY> route;
	private int posInRoute;
	private boolean destruction;

	public Enemy(int x, int y, Room r) {
		super(x, y, r);
	}

	@Override
	public void create() {
		modX = 3;
		modY = 3;
		route = null;
		posInRoute = 0;
		destruction = false;
	}

	@Override
	public void processKey(KEY key, KEY direction) {
		if(destruction){
			if(animation_end){
				destroy();
			}
			return ;
		}
		checkCollision();
		
		if(route == null || route.size() == 0){
			return ;
		}
		
		if(posInRoute >= route.size()){
			posInRoute = 0;
		}
		if(posInRoute < 0){
			posInRoute = 0;
		}
		key = route.get(posInRoute);
		
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
		case NO_KEY:
			break;
		default:
			break;
		}
	}
	
	private void checkCollision() {
		List<Objeto> collisions = collision();
		if (collisions != null) {
			for (Objeto obj : collisions) {
				if (obj instanceof Player) {
					Game g = (Game) myRoom;
					g.callForDestruction();
					System.out.println("Player hit");
				}
				if (obj instanceof Enemy) {
					System.out.println("Enemy hit");
				}
			}
		}		
	}

	protected List<KEY> createRoute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void callForDestruction(){
		destruction = true;
		//sprite_index = ;
		image_index = 0;
		image_speed = 0.1;
	}
}
