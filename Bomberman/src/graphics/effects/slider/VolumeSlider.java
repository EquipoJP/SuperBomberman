/**
 * Class containing the logic of a volume slider
 */
package graphics.effects.slider;

import kuusisto.tinysound.TinySound;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class VolumeSlider implements SliderHelper {

	/*
	 * TinySound Global Volume goes from 1.0 to infinite. It is a sound_volume *
	 * global_volume function.
	 * 
	 * Ours will be from 1.0 - 1.25 - 1.5 - 1.75 - 2.0
	 */

	@Override
	public void action(int subimage) {
		double volume = 1.0;
		switch (subimage) {
		case 0:
			volume = 1.0;
			break;
		case 1:
			volume = 1.25;
			break;
		case 2:
			volume = 1.5;
			break;
		case 3:
			volume = 1.75;
			break;
		case 4:
			volume = 2.0;
			break;
		default:
			volume = 1.0;
			break;
		}
		TinySound.setGlobalVolume(volume);
	}

}
