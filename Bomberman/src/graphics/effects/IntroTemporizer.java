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
		myRoom.addObjeto(new FadeOut(0,0,myRoom));
	}

	@Override
	public void customStep() {
		// Nothing
	}

	@Override
	public void alarm(int alarmNo) {
		if(alarmNo == 0){
			destroy();
		}
	}

	@Override
	public void processKey(KEY key) {
		// Nothing
	}

}
