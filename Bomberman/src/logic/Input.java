/**
 * Class containing the player's input
 */
package logic;

import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Input {

	/* public attributes */
	public static final int NO_KEY = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int LEFT = 4;
	public static final int SPACE = 5;
	public static final int ENTER = 6;
	public static final int SCP = 7;

	public static final String up = "up", down = "down", right = "right",
			left = "left", space = "space", enter = "enter", scp = "escape";
	public static final String pressed = " pressed", released = " released";

	/* private attributes */
	private int nextKey;

	/**
	 * Initializes the next key to be processed
	 * 
	 * @param main
	 */
	public Input(JComponent jc) {
		setNextKey(NO_KEY);
		setKeyBindings(jc);
	}

	/**
	 * Sets the next key to be processed to @param value
	 * 
	 * @param value
	 *            value for the key to be processed
	 */
	public synchronized void setNextKey(int value) {
		nextKey = value;
	}

	/**
	 * Gets the next key to be processed and resets it to default value
	 * 
	 * @return the next key to be processed
	 */
	public synchronized int getNextKey() {
		int value = nextKey;
		nextKey = NO_KEY;
		return value;
	}

	/**
	 * Initializes the key bindings for the main panel of the game (up, down,
	 * right, left, enter, sapce, escape)
	 * 
	 * @param panel
	 *            main panel of the game
	 */
	private void setKeyBindings(JComponent jc) {
		// get input map and action map
		InputMap im = jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = jc.getActionMap();

		// put the key pressed & released events
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), up + pressed);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), up + released);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), down
				+ pressed);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), down
				+ released);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), right
				+ pressed);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), right
				+ released);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), left
				+ pressed);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), left
				+ released);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), space
				+ pressed);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), space
				+ released);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), enter
				+ pressed);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), enter
				+ released);

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), scp
				+ pressed);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), scp
				+ released);

		// put the actions associated with the events
		am.put(up + pressed, new KeyAction(up + pressed, this));
		am.put(down + pressed, new KeyAction(down + pressed, this));
		am.put(right + pressed, new KeyAction(right + pressed, this));
		am.put(left + pressed, new KeyAction(left + pressed, this));
		am.put(space + pressed, new KeyAction(space + pressed, this));
		am.put(enter + pressed, new KeyAction(enter + pressed, this));
		am.put(scp + pressed, new KeyAction(scp + pressed, this));

		am.put(up + released, new KeyAction(up + released, this));
		am.put(down + released, new KeyAction(down + released, this));
		am.put(right + released, new KeyAction(right + released, this));
		am.put(left + released, new KeyAction(left + released, this));
		am.put(space + released, new KeyAction(space + released, this));
		am.put(enter + released, new KeyAction(enter + released, this));
		am.put(scp + released, new KeyAction(scp + released, this));
	}

}
