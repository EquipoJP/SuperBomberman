/**
 * Class containing the states machine
 */
package logic;

import graphics.*;
import graphics.menus.*;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class StatesMachine {

	/* machine's states */
	public final int INTRO = 0;
	public final int MAIN_MENU = 1;
	public final int T_MODE = 2;
	public final int SB_MODE = 3;
	public final int PAUSE = 4;
	public final int RANKS = 5;
	public final int TOP10 = 6;
	public final int GAME_OVER = 7;
	public final int OPTIONS_MENU = 8;

	/* private attributes */
	private int state;
	private int next_state;
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
		state = 0;
		next_state = state;
		this.input = input;
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
	 * @param key 
	 */
	private void intro(int key) {

	}

	/**
	 * Shows the menu of the game
	 * @param key 
	 */
	private void main_menu(int key) {

	}
	
	/**
	 * Shows the options menu of the game
	 * @param key
	 */
	private void options_menu(int key){
		
	}

	/**
	 * Traditional mode of the game
	 * @param key 
	 */
	private void t_mode(int key) {

	}

	/**
	 * Super-Bomber mode of the game
	 * @param key 
	 */
	private void sb_mode(int key) {

	}

	/**
	 * Pause mode
	 * @param key 
	 */
	private void pause(int key) {

	}

	/**
	 * Show the ranking of the Super-Bomber mode
	 * @param key 
	 */
	private void ranks(int key) {

	}

	/**
	 * Let the player put a name to the highscore (entering the top 10)
	 * @param key 
	 */
	private void top10(int key) {

	}
	

	private void game_over(int key) {
	}
}
