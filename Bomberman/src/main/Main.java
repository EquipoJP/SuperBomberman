/**
 * Main class containing the applet that runs the game
 */
package main;

import java.awt.Color;
import java.awt.Frame;

import javax.swing.JApplet;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
@SuppressWarnings("serial")
public class Main extends JApplet {
	
	/* The object that runs the game loop and its thread */
	private GameLoop gameLoop = null;
	private Thread tGameLoop = null;

	/**
	 * Init() method executes once, when the applet is created, and takes
	 * arguments and parameters. Also good for reading ini files, etc
	 */
	@Override
	public void init() {
		setSize(800, 800);
        setBackground(Color.BLACK);
        this.getContentPane().setBackground(getBackground());
        setFocusable(true);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("Bomberman");
        frame.setResizable(false);
	}

	/**
	 * Start() method is called many times, the first one after the init()
	 * method. Anytime the applet is stopped (not destroyed) and then resumed,
	 * start() method will be called
	 */
	@Override
	public void start() {
		gameLoop = new GameLoop(this.getRootPane());
		tGameLoop = new Thread(gameLoop);
		tGameLoop.start();
		gameLoop.stoped = false;
	}

	/**
	 * Stop() method is called many times, always after the start() method is
	 * called. It should undo what start() does
	 */
	@Override
	public void stop() {
		super.stop();
	}

	/**
	 * Destroy() method is called once, when the applet is destroyed. It should
	 * undo init() method and make the final cleanup
	 */
	@Override
	public void destroy() {
		gameLoop.stoped = true;
		super.destroy();
	}
}
