/**
 * Class containing the states machine
 */
package logic;

import graphics.d3.SuperBomberman3D;
import graphics.rooms.controlsMenu.Controls;
import graphics.rooms.credits.Credits;
import graphics.rooms.game.Game;
import graphics.rooms.intro.Intro;
import graphics.rooms.mainMenu.MainMenu;
import graphics.rooms.optionsMenu.OptionsMenu;
import graphics.rooms.pauseMenu.PauseMenu;
import graphics.rooms.rankMenu.RankMenu;

import java.awt.Graphics;

import logic.Input.KEY;
import logic.misc.LevelManager;
import logic.misc.Record;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class StatesMachine {

	/* machine's states */
	public enum STATE {
		INTRO, MAIN_MENU, OPTIONS_MENU, GAME, GAME3D, PAUSE, RANKS, TOP10, CREDITS, CONTROLS
	};

	/* private attributes */
	public static STATE state = STATE.INTRO;
	public static Input input;

	/* different screens */
	private static Intro introScreen = null;
	private static MainMenu titleScreen = null;
	private static OptionsMenu optionScreen = null;
	private static RankMenu rankScreen = null;
	private static Game gameScreen = null;
	private static PauseMenu pauseScreen = null;
	private static Credits credits = null;
	private static Controls controls = null;
	public static LwjglApplication game3D = null;

	/**
	 * Creation of the states machine. It starts on the game's intro
	 * 
	 * @param in input
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
		case GAME:
			game(key, direction);
			break;
		case GAME3D:
			game3d(key, direction);
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
		case CREDITS:
			credits(key, direction);
			break;
		case CONTROLS:
			controls(key, direction);
			break;
		default:
			break;
		}
	}

	/**
	 * Render a frame
	 * 
	 * @param g
	 *            graphics in which to paint
	 */
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
		case GAME:
			gameScreen.render(g);
			break;
		case GAME3D:
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
		case CREDITS:
			credits.render(g);
			break;
		case CONTROLS:
			controls.render(g);
			break;
		default:
			break;
		}
	}

	/**
	 * Changes room
	 * 
	 * @param st
	 *            state to go to
	 * @param persist
	 *            true if the room needn't be cleaned
	 */
	public static void goToRoom(STATE st, boolean persist) {
		if (persist && state == STATE.GAME) {
			persistent();
		} else {
			clearRoom(state);
		}

		if (state == STATE.PAUSE) {
			if (st != STATE.GAME) {
				clearRoom(STATE.GAME);
			}
		}

		state = st;

		if ((state == STATE.GAME) && gameScreen != null) {
			gameScreen.resume();
		}

		stateMachine();
	}

	/**
	 * Pause the game
	 */
	private static void persistent() {
		gameScreen.pause();
	}

	/**
	 * Cleans the state's room
	 * 
	 * @param st
	 *            state
	 */
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
		case GAME:
			gameScreen.destroy();
			gameScreen = null;
			break;
		case GAME3D:
			game3D.exit();
			game3D = null;
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
		case CREDITS:
			credits.destroy();
			credits = null;
			break;
		case CONTROLS:
			controls.destroy();
			controls = null;
			break;
		default:
			break;
		}
	}

	/**
	 * Shows the introduction of the game
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void intro(KEY key, KEY direction) {

		if (introScreen == null) {
			introScreen = new Intro(main.Game.WIDTH, main.Game.HEIGHT, "Intro");
		}
		introScreen.step(key, direction);
	}

	/**
	 * Shows the main menu of the game
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void main_menu(KEY key, KEY direction) {

		if (titleScreen == null) {
			titleScreen = new MainMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Main menu");
		}
		titleScreen.step(key, direction);
	}

	/**
	 * Shows the sound options menu of the game
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void options_menu(KEY key, KEY direction) {

		if (optionScreen == null) {
			optionScreen = new OptionsMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Options menu");
		}
		optionScreen.step(key, direction);
	}

	/**
	 * Game mode
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void game(KEY key, KEY direction) {

		if (gameScreen == null) {
			gameScreen = new Game(main.Game.WIDTH, main.Game.HEIGHT, "Game");
		}
		gameScreen.step(key, direction);
	}
	
	/**
	 * 3D Game mode
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void game3d(KEY key, KEY direction) {
		if (game3D == null) {
			Global.levels = new LevelManager();
			Game game = new Game(main.Game.WIDTH, main.Game.HEIGHT, "Game");
			while(!game.loadComplete()){
				;
			}
			game3D = SuperBomberman3D.main(game);
		}
	}

	/**
	 * Pause mode
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void pause(KEY key, KEY direction) {
		if (pauseScreen == null) {
			pauseScreen = new PauseMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Pause");
		}
		pauseScreen.step(key, direction);
	}

	/**
	 * Show the ranking of the game
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void ranks(KEY key, KEY direction) {

		if (rankScreen == null) {
			rankScreen = new RankMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Ranks", null);
		}
		rankScreen.step(key, direction);
	}

	/**
	 * Show the ranking of the game with a new record
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void top10(KEY key, KEY direction) {

		if (rankScreen == null) {
			Record record = Global.scoreManager.record();
			Global.scoreManager.reset();
			rankScreen = new RankMenu(main.Game.WIDTH, main.Game.HEIGHT,
					"Top10", record);
		}
		rankScreen.step(key, direction);
	}

	/**
	 * Show the credits of the game
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void credits(KEY key, KEY direction) {

		if (credits == null) {
			credits = new Credits(main.Game.WIDTH, main.Game.HEIGHT, "Credits");
		}
		credits.step(key, direction);
	}
	
	/**
	 * Show the controls of the game
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	private static void controls(KEY key, KEY direction) {

		if (controls == null) {
			controls = new Controls(main.Game.WIDTH, main.Game.HEIGHT, "Controls");
		}
		controls.step(key, direction);
	}
}
