package graphics.rooms.mainMenu;

import logic.Sprite;
import main.Initialization;

public class MainMenuRepository {

	static Sprite background = null;

	static Sprite titleButton = null;
	static Sprite gameButton = null;
	static Sprite optionsButton = null;
	static Sprite rankingButton = null;
	static Sprite creditsButton = null;
	static Sprite quitButton = null;

	public static void load() {
		loadBackground();
		loadTitleButton();
		loadGameButton();
		loadOptionsButton();
		loadRankingButton();
		loadCreditsButton();
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

	private static void loadGameButton() {
		if(gameButton == null){
			gameButton = Initialization.getSpriteFromMenu(Initialization.MENUS.GAME_BUTTON.toString());
		}
	}

	private static void loadOptionsButton() {
		if(optionsButton == null){
			optionsButton = Initialization.getSpriteFromMenu(Initialization.MENUS.OPTIONS_BUTTON.toString());
		}
	}

	private static void loadRankingButton() {
		if(rankingButton == null){
			rankingButton = Initialization.getSpriteFromMenu(Initialization.MENUS.RANKING_BUTTON.toString());
		}
	}

	private static void loadCreditsButton() {
		if(creditsButton == null){
			creditsButton = Initialization.getSpriteFromMenu(Initialization.MENUS.CREDITS_BUTTON.toString());
		}
	}

	private static void loadQuitButton() {
		if(quitButton == null){
			quitButton = Initialization.getSpriteFromMenu(Initialization.MENUS.QUIT_BUTTON.toString());
		}
	}
}
