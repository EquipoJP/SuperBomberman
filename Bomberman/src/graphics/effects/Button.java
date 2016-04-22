/**
 * Class representing a button
 */
package graphics.effects;

import logic.Input.KEY;
import graphics.rooms.Room;
import logic.Objeto;
import logic.Sprite;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Button extends Objeto {

	private boolean selected;
	private Runnable function;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param r
	 *            room
	 * @param sprite
	 *            sprite to use
	 * @param run
	 *            function to run when selected
	 */
	public Button(int x, int y, Room r, Sprite sprite, Runnable run) {
		super(x, y, 0, r);

		sprite_index = sprite;
		image_speed = 0;
		image_index = 0;

		selected = false;
		function = run;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if (selected) {
			image_index = 1;
		} else {
			image_index = 0;
		}
	}

	/**
	 * Select the button
	 */
	public void select() {
		this.selected = true;
	}

	/**
	 * Unselect the button
	 */
	public void unselect() {
		this.selected = false;
	}

	/**
	 * @return true if it is selected, false otherwise
	 */
	public boolean isSelected() {
		return this.selected;
	}

	public void confirm() {
		function.run();
	}
}
