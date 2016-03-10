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
public class Input extends KeyAdapter{

	Game game;
	public enum KEY{
		UP, DOWN, LEFT, RIGHT, SPACE, ENTER, ESCAPE, NO_KEY
	}
	
	private boolean up, down, left, right, space, enter, escape;
	
	public Input(Game game) {
		this.game = game;
		
		up = false;
		down = false;
		left = false;
		right = false;
		space = false;
		enter = false;
		escape = false;
		
		game.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
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
		switch(e.getKeyCode()){
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
	
	public synchronized KEY getKey(){
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
		if(escape){
			return KEY.ESCAPE;
		}
		if(enter){
			return KEY.ENTER;
		}
		if(space){
			return KEY.SPACE;
		}
		if(right){
			return KEY.RIGHT;
		}
		if(left){
			return KEY.LEFT;
		}
		if(down){
			return KEY.DOWN;
		}
		if(up){
			return KEY.UP;
		}
		return KEY.NO_KEY;
	}

}
