/**
 * Class containing the player's input
 */
package logic;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.Game;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Input extends KeyAdapter {

	Game game;
	public KeyMapper mapper;

	public enum KEY {
		UP, DOWN, LEFT, RIGHT, BOMB, CONFIRM, PAUSE, NO_KEY
	}

	private boolean up, down, left, right, bomb, confirm, pause;
	private KEY lastKey;

	/**
	 * @param game
	 *            game to which attach the input
	 */
	public Input(Game game) {
		this.game = game;
		mapper = new KeyMapper();

		up = false;
		down = false;
		left = false;
		right = false;
		bomb = false;
		confirm = false;
		pause = false;

		lastKey = KEY.NO_KEY;
		game.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == mapper.getCodeKey(KEY.UP)) {
			up = true;
		} else if (code == mapper.getCodeKey(KEY.DOWN)) {
			down = true;
		} else if (code == mapper.getCodeKey(KEY.LEFT)) {
			left = true;
		} else if (code == mapper.getCodeKey(KEY.RIGHT)) {
			right = true;
		} else if (code == mapper.getCodeKey(KEY.BOMB)) {
			bomb = true;
		} else if (code == mapper.getCodeKey(KEY.CONFIRM)) {
			confirm = true;
		} else if (code == mapper.getCodeKey(KEY.PAUSE)) {
			pause = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == mapper.getCodeKey(KEY.UP)) {
			up = false;
		} else if (code == mapper.getCodeKey(KEY.DOWN)) {
			down = false;
		} else if (code == mapper.getCodeKey(KEY.LEFT)) {
			left = false;
		} else if (code == mapper.getCodeKey(KEY.RIGHT)) {
			right = false;
		} else if (code == mapper.getCodeKey(KEY.BOMB)) {
			bomb = false;
		} else if (code == mapper.getCodeKey(KEY.CONFIRM)) {
			confirm = false;
		} else if (code == mapper.getCodeKey(KEY.PAUSE)) {
			pause = false;
		}
	}

	/**
	 * Gets the key pressed (no rebounds, does not take into account
	 * long-pressed keys)
	 * 
	 * @return key
	 */
	public synchronized KEY getKey() {
		return getNoReboundsKey();
	}

	/**
	 * Gets the key pressed. No rebounds, does not take into account
	 * long-pressed keys.
	 * 
	 * @return key
	 */
	private KEY getNoReboundsKey() {
		/*
		 * Key's priority order: 1. Escape 2. Enter 3. Space 4. Right 5. Left 6.
		 * Down 7. Up
		 */
		KEY key = KEY.NO_KEY;
		if (pause) {
			key = KEY.PAUSE;
		} else if (confirm) {
			key = KEY.CONFIRM;
		} else if (bomb) {
			key = KEY.BOMB;
		} else if (right) {
			key = KEY.RIGHT;
		} else if (left) {
			key = KEY.LEFT;
		} else if (down) {
			key = KEY.DOWN;
		} else if (up) {
			key = KEY.UP;
		}

		if (key != lastKey) {
			lastKey = key;
			return key;
		} else {
			return KEY.NO_KEY;
		}
	}

	/**
	 * Gets the direction key, taking long-pressed keys into account
	 * 
	 * @return key
	 */
	public synchronized KEY getDirection() {
		/*
		 * Key's priority order: 1. Right 2. Left 3. Down 4. Up
		 */
		if (right) {
			return KEY.RIGHT;
		}
		if (left) {
			return KEY.LEFT;
		}
		if (down) {
			return KEY.DOWN;
		}
		if (up) {
			return KEY.UP;
		}
		return KEY.NO_KEY;
	}

}
