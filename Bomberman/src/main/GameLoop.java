/**
 * Class that implements the heart of the game (the game loop)
 */
package main;

import java.awt.Graphics;

import javax.swing.JComponent;

import logic.Input;
import logic.StatesMachine;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class GameLoop implements Runnable {

	/* private attributes */
	boolean stoped;
	private Input input;
	private Graphics graphics;

	/**
	 * Constructor method
	 * 
	 * @param jc
	 *            component in which the keybinder has to listen
	 * @param g
	 *            section of the screen to paint (for the rooms)
	 */
	public GameLoop(JComponent jc, Graphics g) {
		stoped = false;
		input = new Input(jc);
	}

	@Override
	public void run() {
		StatesMachine sm = new StatesMachine(input, graphics);

		while (!stoped) {
			sm.stateMachine();
			try {
				// 17 ms => 60 fps
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
