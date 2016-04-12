/**
 * Class to get the resources for the rank menu
 */
package graphics.rooms.rankMenu;

import logic.Sprite;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class RankMenuRepository {

	static Sprite background = null;

	static Sprite titleButton = null;

	static Sprite continueButton = null;
	static Sprite backButton = null;

	public static Sprite alphabet = null;
	public static Sprite digits = null;
	public static Sprite newRecord = null;

	/**
	 * Load resources
	 */
	public static void load() {
		loadBackground();
		loadTitleButton();
		loadContinueButton();
		loadBackButton();
		loadPaintService();
	}

	/**
	 * Load the background
	 */
	private static void loadBackground() {
		if (background == null) {
			background = Initialization.getSpriteFromMenu(Initialization.MENUS.BACKGROUND.toString());
		}
	}

	/**
	 * Load the title button
	 */
	private static void loadTitleButton() {
		if (titleButton == null) {
			titleButton = Initialization.getSpriteFromMenu(Initialization.MENUS.TITLE_BUTTON.toString());
		}
	}

	/**
	 * Load the continue button
	 */
	private static void loadContinueButton() {
		if (continueButton == null) {
			continueButton = Initialization.getSpriteFromMenu(Initialization.MENUS.CONTINUE_BUTTON.toString());
		}
	}

	/**
	 * Load the back button
	 */
	private static void loadBackButton() {
		if (backButton == null) {
			backButton = Initialization.getSpriteFromMenu(Initialization.MENUS.BACK_BUTTON.toString());
		}
	}

	/**
	 * Load the paint service
	 */
	private static void loadPaintService() {
		if (alphabet == null) {
			alphabet = Initialization.getSpriteFromMenu(Initialization.MENUS.ALPHABET.toString());
		}
		if (digits == null) {
			digits = Initialization.getSpriteFromMenu(Initialization.MENUS.DIGITS.toString());
		}
		if (newRecord == null) {
			newRecord = Initialization.getSpriteFromMenu(Initialization.MENUS.NEW.toString());
		}
	}

}
