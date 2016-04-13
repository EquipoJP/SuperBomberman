package graphics.rooms.controlsMenu;

import logic.Sprite;
import main.Initialization;

public class ControlsRepository {

	static Sprite background = null;

	static Sprite titleButton = null;
	static Sprite continueButton = null;
	
	static Sprite up = null;
	static Sprite down = null;
	static Sprite left = null;
	static Sprite right = null;
	static Sprite pause = null;
	static Sprite bomb = null;
	static Sprite confirm = null;

	/**
	 * Load resources
	 */
	public static void load() {
		loadBackground();
		loadTitleButton();
		loadContinueButton();
		
		loadControls();
	}

	/**
	 * Load background
	 */
	private static void loadBackground() {
		if (background == null) {
			background = Initialization
					.getSpriteFromMenu(Initialization.MENUS.BACKGROUND
							.toString());
		}
	}

	/**
	 * Load the title button
	 */
	private static void loadTitleButton() {
		if (titleButton == null) {
			titleButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.TITLE_BUTTON
							.toString());
		}
	}

	/**
	 * Load the continue button
	 */
	private static void loadContinueButton() {
		if (continueButton == null) {
			continueButton = Initialization
					.getSpriteFromMenu(Initialization.MENUS.CONTINUE_BUTTON
							.toString());
		}
	}
	
	/**
	 * Load control buttons
	 */
	private static void loadControls(){
		if(up == null){
			up = Initialization.getSpriteFromMenu(Initialization.CTRLS.CTRL_UP.toString());
		}
		if(down == null){
			down = Initialization.getSpriteFromMenu(Initialization.CTRLS.CTRL_DOWN.toString());
		}
		if(left == null){
			left = Initialization.getSpriteFromMenu(Initialization.CTRLS.CTRL_LEFT.toString());
		}
		if(right == null){
			right = Initialization.getSpriteFromMenu(Initialization.CTRLS.CTRL_RIGHT.toString());
		}
		if(pause == null){
			pause = Initialization.getSpriteFromMenu(Initialization.CTRLS.CTRL_PAUSE.toString());
		}
		if(bomb == null){
			bomb = Initialization.getSpriteFromMenu(Initialization.CTRLS.CTRL_BOMB.toString());
		}
		if(confirm == null){
			confirm = Initialization.getSpriteFromMenu(Initialization.CTRLS.CTRL_CONFIRM.toString());
		}
	}

}
