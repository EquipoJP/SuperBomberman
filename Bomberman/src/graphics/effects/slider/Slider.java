package graphics.effects.slider;

import graphics.D2.rooms.Room;
import graphics.effects.Visual;
import logic.Input.KEY;
import logic.Sprite;

public class Slider extends Visual {

	private SliderHelper sliderHelper;

	public Slider(int x, int y, Room r, Sprite spr, SliderHelper sh, int image_index) {
		super(x, y, r, spr);
		
		image_speed = 0;
		this.image_index = image_index;
		
		sliderHelper = sh;
	}

	@Override
	public void processKey(KEY key, KEY direction) {
		super.processKey(key, direction);

		switch (key) {
		case SPACE:
			image_index = (image_index + 1) % sprite_index.getSubimages();
			sliderHelper.action((int) image_index);
			break;
		default:
			break;
		}
	}

}
