/**
 * Class containing the states machine
 */
package logic;

import graphics.D2.rooms.Credits;
import graphics.D2.rooms.Game;
import graphics.D2.rooms.GameOverMenu;
import graphics.D2.rooms.Intro;
import graphics.D2.rooms.MainMenu;
import graphics.D2.rooms.OptionsMenu;
import graphics.D2.rooms.PauseMenu;
import graphics.D2.rooms.RankMenu;
import graphics.D2.rooms.SB_Game;
import graphics.D2.rooms.T_Game;

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

		switch (state) {
		case INTRO:
			intro(key);
			break;
		case MAIN_MENU:
			main_menu(key);
			break;
		case OPTIONS_MENU:
			options_menu(key);
			break;
		case T_MODE:
			t_mode(key);
			break;
		case SB_MODE:
			sb_mode(key);
			break;
		case PAUSE:
			pause(key);
			break;
		case RANKS:
			ranks(key);
			break;
		case TOP10:
			top10(key);
			break;
		case GAME_OVER:
			game_over(key);
			break;
		case CREDITS:
			credits(key);
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
			;
		}
		else{
			clearRoom(state);
		}
		
		if(state == STATE.PAUSE){
			if(st == STATE.MAIN_MENU){
				gameScreen = null;
			}
		}

		state = st;
		stateMachine();
	}

	private static void clearRoom(STATE st) {
		// TODO Auto-generated method stub
		switch (state) {
		case INTRO:
			introScreen = null;
			break;
		case MAIN_MENU:
			titleScreen = null;
			break;
		case OPTIONS_MENU:
			optionScreen = null;
			break;
		case T_MODE:
			gameScreen = null;
			break;
		case SB_MODE:
			gameScreen = null;
			break;
		case PAUSE:
			pauseScreen = null;
			break;
		case RANKS:
			rankScreen = null;
			break;
		case TOP10:
			rankScreen = null;
			break;
		case GAME_OVER:
			gameOverScreen = null;
			break;
		case CREDITS:
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
	 */
	private static void intro(KEY key) {

		if (introScreen == null) {
			introScreen = new Intro(main.Game.WIDTH, main.Game.HEIGHT, "Intro");
		}
		introScreen.step(key);
	}

	/**
	 * Shows the menu of the game
	 * 
	 * @param key
	 */
	private static void main_menu(KEY key) {

		if (titleScreen == null) {
			// ////////////////////////////////////////////////////////////////////////////////
			titleScreen = new MainMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Main menu", null);
			// ////////////////////////////////////////////////////////////////////////////////
		}
		titleScreen.step(key);
		// TODO complete the method
	}

	/**
	 * Shows the options menu of the game
	 * 
	 * @param key
	 */
	private static void options_menu(KEY key) {

		if (optionScreen == null) {
			optionScreen = new OptionsMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Options menu");
		}
		optionScreen.step(key);
		// TODO complete the method
	}

	/**
	 * Traditional mode of the game
	 * 
	 * @param key
	 */
	private static void t_mode(KEY key) {

		if (gameScreen == null) {
			// ////////////////////////////////////////
			String file = "maps/level1.txt";
			STAGE stage = Initialization.STAGE.GREENVILLAGE;
			// ////////////////////////////////////////
			gameScreen = new T_Game(main.Game.WIDTH, main.Game.HEIGHT,
					"T mode", file, stage);
		}
		gameScreen.step(key);
		// TODO complete the method
	}

	/**
	 * Super-Bomber mode of the game
	 * 
	 * @param key
	 */
	private static void sb_mode(KEY key) {

		if (gameScreen == null) {
			// /////////////////////////////////////
			String file = "maps/level1.txt";
			STAGE stage = Initialization.STAGE.PEACETOWN;
			// ////////////////////////////////////
			gameScreen = new SB_Game(main.Game.WIDTH, main.Game.HEIGHT,
					"Super Bomber mode", file, stage);
		}
		gameScreen.step(key);
		// TODO complete the method
	}

	/**
	 * Pause mode
	 * 
	 * @param key
	 */
	private static void pause(KEY key) {

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
		pauseScreen.step(key);
		// TODO complete the method
	}

	/**
	 * Show the ranking of the Super-Bomber mode
	 * 
	 * @param key
	 */
	private static void ranks(KEY key) {

		if (rankScreen == null) {
			rankScreen = new RankMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Ranks");
		}
		rankScreen.step(key);
		// TODO complete the method
	}

	/**
	 * Let the player put a name to the highscore (entering the top 10)
	 * 
	 * @param key
	 */
	private static void top10(KEY key) {

		if (rankScreen == null) {
			rankScreen = new RankMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Top10");
		}
		rankScreen.step(key);
	}

	/**
	 * Show the game over menu screen
	 * 
	 * @param key
	 */
	private static void game_over(KEY key) {

		if (gameOverScreen == null) {
			gameOverScreen = new GameOverMenu(main.Game.WIDTH,
					main.Game.HEIGHT, "Game over");
		}
		gameOverScreen.step(key);
		// TODO complete the method
	}

	private static void credits(KEY key) {

		if (credits == null) {
			// ////////////////////////////////////////////////////////////////////////////////
			credits = new Credits(main.Game.WIDTH, main.Game.HEIGHT, "Credits");
			// ////////////////////////////////////////////////////////////////////////////////
		}
		credits.step(key);
		// TODO complete the method
	}
}
