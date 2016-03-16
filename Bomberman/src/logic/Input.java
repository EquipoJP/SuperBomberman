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

	public enum KEY {
		UP, DOWN, LEFT, RIGHT, SPACE, ENTER, ESCAPE, NO_KEY
	}

	private boolean up, down, left, right, space, enter, escape;
	private KEY lastKey;

	public Input(Game game) {
		this.game = game;

		up = false;
		down = false;
		left = false;
		right = false;
		space = false;
		enter = false;
		escape = false;

		lastKey = KEY.NO_KEY;
		game.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_SPACE:
			space = true;
			break;
		case KeyEvent.VK_ENTER:
			enter = true;
			break;
		case KeyEvent.VK_ESCAPE:
			escape = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_SPACE:
			space = false;
			break;
		case KeyEvent.VK_ENTER:
			enter = false;
			break;
		case KeyEvent.VK_ESCAPE:
			escape = false;
			break;
		}
	}

	public synchronized KEY getKey() {
		return getNoReboundsKey();
	}

	private KEY getNoReboundsKey() {
		/*
		 * Key's priority order: 
		 * 1. Escape 
		 * 2. Enter 
		 * 3. Space 
		 * 4. Right 
		 * 5. Left 
		 * 6. Down 
		 * 7. Up
		 */
		KEY key = KEY.NO_KEY;
		if (escape) {
			key = KEY.ESCAPE;
		}
		else if (enter) {
			key = KEY.ENTER;
		}
		else if (space) {
			key = KEY.SPACE;
		}
		else if (right) {
			key = KEY.RIGHT;
		}
		else if (left) {
			key = KEY.LEFT;
		}
		else if (down) {
			key = KEY.DOWN;
		}
		else if (up) {
			key = KEY.UP;
		}
		
		if(key != lastKey){
			lastKey = key;
			return key;
		}
		else{
			return KEY.NO_KEY;
		}
	}

	public synchronized KEY getDirection() {
		/*
		 * Key's priority order: 
		 * 1. Right 
		 * 2. Left 
		 * 3. Down 
		 * 4. Up
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
