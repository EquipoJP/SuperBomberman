/**
 * Class representing a temporizer
 */
package graphics.effects;

import graphics.rooms.Room;
import logic.Objeto;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class IntroTemporizer extends Objeto {

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param r
	 *            room
	 */
	public IntroTemporizer(int x, int y, Room r) {
		super(x, y, 0, r);
	}

	@Override
	public void create() {
		setAlarm(0, 80);
	}

	@Override
	public void customDestroy() {
		myRoom.addObjeto(new Fade(0, 0, myRoom, true));
	}

	@Override
	public void alarm(int alarmNo) {
		if (alarmNo == 0) {
			destroy();
		}
	}
}
