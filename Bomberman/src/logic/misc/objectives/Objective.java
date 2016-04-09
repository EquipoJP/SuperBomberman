/**
 * Class that represents an objective
 */
package logic.misc.objectives;

import graphics.rooms.Room;
import logic.Objeto;
import logic.characters.Player;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public abstract class Objective {

	public abstract boolean test(Room room);

	/**
	 * Method to check if the game is over and the player defeated
	 * 
	 * @param room
	 *            room to check
	 * @return true if the player has been defeated
	 */
	public boolean gameOver(Room room) {
		for (Objeto obj : room.objetos) {
			if (obj instanceof Player) {
				return false;
			}
		}
		return true;
	}
}
