package graphics.D2.rooms.pauseMenu;

import logic.Sprite;
import main.Initialization;

public class PauseMenuRepository {
	
	static Sprite background = null;

	static Sprite titleButton = null;
	static Sprite continueButton = null;
	static Sprite quitButton = null;

	public static void load() {
		loadBackground();
		loadTitleButton();
		loadContinueButton();
		loadQuitButton();
	}

	private static void loadBackground() {
		if(background == null){
			background = Initialization.getSpriteFromMenu(Initialization.MENUS.BACKGROUND.toString());
		}
	}

	private static void loadTitleButton() {
		if (titleButton == null) {
			titleButton = Initialization.getSpriteFromMenu(Initialization.MENUS.TITLE_BUTTON.toString());
		}
	}
	
	private static void loadContinueButton() {
		if (continueButton == null) {
			continueButton = Initialization.getSpriteFromMenu(Initialization.MENUS.CONTINUE_BUTTON.toString());
		}
	}

	private static void loadQuitButton() {
		if(quitButton == null){
			quitButton = Initialization.getSpriteFromMenu(Initialization.MENUS.QUIT_BUTTON.toString());
		}
	}

}
