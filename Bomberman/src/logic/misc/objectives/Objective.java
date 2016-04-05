package logic.misc.objectives;

import graphics.rooms.Room;
import logic.Objeto;
import logic.characters.Player;

public abstract class Objective {

	public abstract boolean test(Room room);
	
	public boolean gameOver(Room room){
		for (Objeto obj : room.objetos) {
			if (obj instanceof Player) {
				return false;
			}
		}
		return true;
	}
}
