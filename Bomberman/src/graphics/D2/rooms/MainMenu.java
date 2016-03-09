/**
 * Class representing the main menu screen
 */
package graphics.D2.rooms;

import graphics.effects.Button;
import graphics.effects.Cursor;

import java.awt.Graphics;

import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class MainMenu extends Room {

	private static enum selection {
		GAME, OPTIONS, RANKING, CREDITS, QUIT
	};
	
	private Sprite background;

	private selection[] menuOptions = { selection.GAME, selection.OPTIONS,
			selection.RANKING, selection.CREDITS, selection.QUIT };
	
	private Button[] menuButtons;
	private Cursor cursor;
	
	private int selected;

	public MainMenu(int w, int h, String n, Sprite background) {
		super(w, h, n);
		
		createButtons();
		selected = 0;
		
		System.out.println("MAIN MENU");
	}

	private void createButtons() {
		// TODO Auto-generated method stub
		// TODO create buttons for the menu and a cursor
	}

	@Override
	public void drawBackground(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0, 0, width, height);
		if(background != null){
			g.drawImage(background.getSubsprites()[0], 0, 0, null);
		}
	}

	@Override
	public void step(KEY key) {
		// TODO Auto-generated method stub
		
		// TODO procesar la key como si se tratara de un controlador de botones
		
		StatesMachine.goToRoom(STATE.SB_MODE);
	}

}
