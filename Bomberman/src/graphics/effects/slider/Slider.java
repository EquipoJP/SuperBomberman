/**
 * Class representing an slider
 */
package graphics.effects.slider;

import graphics.effects.Visual;
import graphics.rooms.Room;
import logic.Input.KEY;
import logic.Sprite;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Slider extends Visual {

	private SliderHelper sliderHelper;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param r
	 *            room
	 * @param spr
	 *            sprite to use
	 * @param sh
	 *            slider helper to take functionality
	 * @param image_index
	 *            image index to begin in
	 */
	public Slider(int x, int y, Room r, Sprite spr, SliderHelper sh,
			int image_index) {
		super(x, y, r, spr);

		image_speed = 0;
		this.image_index = image_index;

		sliderHelper = sh;
	}

	@Override
	public void processKey(KEY key, KEY direction) {
		super.processKey(key, direction);

		switch (key) {
		case RIGHT:
			image_index = (image_index + 1) % sprite_index.getSubimages();
			break;
		case LEFT:
			image_index = (image_index - 1) % sprite_index.getSubimages();
		default:
			break;
		}
		sliderHelper.action((int) image_index);
	}

}
