/**
 * Class containing the resources for the pause menu
 */
package graphics.rooms.pauseMenu;

import logic.Sprite;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class PauseMenuRepository {

	static Sprite background = null;

	static Sprite titleButton = null;
	static Sprite continueButton = null;
	static Sprite quitButton = null;

	/**
	 * Load the resources
	 */
	public static void load() {
		loadBackground();
		loadTitleButton();
		loadContinueButton();
		loadQuitButton();
	}

	/**
	 * Load the background
	 */
	private static void loadBackground() {
		if (background == null) {
			background = Initialization
					.getSpriteFromMenu(Initialization.MENUS.BACKGROUND
							.toString());
		}
	}

	/**
	 * Load the title button
	 */
	private static void loadTitleButton() {
		if (titleButton == null) {
			titleButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.TITLE_BUTTON
							.toString());
		}
	}

	/**
	 * Load continue button
	 */
	private static void loadContinueButton() {
		if (continueButton == null) {
			continueButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.CONTINUE_BUTTON
							.toString());
		}
	}

	/**
	 * Load quit button
	 */
	private static void loadQuitButton() {
		if (quitButton == null) {
			quitButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.QUIT_BUTTON
							.toString());
		}
	}

}
