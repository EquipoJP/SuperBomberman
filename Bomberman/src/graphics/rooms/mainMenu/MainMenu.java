/**
 * Class representing the main menu screen
 */
package graphics.rooms.mainMenu;

import graphics.effects.Button;
import graphics.effects.Visual;
import graphics.rooms.Room;

import java.awt.Graphics;

import kuusisto.tinysound.Sound;
import logic.Global;
import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import sound.MusicRepository;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class MainMenu extends Room {

	private Button[][] menuButtons;
	private final int ROWS = 4;
	private final int COLUMNS = 2;
	private int selected;
	private int column;

	private static final int PADDING_BORDER = 25;
	private static final int INTERBUTTON_BORDER = 25;
	private static final int TITLEBUTTON_BORDER = 64;

	/**
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @param n
	 *            name
	 */
	public MainMenu(int w, int h, String n) {
		super(w, h, n);
	}

	@Override
	public void load() {
		MainMenuRepository.load();

		this.background = MainMenuRepository.background;

		Sprite title = MainMenuRepository.titleButton;
		int x = width / 2;
		int y = PADDING_BORDER + title.getHeight() / 2;

		addObjeto(new Visual(x, y, this, title));

		createButtons();
		selected = 0;
		column = 0;
		select(0);

		setMusic(MusicRepository.menu, true);
	}

	/**
	 * Create some buttons
	 */
	private void createButtons() {
		menuButtons = new Button[ROWS][COLUMNS];

		// variables
		int _width = width / 2;
		int x = _width / 2;
		int x2 = x + _width;
		int y = PADDING_BORDER + MainMenuRepository.titleButton.getHeight() + TITLEBUTTON_BORDER;

		// Game button
		Sprite sprite = MainMenuRepository.gameButton;
		menuButtons[0][0] = new Button(x, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				Global.is2D = true;
				StatesMachine.goToRoom(STATE.GAME, false);
			}
		});
		addObjeto(menuButtons[0][0]);
		
		// 3D Game button
		sprite = MainMenuRepository.d3gameButton;
		menuButtons[0][1] = new Button(x2, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				StatesMachine.goToRoom(STATE.GAME3D, false);
			}
		});
		addObjeto(menuButtons[0][1]);

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// Options button
		sprite = MainMenuRepository.optionsButton;
		menuButtons[1][0] = new Button(x, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				StatesMachine.goToRoom(STATE.OPTIONS_MENU, false);
			}
		});
		addObjeto(menuButtons[1][0]);

		// Controls button
		sprite = MainMenuRepository.controlsButton;
		menuButtons[1][1] = new Button(x2, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				StatesMachine.goToRoom(STATE.CONTROLS, false);
			}
		});
		addObjeto(menuButtons[1][1]);

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		
		// Ranking button
		sprite = MainMenuRepository.rankingButton;
		menuButtons[2][0] = new Button(x, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				StatesMachine.goToRoom(STATE.RANKS, false);
			}
		});
		addObjeto(menuButtons[2][0]);

		// Credits button
		sprite = MainMenuRepository.creditsButton;
		menuButtons[2][1] = new Button(x2, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				StatesMachine.goToRoom(STATE.CREDITS, false);
			}
		});
		addObjeto(menuButtons[2][1]);

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// Quit button
		sprite = MainMenuRepository.quitButton;
		menuButtons[3][0] = new Button(width / 2, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				Global.stopGame();
				System.exit(0);
			}
		});
		menuButtons[3][1] = menuButtons[3][0];
		addObjeto(menuButtons[3][0]);
	}

	/**
	 * Selects a button
	 * 
	 * @param no
	 *            number of the selected button
	 */
	private void select(int no) {
		unselect();
		selected = no;
		menuButtons[no][column].select();
	}

	/**
	 * Deselects buttons
	 */
	private void unselect() {
		for (int i = 0; i < menuButtons.length; i++) {
			for (int j = 0; j < menuButtons[i].length; j++) {
				if (menuButtons[i][j] != null) {
					menuButtons[i][j].unselect();
				}
			}
		}
	}

	/**
	 * Next button
	 */
	private void next() {
		int no = (selected + 1) % menuButtons.length;
		select(no);
	}

	/**
	 * Next column button
	 */
	private void nextColumn() {
		column = (column + 1) % COLUMNS;
		select(selected);
	}

	/**
	 * Previous button
	 */
	private void previous() {
		int no = ((selected - 1) % menuButtons.length + menuButtons.length) % menuButtons.length;
		select(no);
	}

	/**
	 * Previous column button
	 */
	private void previousColumn() {
		column = ((column - 1) % COLUMNS + COLUMNS) % COLUMNS;
		select(selected);
	}

	/**
	 * Confirm the button
	 */
	private void confirm() {
		menuButtons[selected][column].confirm();
	}

	@Override
	public void drawBackground(Graphics g) {
		if (!loadComplete()) {
			return;
		}
		g.clearRect(0, 0, width, height);
		if (background != null) {
			g.drawImage(background.getSubsprites()[0], 0, 0, null);
		}
	}

	@Override
	public void step(KEY key, KEY direction) {
		super.step(key, direction);
		if (!loadComplete()) {
			return;
		}
		Sound sel = MusicRepository.select;
		switch (key) {
		case DOWN:
			next();
			sel.play();
			break;
		case UP:
			previous();
			sel.play();
			break;
		case RIGHT:
			nextColumn();
			sel.play();
			break;
		case LEFT:
			previousColumn();
			sel.play();
			break;
		case CONFIRM:
			confirm();
			break;
		default:
			break;
		}
	}
}
