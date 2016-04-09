/**
 * Class representing the stairs
 */
package logic.characters;

import graphics.rooms.Room;
import graphics.rooms.game.GameRepository;
import logic.Objeto;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Stairs extends Objeto {

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param r
	 *            room
	 */
	public Stairs(int x, int y, int z, Room r) {
		super(x, y, z, r);

		sprite_index = GameRepository.stairs;
		image_speed = 0;
		image_index = 0;
	}

}
