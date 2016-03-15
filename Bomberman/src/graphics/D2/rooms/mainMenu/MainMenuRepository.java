package graphics.D2.rooms.mainMenu;

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

	public static void unload() {
		background = null;
		titleButton = null;
		gameButton = null;
		optionsButton = null;
		rankingButton = null;
		creditsButton = null;
		quitButton = null;
	}

	private static void loadBackground() {
		// TODO Auto-generated method stub

	}

	private static void loadTitleButton() {
		if (titleButton == null) {
			titleButton = Initialization.getSpriteFromMenu(Initialization.BUTTONS.TITLE_BUTTON.toString());
		}
	}

	private static void loadGameButton() {
		if(gameButton == null){
			gameButton = Initialization.getSpriteFromMenu(Initialization.BUTTONS.GAME_BUTTON.toString());
		}
	}

	private static void loadOptionsButton() {
		if(optionsButton == null){
			optionsButton = Initialization.getSpriteFromMenu(Initialization.BUTTONS.OPTIONS_BUTTON.toString());
		}
	}

	private static void loadRankingButton() {
		if(rankingButton == null){
			rankingButton = Initialization.getSpriteFromMenu(Initialization.BUTTONS.RANKING_BUTTON.toString());
		}
	}

	private static void loadCreditsButton() {
		if(creditsButton == null){
			creditsButton = Initialization.getSpriteFromMenu(Initialization.BUTTONS.CREDITS_BUTTON.toString());
		}
	}

	private static void loadQuitButton() {
		if(quitButton == null){
			quitButton = Initialization.getSpriteFromMenu(Initialization.BUTTONS.QUIT_BUTTON.toString());
		}
	}
}
