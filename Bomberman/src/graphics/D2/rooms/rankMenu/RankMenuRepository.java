package graphics.D2.rooms.rankMenu;

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
		// TODO Auto-generated method stub

	}

	private static void loadTitleButton() {
		if (titleButton == null) {
			titleButton = Initialization.getSpriteFromMenu(Initialization.BUTTONS.TITLE_BUTTON.toString());
		}
	}

	private static void loadContinueButton() {
		if (continueButton == null) {
			continueButton = Initialization.getSpriteFromMenu(Initialization.BUTTONS.CONTINUE_BUTTON.toString());
		}
	}

	private static void loadBackButton() {
		if (backButton == null) {
			backButton = Initialization.getSpriteFromMenu(Initialization.BUTTONS.BACK_BUTTON.toString());
		}
	}

}
