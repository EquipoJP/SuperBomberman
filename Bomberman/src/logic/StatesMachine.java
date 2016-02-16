/**
 * Class containing the states machine
 */
package logic;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class StatesMachine {

	/* machine's states */
	public final int INTRO = 0;
	public final int MENU = 1;
	public final int T_MODE = 2;
	public final int SB_MODE = 3;
	public final int PAUSE = 4;
	public final int RANKS = 5;
	public final int TOP10 = 6;

	/* private attributes */
	private int state;
	private int next_state;
	private Input input;

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
		case MENU:
			menu(key);
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
	private void menu(int key) {

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
}
