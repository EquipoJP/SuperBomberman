package logic.misc.objectives;

import graphics.rooms.Room;
import logic.Objeto;
import logic.characters.Enemy;

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
