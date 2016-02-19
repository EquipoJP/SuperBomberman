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
	private KEY key;
	
	public Input(Game game) {
		this.game = game;
		this.key = KEY.NO_KEY;
		game.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			setKey(KEY.UP);
			break;
		case KeyEvent.VK_DOWN:
			setKey(KEY.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			setKey(KEY.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			setKey(KEY.RIGHT);
			break;
		case KeyEvent.VK_SPACE:
			setKey(KEY.SPACE);
			break;
		case KeyEvent.VK_ENTER:
			setKey(KEY.ENTER);
			break;
		case KeyEvent.VK_ESCAPE:
			setKey(KEY.ESCAPE);
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_SPACE ||
				e.getKeyCode() == KeyEvent.VK_ENTER ||
				e.getKeyCode() == KeyEvent.VK_ESCAPE){
			setKey(KEY.NO_KEY);
		}
	}
	
	private synchronized void setKey(KEY k){
		key = k;
	}
	
	public synchronized KEY getKey(){
		return key;
	}

}
