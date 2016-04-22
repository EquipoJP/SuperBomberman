/**
 * Controls' room
 */
package graphics.rooms.controlsMenu;

import graphics.effects.Button;
import graphics.effects.ControlButton;
import graphics.effects.PintableButton;
import graphics.rooms.Room;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import kuusisto.tinysound.Sound;
import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import sound.MusicRepository;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Controls extends Room {

	private static final int PADDING_BORDER = 25;
	private static final int INTERBUTTON_BORDER = 25;

	public Button[][] menuButtons;
	public boolean selectingKey = false;
	private final int ROWS = 8;
	private final int COLUMNS = 2;
	private int selected;
	private int column;

	/**
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @param n
	 *            name
	 */
	public Controls(int w, int h, String n) {
		super(w, h, n);
	}

	@Override
	public void load() {
		ControlsRepository.load();

		this.background = ControlsRepository.background;

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
		int x2 = x + _width / 2;
		int y = PADDING_BORDER;
		int i = 0;

		// UP button
		Sprite sprite = ControlsRepository.up;
		createOneButton(x, y, sprite, KEY.UP, i, 0, x2, i, 1, getKeyText(KEY.UP));

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		i++;

		// DOWN button
		sprite = ControlsRepository.down;
		createOneButton(x, y, sprite, KEY.DOWN, i, 0, x2, i, 1, getKeyText(KEY.DOWN));

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		i++;

		// LEFT button
		sprite = ControlsRepository.left;
		createOneButton(x, y, sprite, KEY.LEFT, i, 0, x2, i, 1, getKeyText(KEY.LEFT));

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		i++;

		// RIGHT button
		sprite = ControlsRepository.right;
		createOneButton(x, y, sprite, KEY.RIGHT, i, 0, x2, i, 1, getKeyText(KEY.RIGHT));

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		i++;

		// BOMB button
		sprite = ControlsRepository.bomb;
		createOneButton(x, y, sprite, KEY.BOMB, i, 0, x2, i, 1, getKeyText(KEY.BOMB));

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		i++;

		// PAUSE button
		sprite = ControlsRepository.pause;
		createOneButton(x, y, sprite, KEY.PAUSE, i, 0, x2, i, 1, getKeyText(KEY.PAUSE));

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;
		i++;

		// CONFIRM button
		sprite = ControlsRepository.confirm;
		createOneButton(x, y, sprite, KEY.CONFIRM, i, 0, x2, i, 1, getKeyText(KEY.CONFIRM));

		Sprite next = ControlsRepository.continueButton;
		y = this.height - PADDING_BORDER - next.getHeight() / 2;
		i++;

		menuButtons[i][0] = new Button(width / 2, y, this, next, new Runnable() {

			@Override
			public void run() {
				StatesMachine.goToRoom(STATE.MAIN_MENU, false);
			}
		});

		menuButtons[7][1] = menuButtons[7][0];
		addObjeto(menuButtons[7][0]);
	}

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param sprite
	 *            sprite
	 * @param key
	 *            key attached to the button
	 * @param i1
	 *            first row index
	 * @param j1
	 *            first column index
	 * @param x2
	 *            second x coordinate
	 * @param i2
	 *            second row index
	 * @param j2
	 *            second column index
	 * @param text
	 *            text to paint
	 */
	private void createOneButton(int x, int y, Sprite sprite, KEY key, int i1, int j1, int x2, int i2, int j2,
			String text) {
		menuButtons[i1][j1] = new ControlButton(x, y + sprite.getHeight() / 2, this, sprite, key, new Runnable() {

			@Override
			public void run() {
				// Set new text to pintable
				ControlButton cb = ((ControlButton) menuButtons[i1][j1]);
				cb.setPintableText("...");
				cb.selecting = true;
			}
		});
		addObjeto(menuButtons[i1][j1]);

		menuButtons[i2][j2] = new PintableButton(x2, y + sprite.getHeight() / 2 - sprite.getCenterY(), this, text);
		addObjeto(menuButtons[i2][j2]);
		((ControlButton) menuButtons[i1][j1]).setPintable((PintableButton) menuButtons[i2][j2]);
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
	 * Deselects a button
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
	 * Previous button
	 */
	private void previous() {
		int no = ((selected - 1) % menuButtons.length + menuButtons.length) % menuButtons.length;
		select(no);
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
		if (!selectingKey) {
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
				break;
			case LEFT:
				break;
			case CONFIRM:
				confirm();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Get text from a key
	 * 
	 * @param key
	 *            key to get the text from
	 * @return text attached to the key
	 */
	private String getKeyText(KEY key) {
		int code = StatesMachine.input.mapper.getCodeKey(key);
		String returned = ControlButton.defaultTextes(code);
		if (returned == null) {
			returned = KeyEvent.getKeyText(code);
		}
		return returned;
	}
}
