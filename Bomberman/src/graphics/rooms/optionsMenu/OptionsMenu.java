/**
 * Class representing the sound options menu
 */
package graphics.rooms.optionsMenu;

import java.awt.Graphics;

import graphics.effects.Button;
import graphics.effects.Visual;
import graphics.effects.slider.Slider;
import graphics.effects.slider.VolumeSlider;
import graphics.rooms.Room;
import kuusisto.tinysound.TinySound;
import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import sound.MusicRepository;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class OptionsMenu extends Room {

	private static final int PADDING_BORDER = 25;

	/**
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @param n
	 *            name
	 */
	public OptionsMenu(int w, int h, String n) {
		super(w, h, n);
	}

	@Override
	public void load() {
		OptionsMenuRepository.load();

		background = OptionsMenuRepository.background;

		int x = width / 2;

		Sprite back = OptionsMenuRepository.backButton;
		int y = this.height - PADDING_BORDER - back.getHeight() / 2;
		Button back_obj = new Button(x, y, this, back, null);
		back_obj.select();
		addObjeto(back_obj);

		y = height / 2;
		Sprite slider = OptionsMenuRepository.slider;

		int img_index = getImageIndex();

		addObjeto(new Slider(x, y, this, slider, new VolumeSlider(), img_index));

		Sprite title = OptionsMenuRepository.titleButton;
		y = PADDING_BORDER + title.getHeight() / 2;
		addObjeto(new Visual(x, y, this, title));

		setMusic(MusicRepository.menu, true);
	}

	/**
	 * @return image index of the slider from the global volume
	 */
	private int getImageIndex() {
		double volume = TinySound.getGlobalVolume();
		int img_index = 0;

		if (volume >= 1.0 && volume < 1.25) {
			img_index = 0;
		} else if (volume >= 1.25 && volume < 1.5) {
			img_index = 1;
		} else if (volume >= 1.5 && volume < 1.75) {
			img_index = 2;
		} else if (volume >= 1.75 && volume < 2.0) {
			img_index = 3;
		} else if (volume >= 2.0) {
			img_index = 4;
		}

		return img_index;
	}

	@Override
	public void drawBackground(Graphics g) {
		if (!loadComplete()) {
			return;
		}
		g.clearRect(0, 0, width, height);
		if (background != null) {
			g.drawImage(background.getSubsprites()[0], 0, 0, null);
		}
	}

	/**
	 * Confirms
	 */
	private void confirm() {
		StatesMachine.goToRoom(STATE.MAIN_MENU, false);
	}

	@Override
	public void step(KEY key, KEY direction) {
		super.step(key, direction);
		if (!loadComplete()) {
			return;
		}
		switch (key) {
		case CONFIRM:
			confirm();
			break;
		case PAUSE:
			confirm();
			break;
		default:
			break;
		}
	}
}
