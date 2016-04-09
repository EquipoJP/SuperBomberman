/**
 * Class representing a visual (object with no functionality)
 */
package graphics.effects;

import graphics.rooms.Room;
import logic.Objeto;
import logic.Sprite;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Visual extends Objeto {

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param r
	 *            room
	 * @param spr
	 *            sprite to use
	 */
	public Visual(int x, int y, Room r, Sprite spr) {
		super(x, y, 0, r);

		sprite_index = spr;

		if (sprite_index.getSubimages() == 0) {
			image_speed = 0;
		} else {
			image_speed = 0.05;
		}
	}
}
