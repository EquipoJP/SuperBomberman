/**
 * Class that represents an objective whose goal is to kill every enemy on the room
 */
package logic.misc.objectives;

import graphics.rooms.Room;
import logic.Objeto;
import logic.characters.Enemy;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class KillEnemiesObjective extends Objective {

	@Override
	public boolean test(Room room) {
		for (Objeto obj : room.objetos) {
			if (obj instanceof Enemy) {
				return false;
			}
		}
		return true;
	}

}
