/**
 * Class representing a visual that renders some fixed text.
 */
package graphics.effects;

import java.awt.Graphics;

import graphics.rooms.Room;
import logic.collisions.Point2D;
import utils.PaintService;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class PintableButton extends Button {

	public String key;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param r
	 *            room
	 * @param key
	 *            text to render
	 */
	public PintableButton(int x, int y, Room r, String key) {
		super(x, y, r, null, null);
		this.key = key;
	}

	@Override
	public void render(Graphics g) {
		PaintService.paintTextColor(key, new Point2D(x, y), g, PaintService.DIGITS_ORANGE.getRGB());
	}

}
