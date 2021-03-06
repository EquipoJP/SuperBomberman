/**
 * Main class. Loads up the game. Have the game's heart.
 */
package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import logic.Global;
import logic.Input;
import logic.StatesMachine;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 5374655311458429613L;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Super Bomberman";
	public static final int NUM_BUFFERS = 3;
	public static final double FPS = 60.0;

	public static JFrame frame;
	public Input input;

	public boolean running = false;
	private Thread thread;

	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	/**
	 * Start the game
	 */
	private synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stop the game
	 */
	private synchronized void stop() {
		if (!running) {
			return;
		}

		Global.stopGame();
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	/**
	 * Initializes the game
	 */
	private synchronized void init() {
		Global.startGame();
		input = new Input(this);
		StatesMachine.initStatesMachine(input);
	}

	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = FPS; // FPS

		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		int updates = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			// Game Loop
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				StatesMachine.stateMachine();
				render();
				updates++;
				delta--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				if (Global.DEBUG) {
					System.out.println(updates + " fps " + "STATE: " + StatesMachine.state);
				}
				updates = 0;
			}
		}
		stop();
	}

	/**
	 * Method to render the screen on triple buffering stategy to avoid
	 * flickering
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			// triple buffering strategy
			createBufferStrategy(NUM_BUFFERS);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		// /////////////////////////////////////////////////

		Graphics gg = image.createGraphics();
		StatesMachine.render(gg);
		gg.dispose();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

		// /////////////////////////////////////////////////
		g.dispose();
		bs.show();
	}

	/**
	 * Main method to start, initialize and load up the game
	 * 
	 * @param args
	 *            no arguments
	 */
	public static void main(String[] args) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		game.setMaximumSize(new Dimension(WIDTH, HEIGHT));

		Image image = null;
		try {
			InputStream is = Game.class.getClassLoader().getResourceAsStream("icon.png");
			image = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		frame = new JFrame(TITLE);
		frame.setIconImage(image);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.setFocusable(true);

		game.start();
	}

	/**
	 * Reloads the key mapping
	 */
	public void reloadMapping() {
		input.reloadMapping();
	}
}
