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
		switch (state) {
		case INTRO:
			intro();
			break;
		case MENU:
			menu();
			break;
		case T_MODE:
			t_mode();
			break;
		case SB_MODE:
			sb_mode();
			break;
		case PAUSE:
			pause();
			break;
		case RANKS:
			ranks();
			break;
		case TOP10:
			top10();
			break;
		}

		state = next_state;
	}

	/**
	 * Shows the introduction of the game
	 */
	private void intro() {

	}

	/**
	 * Shows the menu of the game
	 */
	private void menu() {

	}

	/**
	 * Traditional mode of the game
	 */
	private void t_mode() {

	}

	/**
	 * Super-Bomber mode of the game
	 */
	private void sb_mode() {

	}

	/**
	 * Pause mode
	 */
	private void pause() {

	}

	/**
	 * Show the ranking of the Super-Bomber mode
	 */
	private void ranks() {

	}

	/**
	 * Let the player put a name to the highscore (entering the top 10)
	 */
	private void top10() {

	}
}
