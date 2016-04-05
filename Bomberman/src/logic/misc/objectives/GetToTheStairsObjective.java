package logic.misc.objectives;

import graphics.rooms.Room;
import logic.Objeto;
import logic.characters.Enemy;

public class GetToTheStairsObjective extends Objective{

	@Override
	public boolean test(Room room) {
		for (Objeto obj : room.objetos) {
			if (obj instanceof Enemy) {
				return false;
			}
		}
		
		// Check if player got to the stairs
		// TODO
		return true;
	}
	
}
