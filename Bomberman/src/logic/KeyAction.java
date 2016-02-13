/**
 * Class containing the actions to take from the key pressed/released events
 */
package logic;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
@SuppressWarnings("serial")
public class KeyAction extends AbstractAction {

	/* private attributes */
	private Input input;

	/**
	 * @param actionCommand
	 *            information to pass to the actionPermed() method
	 * @param input
	 *            initializes el input attribute to change the next key to be
	 *            processed
	 */
	public KeyAction(String actionCommand, Input input) {
		putValue(ACTION_COMMAND_KEY, actionCommand);
		this.input = input;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// variables' definition
		int key = -1;
		boolean pressed = false;

		// parse the action command to get the key pressed/released
		String action = arg0.getActionCommand();
		String[] split = action.split(" ");
		String actionKey = split[0];
		String actionPressed = split[1];

		// put values
		pressed = actionPressed.equalsIgnoreCase(Input.pressed);
		switch (actionKey) {
		case Input.up:
			key = Input.UP;
			break;
		case Input.down:
			key = Input.DOWN;
			break;
		case Input.right:
			key = Input.RIGHT;
			break;
		case Input.left:
			key = Input.LEFT;
			break;
		case Input.space:
			key = Input.SPACE;
			break;
		case Input.enter:
			key = Input.ENTER;
			break;
		case Input.scp:
			key = Input.SCP;
			break;
		}

		if (pressed) {
			input.setNextKey(key);
		} else {
			input.setNextKey(Input.NO_KEY);
		}

		// for debug purposes
		System.out.println(arg0.getActionCommand());
	}

}
