/**
 * Class containing the player's input
 */
package logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Input implements KeyListener {

	/* public attributes */
	public final int NO_KEY = 0;
	public final int UP = 1;
	public final int DOWN = 2;
	public final int RIGHT = 3;
	public final int LEFT = 4;
	public final int SPACE = 5;
	public final int ENTER = 6;
	public final int SCP = 7;

	/* private attributes */
	private int nextKey;

	/**
	 * Initializes the next key to be processed
	 */
	public Input() {
		setNextKey(NO_KEY);
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

	@Override
	public void keyPressed(KeyEvent ke) {
		switch(ke.getKeyCode()){
		case KeyEvent.VK_UP:	// up
			setNextKey(UP);
			System.out.println("UP");
			break;
		case KeyEvent.VK_DOWN:	// down
			setNextKey(DOWN);
			System.out.println("DOWN");
			break;
		case KeyEvent.VK_RIGHT:	//right
			setNextKey(RIGHT);
			System.out.println("RIGHT");
			break;
		case KeyEvent.VK_LEFT:	// left
			setNextKey(LEFT);
			System.out.println("LEFT");
			break;
		case KeyEvent.VK_SPACE:	// space
			setNextKey(SPACE);
			System.out.println("SPACE");
			break;
		case KeyEvent.VK_ENTER:	// enter
			setNextKey(ENTER);
			System.out.println("ENTER");
			break;
		case KeyEvent.VK_ESCAPE:	//scp
			setNextKey(SCP);
			System.out.println("UP");
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		switch(ke.getKeyCode()){
		case KeyEvent.VK_UP:	// up
			setNextKey(NO_KEY);
			break;
		case KeyEvent.VK_DOWN:	// down
			setNextKey(NO_KEY);
			break;
		case KeyEvent.VK_RIGHT:	//right
			setNextKey(NO_KEY);
			break;
		case KeyEvent.VK_LEFT:	// left
			setNextKey(NO_KEY);
			break;
		case KeyEvent.VK_SPACE:	// space
			setNextKey(NO_KEY);
			break;
		case KeyEvent.VK_ENTER:	// enter
			setNextKey(NO_KEY);
			break;
		case KeyEvent.VK_ESCAPE:	//scp
			setNextKey(NO_KEY);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub

	}

}
