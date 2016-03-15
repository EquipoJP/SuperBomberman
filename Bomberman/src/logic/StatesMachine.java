/**
 * Class containing the states machine
 */
package logic;

import graphics.D2.rooms.Game;
import graphics.D2.rooms.credits.Credits;
import graphics.D2.rooms.gameOverMenu.GameOverMenu;
import graphics.D2.rooms.intro.Intro;
import graphics.D2.rooms.mainMenu.MainMenu;
import graphics.D2.rooms.optionsMenu.OptionsMenu;
import graphics.D2.rooms.pauseMenu.PauseMenu;
import graphics.D2.rooms.rankMenu.RankMenu;
import graphics.D2.rooms.sbGame.SB_Game;
import graphics.D2.rooms.tGame.T_Game;

import java.awt.Graphics;

import logic.Input.KEY;
import main.Initialization;
import main.Initialization.STAGE;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class StatesMachine {

	/* machine's states */
	public enum STATE {
		INTRO, MAIN_MENU, OPTIONS_MENU, T_MODE, SB_MODE, PAUSE, GAME_OVER, RANKS, TOP10, CREDITS
	};

	/* private attributes */
	private static STATE state = STATE.INTRO;
	public static Input input;

	/* different screens */
	private static Intro introScreen = null;
	private static MainMenu titleScreen = null;
	private static OptionsMenu optionScreen = null;
	private static RankMenu rankScreen = null;
	private static Game gameScreen = null;
	private static PauseMenu pauseScreen = null;
	private static GameOverMenu gameOverScreen = null;
	private static Credits credits = null;

	/**
	 * Creation of the states machine. It starts on the game's intro
	 */
	public static void initStatesMachine(Input in) {
		state = STATE.INTRO;
		input = in;
	}

	/**
	 * One iteration of the states machine. An iteration represents a frame of
	 * the game. This method will be called from GameLoop object.
	 */
	public static void stateMachine() {
		Input.KEY key = input.getKey();
		Input.KEY direction = input.getDirection();

		switch (state) {
		case INTRO:
			intro(key, direction);
			break;
		case MAIN_MENU:
			main_menu(key, direction);
			break;
		case OPTIONS_MENU:
			options_menu(key, direction);
			break;
		case T_MODE:
			t_mode(key, direction);
			break;
		case SB_MODE:
			sb_mode(key, direction);
			break;
		case PAUSE:
			pause(key, direction);
			break;
		case RANKS:
			ranks(key, direction);
			break;
		case TOP10:
			top10(key, direction);
			break;
		case GAME_OVER:
			game_over(key, direction);
			break;
		case CREDITS:
			credits(key, direction);
			break;
		default:
			break;
		}

	}

	public static void render(Graphics g) {
		switch (state) {
		case INTRO:
			introScreen.render(g);
			break;
		case MAIN_MENU:
			titleScreen.render(g);
			break;
		case OPTIONS_MENU:
			optionScreen.render(g);
			break;
		case T_MODE:
			gameScreen.render(g);
			break;
		case SB_MODE:
			gameScreen.render(g);
			break;
		case PAUSE:
			pauseScreen.render(g);
			break;
		case RANKS:
			rankScreen.render(g);
			break;
		case TOP10:
			rankScreen.render(g);
			break;
		case GAME_OVER:
			gameOverScreen.render(g);
			break;
		case CREDITS:
			credits.render(g);
			break;
		default:
			break;
		}
	}

	public static void goToRoom(STATE st, boolean persist) {
		if(persist && (state == STATE.SB_MODE || state == STATE.T_MODE)){
			persistent();
		}
		else{
			clearRoom(state);
		}
		
		if(state == STATE.PAUSE){
			if(st == STATE.MAIN_MENU){
				clearRoom(STATE.SB_MODE);
			}
		}

		state = st;
		
		if((state == STATE.SB_MODE || state == STATE.T_MODE) && gameScreen != null){
			gameScreen.resume();
		}
		
		stateMachine();
	}
	
	private static void persistent(){
		gameScreen.pause();
	}

	private static void clearRoom(STATE st) {
		switch (st) {
		case INTRO:
			introScreen.destroy();
			introScreen = null;
			break;
		case MAIN_MENU:
			titleScreen.destroy();
			titleScreen = null;
			break;
		case OPTIONS_MENU:
			optionScreen.destroy();
			optionScreen = null;
			break;
		case T_MODE:
			gameScreen.destroy();
			gameScreen = null;
			break;
		case SB_MODE:
			gameScreen.destroy();
			gameScreen = null;
			break;
		case PAUSE:
			pauseScreen.destroy();
			pauseScreen = null;
			break;
		case RANKS:
			rankScreen.destroy();
			rankScreen = null;
			break;
		case TOP10:
			rankScreen.destroy();
			rankScreen = null;
			break;
		case GAME_OVER:
			gameOverScreen.destroy();
			gameOverScreen = null;
			break;
		case CREDITS:
			credits.destroy();
			credits = null;
			break;
		default:
			break;
		}
	}

	/**
	 * Shows the introduction of the game
	 * 
	 * @param key
	 * @param direction 
	 */
	private static void intro(KEY key, KEY direction) {

		if (introScreen == null) {
			introScreen = new Intro(main.Game.WIDTH, main.Game.HEIGHT, "Intro");
		}
		introScreen.step(key, direction);
	}

	/**
	 * Shows the menu of the game
	 * 
	 * @param key
	 * @param direction 
	 */
	private static void main_menu(KEY key, KEY direction) {

		if (titleScreen == null) {
			// ////////////////////////////////////////////////////////////////////////////////
			titleScreen = new MainMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Main menu");
			// ////////////////////////////////////////////////////////////////////////////////
		}
		titleScreen.step(key, direction);
		// TODO complete the method
	}

	/**
	 * Shows the options menu of the game
	 * 
	 * @param key
	 * @param direction 
	 */
	private static void options_menu(KEY key, KEY direction) {

		if (optionScreen == null) {
			optionScreen = new OptionsMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Options menu");
		}
		optionScreen.step(key, direction);
		// TODO complete the method
	}

	/**
	 * Traditional mode of the game
	 * 
	 * @param key
	 * @param direction 
	 */
	private static void t_mode(KEY key, KEY direction) {

		if (gameScreen == null) {
			// ////////////////////////////////////////
			String file = "maps/level1.txt";
			STAGE stage = Initialization.STAGE.GREENVILLAGE;
			// ////////////////////////////////////////
			gameScreen = new T_Game(main.Game.WIDTH, main.Game.HEIGHT,
					"T mode", file, stage);
		}
		gameScreen.step(key, direction);
		// TODO complete the method
	}

	/**
	 * Super-Bomber mode of the game
	 * 
	 * @param key
	 * @param direction 
	 */
	private static void sb_mode(KEY key, KEY direction) {

		if (gameScreen == null) {
			// /////////////////////////////////////
			String file = "maps/level1.txt";
			STAGE stage = Initialization.STAGE.PEACETOWN;
			// ////////////////////////////////////
			gameScreen = new SB_Game(main.Game.WIDTH, main.Game.HEIGHT,
					"Super Bomber mode", file, stage);
		}
		gameScreen.step(key, direction);
		// TODO complete the method
	}

	/**
	 * Pause mode
	 * 
	 * @param key
	 * @param direction 
	 */
	private static void pause(KEY key, KEY direction) {

		if (pauseScreen == null) {
			STATE mode = STATE.MAIN_MENU;
			if(gameScreen != null){
				if(gameScreen instanceof SB_Game){
					mode = STATE.SB_MODE;
				}
				if(gameScreen instanceof T_Game){
					mode = STATE.T_MODE;
				}
			}
			
			pauseScreen = new PauseMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Pause", mode);
		}
		pauseScreen.step(key, direction);
		// TODO complete the method
	}

	/**
	 * Show the ranking of the Super-Bomber mode
	 * 
	 * @param key
	 * @param direction 
	 */
	private static void ranks(KEY key, KEY direction) {

		if (rankScreen == null) {
			rankScreen = new RankMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Ranks", null);
		}
		rankScreen.step(key, direction);
		// TODO complete the method
	}

	/**
	 * Let the player put a name to the highscore (entering the top 10)
	 * 
	 * @param key
	 * @param direction 
	 */
	private static void top10(KEY key, KEY direction) {

		if (rankScreen == null) {
			rankScreen = new RankMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Top10", null);
		}
		rankScreen.step(key, direction);
	}

	/**
	 * Show the game over menu screen
	 * 
	 * @param key
	 * @param direction 
	 */
	private static void game_over(KEY key, KEY direction) {

		if (gameOverScreen == null) {
			gameOverScreen = new GameOverMenu(main.Game.WIDTH,
					main.Game.HEIGHT, "Game over");
		}
		gameOverScreen.step(key, direction);
		// TODO complete the method
	}

	private static void credits(KEY key, KEY direction) {

		if (credits == null) {
			// ////////////////////////////////////////////////////////////////////////////////
			credits = new Credits(main.Game.WIDTH, main.Game.HEIGHT, "Credits");
			// ////////////////////////////////////////////////////////////////////////////////
		}
		credits.step(key, direction);
		// TODO complete the method
	}
}
