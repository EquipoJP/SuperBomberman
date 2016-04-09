/**
 * Class containing the resources for the credits room
 */
package graphics.rooms.credits;

import logic.Sprite;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class CreditsRepository {

	static Sprite background = null;

	static Sprite titleButton = null;
	static Sprite credits = null;
	static Sprite backButton = null;

	/**
	 * Load resources
	 */
	public static void load() {
		loadBackground();
		loadTitleButton();
		loadCredits();
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
	 * Load credits
	 */
	private static void loadCredits() {
		if (credits == null) {
			credits = Initialization
					.getSpriteFromMenu(Initialization.MENUS.CREDITS.toString());
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
