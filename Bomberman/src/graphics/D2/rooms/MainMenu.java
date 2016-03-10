/**
 * Class representing the main menu screen
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
public class MainMenu extends Room {

	private static enum selection {
		GAME, OPTIONS, RANKING, CREDITS, QUIT
	};

	private Sprite background;

	private Button[] menuButtons;
	private int selected;

	private static final int PADDING_BORDER = 25;
	private static final int INTERBUTTON_BORDER = 25;
	private static final int TITLEBUTTON_BORDER = 64;
	
	private KEY lastKey;

	public MainMenu(int w, int h, String n, Sprite background) {
		super(w, h, n);

		Sprite title = Initialization.getSpriteFromMenu("TITLE_BUTTON");
		int x = w / 2;
		int y = PADDING_BORDER + title.getHeight() / 2;

		addObjeto(new Visual(x, y, this, title));

		createButtons();
		selected = 0;
		select(0);
		
		lastKey = KEY.NO_KEY;

		System.out.println("MAIN MENU");
	}

	private void createButtons() {
		menuButtons = new Button[selection.values().length];

		// variables
		int x = this.width / 2;
		int y = PADDING_BORDER
				+ Initialization.getSpriteFromMenu("TITLE_BUTTON").getHeight()
				+ TITLEBUTTON_BORDER;

		// Game button
		Sprite sprite = Initialization.getSpriteFromMenu("GAME_BUTTON");
		menuButtons[0] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		
		// Options button
		sprite = Initialization.getSpriteFromMenu("OPTIONS_BUTTON");
		menuButtons[1] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		
		// Ranking button
		sprite = Initialization.getSpriteFromMenu("GAME_BUTTON");
		menuButtons[2] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		
		// Credits button
		sprite = Initialization.getSpriteFromMenu("CREDITS_BUTTON");
		menuButtons[3] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		
		// Quit button
		sprite = Initialization.getSpriteFromMenu("QUIT_BUTTON");
		menuButtons[4] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		
		for(Button b : menuButtons){
			addObjeto(b);
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
		switch (selected) {
		case 0:
			StatesMachine.goToRoom(STATE.SB_MODE);
			break;
		case 1:
			StatesMachine.goToRoom(STATE.OPTIONS_MENU);
			break;
		case 2:
			StatesMachine.goToRoom(STATE.RANKS);
			break;
		case 3:
			StatesMachine.goToRoom(STATE.CREDITS);
			break;
		case 4:
			System.exit(0);
			break;
		default:
			System.exit(-1);
			break;
		}
	}
	
	@Override
	public void drawBackground(Graphics g) {
		g.clearRect(0, 0, width, height);
		if (background != null) {
			g.drawImage(background.getSubsprites()[0], 0, 0, null);
		}
	}

	@Override
	public void step(KEY key) {
		super.step(key);
		// TODO procesar la key como si se tratara de un controlador de botones
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
