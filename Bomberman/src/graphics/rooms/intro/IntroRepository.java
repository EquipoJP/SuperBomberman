/**
 * Class for the load of resources for Intro room
 */
package graphics.rooms.intro;

import logic.Sprite;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class IntroRepository {

	static Sprite logo = null;

	/**
	 * Load resources
	 */
	public static void load() {
		loadLogo();
	}

	/**
	 * Load logo
	 */
	private static void loadLogo() {
		if (logo == null) {
			logo = Initialization.getSpriteFromSprites("LOGO");
		}
	}

}
