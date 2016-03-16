package logic.misc;

import logic.Input.KEY;
import logic.Objeto;

public class Level extends Objeto{
	
	public int mapInitX, mapInitY;
	public int mapWidth, mapHeight;
	
	public Level(int x, int y, int w, int h){
		super(x, y, null);
		mapInitX = x;
		mapInitY = y;
		
		mapWidth = w;
		mapHeight = h;
	}

	@Override
	public void create() {
	}

	@Override
	public void customStep(KEY key) {
	}

	@Override
	public void alarm(int alarmNo) {
	}

	@Override
	public void customDestroy() {
	}

	@Override
	public void processKey(KEY key) {
	}

	@Override
	public void customCollision(Objeto colision) {
		// TODO Auto-generated method stub
		
	}

}
