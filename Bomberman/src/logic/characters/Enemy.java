package logic.characters;

import graphics.D2.rooms.Room;

import java.util.List;

import logic.Input.KEY;
import logic.Objeto;

public class Enemy extends Objeto{
	
	private int modX;
	private int modY;
	
	protected List<KEY> route;
	private int posInRoute;

	public Enemy(int x, int y, Room r) {
		super(x, y, r);
	}

	@Override
	public void create() {
		modX = 3;
		modY = 3;
		route = null;
		posInRoute = 0;
	}

	@Override
	public void customStep(KEY key) {
		// TODO
		
	}

	@Override
	public void alarm(int alarmNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processKey(KEY key) {
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
	
	protected List<KEY> createRoute() {
		// TODO Auto-generated method stub
		return null;
	}

}
