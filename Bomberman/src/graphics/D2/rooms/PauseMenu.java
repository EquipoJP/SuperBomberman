/**
 * Class representing the pause menu (independent of the game mode currently being played)
 */
package graphics.D2.rooms;

import graphics.effects.Button;
import graphics.effects.Visual;

import java.awt.Graphics;

import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class PauseMenu extends Room {

	private Sprite bg;
	
	private Button[] menuButtons;
	private int selected;
	private KEY lastKey;
	
	private STATE mode;
	
	private static enum selection {
		CONTINUE, QUIT
	};
	
	private static final int PADDING_BORDER = 25;
	private static final int INTERBUTTON_BORDER = 25;
	private static final int TITLEBUTTON_BORDER = 64;

	public PauseMenu(int w, int h, String n, STATE mode) {
		super(w, h, n);

		bg = null; // TODO get the sprite for the background
		Sprite title = Initialization.getSpriteFromMenu("TITLE_BUTTON");
		int x = w / 2;
		int y = PADDING_BORDER + title.getHeight() / 2;

		addObjeto(new Visual(x, y, this, title));

		createButtons();
		selected = 0;
		select(0);
		
		lastKey = StatesMachine.input.getKey();
		this.mode = mode;
	}
	
	private void createButtons() {
		menuButtons = new Button[selection.values().length];

		// variables
		int x = this.width / 2;
		int y = PADDING_BORDER
				+ Initialization.getSpriteFromMenu("TITLE_BUTTON").getHeight()
				+ TITLEBUTTON_BORDER;

		// Continue button
		Sprite sprite = Initialization.getSpriteFromMenu("CONTINUE_BUTTON");
		menuButtons[0] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		
		// Quit button
		sprite = Initialization.getSpriteFromMenu("QUIT_BUTTON");
		menuButtons[1] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		
		for(Button b : menuButtons){
			addObjeto(b);
		}
	}

	@Override
	public void drawBackground(Graphics g) {
		g.clearRect(0, 0, width, height);
		if(bg != null){
			g.drawImage(bg.getSubsprites()[0], 0, 0, null);
		}
	}
	
	private void select(int no) {
		menuButtons[selected].unselect();
		selected = no;
		menuButtons[no].select();
	}
	
	private void next(){
		if(lastKey != KEY.DOWN){
			int no = (selected + 1) % menuButtons.length;
			select(no);
		}
	}
	
	private void previous(){
		if(lastKey != KEY.UP){
			// -1 % 5 = -1. With this thing it gets 4
			int no = ((selected - 1) % menuButtons.length + menuButtons.length) % menuButtons.length;
			select(no);
		}
	}

	private void confirm() {
		if(lastKey != KEY.ENTER){
			switch (selected) {
			case 0:
				StatesMachine.goToRoom(mode, false);
				break;
			case 1:
				StatesMachine.goToRoom(STATE.MAIN_MENU, false);
				break;
			}
		}
	}
	
	@Override
	public void step(KEY key) {
		super.step(key);
		switch (key) {
		case DOWN:
			next();
			break;
		case UP:
			previous();
			break;
		case ENTER:
			confirm();
			break;
		default:
			break;
		}
		
		lastKey = key;
	}

}
