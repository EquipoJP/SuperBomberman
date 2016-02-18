/**
 * Class containing the states machine
 */
package logic;

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
	private Graphics graphics;

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
	public StatesMachine(Input input, Graphics g) {
		state = STATE.INTRO;
		next_state = state;
		this.input = input;
		this.graphics = g;
	}

	/**
	 * One iteration of the states machine. An iteration represents a frame of
	 * the game. This method will be called from GameLoop object.
	 */
	public void stateMachine() {
		int key = input.getNextKey();

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

	/**
	 * Shows the introduction of the game
	 * 
	 * @param key
	 */
	private void intro(int key) {

		if (introScreen == null) {
			introScreen = new Intro();
		}
		else{
			if(key==3){
				introScreen.action(key);
			}
			introScreen.render(graphics);
		}
		// TODO complete the method
	}

	/**
	 * Shows the menu of the game
	 * 
	 * @param key
	 */
	private void main_menu(int key) {

		if (titleScreen == null) {
			titleScreen = new MainMenu();
		}
		// TODO complete the method
	}

	/**
	 * Shows the options menu of the game
	 * 
	 * @param key
	 */
	private void options_menu(int key) {

		if (optionScreen == null) {
			optionScreen = new OptionsMenu();
		}
		// TODO complete the method
	}

	/**
	 * Traditional mode of the game
	 * 
	 * @param key
	 */
	private void t_mode(int key) {

		if (gameScreen == null) {
			gameScreen = new T_Game();
		}
		// TODO complete the method
	}

	/**
	 * Super-Bomber mode of the game
	 * 
	 * @param key
	 */
	private void sb_mode(int key) {

		if (gameScreen == null) {
			gameScreen = new SB_Game();
		}
		// TODO complete the method
	}

	/**
	 * Pause mode
	 * 
	 * @param key
	 */
	private void pause(int key) {

		if (pauseScreen == null) {
			pauseScreen = new PauseMenu();
		}
		// TODO complete the method
	}

	/**
	 * Show the ranking of the Super-Bomber mode
	 * 
	 * @param key
	 */
	private void ranks(int key) {

		if (rankScreen == null) {
			rankScreen = new RankMenu();
		}
		// TODO complete the method
	}

	/**
	 * Let the player put a name to the highscore (entering the top 10)
	 * 
	 * @param key
	 */
	private void top10(int key) {

		if (rankScreen == null) {
			rankScreen = new RankMenu();
		}
	}

	/**
	 * Show the game over menu screen
	 * 
	 * @param key
	 */
	private void game_over(int key) {

		if (gameOverScreen == null) {
			gameOverScreen = new GameOverMenu();
		}
		// TODO complete the method
	}
}
