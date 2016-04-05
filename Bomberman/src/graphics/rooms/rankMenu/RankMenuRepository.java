package graphics.rooms.rankMenu;

import logic.Sprite;
import main.Initialization;

public class RankMenuRepository {

	static Sprite background = null;

	static Sprite titleButton = null;

	static Sprite continueButton = null;
	static Sprite backButton = null;

	public static void load() {
		loadBackground();
		loadTitleButton();
		loadContinueButton();
		loadBackButton();
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

	private static void loadBackButton() {
		if (backButton == null) {
			backButton = Initialization.getSpriteFromMenu(Initialization.MENUS.BACK_BUTTON.toString());
		}
	}

}
