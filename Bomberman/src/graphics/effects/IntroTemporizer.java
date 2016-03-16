package graphics.effects;

import graphics.D2.rooms.Room;
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
	public void customDestroy() {
		myRoom.addObjeto(new Fade(0,0,myRoom,true));
	}

	@Override
	public void alarm(int alarmNo) {
		if(alarmNo == 0){
			destroy();
		}
	}
}
