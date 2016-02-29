/**
 * Class containing the states machine
 */
package logic;

import java.awt.Graphics;
import java.util.ArrayList;

import graphics.D2.rooms.Game;
import graphics.D2.rooms.GameOverMenu;
import graphics.D2.rooms.Intro;
import graphics.D2.rooms.MainMenu;
import graphics.D2.rooms.OptionsMenu;
import graphics.D2.rooms.PauseMenu;
import graphics.D2.rooms.RankMenu;
import graphics.D2.rooms.SB_Game;
import graphics.D2.rooms.T_Game;
import logic.Input.KEY;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class StatesMachine {

	/* machine's states */
	public enum STATE {
		INTRO, MAIN_MENU, OPTIONS_MENU, T_MODE, SB_MODE, PAUSE, GAME_OVER, RANKS, TOP10
	};

	/* private attributes */
	private STATE state;
	private STATE next_state;

	private Input input;

	/* different screens */
	private Intro introScreen = null;
	private MainMenu titleScreen = null;
	private OptionsMenu optionScreen = null;
	private RankMenu rankScreen = null;
	private Game gameScreen = null;
	private PauseMenu pauseScreen = null;
	private GameOverMenu gameOverScreen = null;

	/**
	 * Creation of the states machine. It starts on the game's intro
	 */
	public StatesMachine(Input input) {
		state = STATE.INTRO;
		next_state = state;
		this.input = input;
		intro(KEY.NO_KEY);
	}

	/**
	 * One iteration of the states machine. An iteration represents a frame of
	 * the game. This method will be called from GameLoop object.
	 */
	public void stateMachine() {
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
		}

		state = next_state;
	}

	public void render(Graphics g) {
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
		}
	}

	/**
	 * Shows the introduction of the game
	 * 
	 * @param key
	 */
	private void intro(KEY key) {

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
	private void main_menu(KEY key) {

		if (titleScreen == null) {
			titleScreen = new MainMenu(main.Game.WIDTH, main.Game.HEIGHT, "Main menu", new ArrayList<Objeto>());
		}
		// TODO complete the method
	}

	/**
	 * Shows the options menu of the game
	 * 
	 * @param key
	 */
	private void options_menu(KEY key) {

		if (optionScreen == null) {
			optionScreen = new OptionsMenu(main.Game.WIDTH, main.Game.HEIGHT, "Options menu", new ArrayList<Objeto>());
		}
		// TODO complete the method
	}

	/**
	 * Traditional mode of the game
	 * 
	 * @param key
	 */
	private void t_mode(KEY key) {

		if (gameScreen == null) {
			gameScreen = new T_Game(main.Game.WIDTH, main.Game.HEIGHT, "T mode");
		}
		// TODO complete the method
	}

	/**
	 * Super-Bomber mode of the game
	 * 
	 * @param key
	 */
	private void sb_mode(KEY key) {

		if (gameScreen == null) {
			gameScreen = new SB_Game(main.Game.WIDTH, main.Game.HEIGHT, "Super Bomber mode");
		}
		// TODO complete the method
	}

	/**
	 * Pause mode
	 * 
	 * @param key
	 */
	private void pause(KEY key) {

		if (pauseScreen == null) {
			pauseScreen = new PauseMenu(main.Game.WIDTH, main.Game.HEIGHT, "Pause", new ArrayList<Objeto>());
		}
		// TODO complete the method
	}

	/**
	 * Show the ranking of the Super-Bomber mode
	 * 
	 * @param key
	 */
	private void ranks(KEY key) {

		if (rankScreen == null) {
			rankScreen = new RankMenu(main.Game.WIDTH, main.Game.HEIGHT, "Ranks", new ArrayList<Objeto>());
		}
		// TODO complete the method
	}

	/**
	 * Let the player put a name to the highscore (entering the top 10)
	 * 
	 * @param key
	 */
	private void top10(KEY key) {

		if (rankScreen == null) {
			rankScreen = new RankMenu(main.Game.WIDTH, main.Game.HEIGHT, "Top10", new ArrayList<Objeto>());
		}
	}

	/**
	 * Show the game over menu screen
	 * 
	 * @param key
	 */
	private void game_over(KEY key) {

		if (gameOverScreen == null) {
			gameOverScreen = new GameOverMenu(main.Game.WIDTH, main.Game.HEIGHT, "Game over", new ArrayList<Objeto>());
		}
		// TODO complete the method
	}
}
