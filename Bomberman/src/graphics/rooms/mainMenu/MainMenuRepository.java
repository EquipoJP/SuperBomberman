/**
 * Class for the load of main menu resources
 */
package graphics.rooms.mainMenu;

import logic.Sprite;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class MainMenuRepository {

	static Sprite background = null;

	static Sprite titleButton = null;
	static Sprite gameButton = null;
	static Sprite optionsButton = null;
	static Sprite rankingButton = null;
	static Sprite creditsButton = null;
	static Sprite quitButton = null;
	static Sprite controlsButton = null;

	/**
	 * Load resources
	 */
	public static void load() {
		loadBackground();
		loadTitleButton();
		loadGameButton();
		loadOptionsButton();
		loadRankingButton();
		loadCreditsButton();
		loadQuitButton();
		loadControlsButton();
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
	 * Load game button
	 */
	private static void loadGameButton() {
		if (gameButton == null) {
			gameButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.GAME_BUTTON
							.toString());
		}
	}

	/**
	 * Load sound options button
	 */
	private static void loadOptionsButton() {
		if (optionsButton == null) {
			optionsButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.OPTIONS_BUTTON
							.toString());
		}
	}

	/**
	 * Load ranking button
	 */
	private static void loadRankingButton() {
		if (rankingButton == null) {
			rankingButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.RANKING_BUTTON
							.toString());
		}
	}

	/**
	 * Load credits button
	 */
	private static void loadCreditsButton() {
		if (creditsButton == null) {
			creditsButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.CREDITS_BUTTON
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
	
	/**
	 * Load controls button
	 */
	private static void loadControlsButton() {
		if (controlsButton == null) {
			controlsButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.CONTROLS_BUTTON
							.toString());
		}
	}
}
