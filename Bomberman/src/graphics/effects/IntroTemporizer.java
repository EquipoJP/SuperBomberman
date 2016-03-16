package graphics.effects;

import java.awt.Graphics;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;

public class IntroTemporizer extends Objeto{

	public IntroTemporizer(int x, int y, Room r) {
		super(x, y, r);
	}

	@Override
	public void create() {
		setAlarm(0,80);
	}

	@Override
	public void render(Graphics g) {
		// Nothing
	}

	@Override
	public void customDestroy() {
		myRoom.addObjeto(new Fade(0,0,myRoom,true));
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		// Nothing
	}

	@Override
	public void alarm(int alarmNo) {
		if(alarmNo == 0){
			destroy();
		}
	}

	@Override
	public void processKey(KEY key, KEY direction) {
		// Nothing
	}

	@Override
	public void customCollision(Objeto colision) {
		// TODO Auto-generated method stub
		
	}

}
