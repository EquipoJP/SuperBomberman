/**
 * Class containing the resources for the sound options menu
 */
package graphics.rooms.optionsMenu;

import logic.Sprite;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class OptionsMenuRepository {

	static Sprite background = null;

	static Sprite titleButton = null;
	static Sprite slider = null;
	static Sprite backButton = null;

	/**
	 * Load resources
	 */
	public static void load() {
		loadBackground();
		loadTitleButton();
		loadSlider();
		loadBackButton();
	}

	/**
	 * Load background
	 */
	private static void loadBackground() {
		if (background == null) {
			background = Initialization
					.getSpriteFromMenu(Initialization.MENUS.BACKGROUND
							.toString());
		}
	}

	/**
	 * Load title button
	 */
	private static void loadTitleButton() {
		if (titleButton == null) {
			titleButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.TITLE_BUTTON
							.toString());
		}
	}

	/**
	 * Load slider
	 */
	private static void loadSlider() {
		if (slider == null) {
			slider = Initialization
					.getSpriteFromMenu(Initialization.MENUS.SLIDER.toString());
		}
	}

	/**
	 * Load back button
	 */
	private static void loadBackButton() {
		if (backButton == null) {
			backButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.BACK_BUTTON
							.toString());
		}
	}

}
