package logic;

import logic.Input.KEY;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customStep(KEY key) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
