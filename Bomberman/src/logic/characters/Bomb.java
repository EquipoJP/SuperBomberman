package logic.characters;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import main.Game;
import logic.Objeto;

public class Bomb extends Objeto {

	private static final int SECONDS = 3;
	private int radius = 1;
	private Player player;

	public Bomb(int x, int y, Room r, int radius, Player player) {
		super(x, y, r);
		this.radius = radius;
		this.player = player;
	}

	@Override
	public void create() {
		System.out.println(SECONDS * (int) Game.FPS);
		setAlarm(0, SECONDS * (int) Game.FPS);
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void alarm(int alarmNo) {
		// TODO Auto-generated method stub
		switch (alarmNo) {
		case 0:
			System.out.println("Alarm");
			destroyBomb();
			break;
		default:
			break;
		}
	}

	@Override
	public void customDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void processKey(KEY key, KEY direction) {
		// TODO Auto-generated method stub

	}
	
	private void destroyBomb(){
		ExplosionManager em = new ExplosionManager(x, y, myRoom, radius);
		myRoom.addObjeto(em);
		player.bombs--;
		destroy();
	}

}
