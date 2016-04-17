package graphics.rooms.controlsMenu;

import graphics.effects.Button;
import graphics.effects.ControlButton;
import graphics.effects.PintableButton;
import graphics.rooms.Room;

import java.awt.Graphics;

import kuusisto.tinysound.Sound;
import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import sound.MusicRepository;

public class Controls extends Room {

	private static final int PADDING_BORDER = 25;
	private static final int INTERBUTTON_BORDER = 25;

	public Button[][] menuButtons;
	public boolean selectingKey = false;
	private final int ROWS = 8;
	private final int COLUMNS = 2;
	private int selected;
	private int column;

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

		// UP button
		Sprite sprite = ControlsRepository.up;
		menuButtons[0][0] = new ControlButton(x, y + sprite.getHeight() / 2, this, sprite, KEY.UP,new Runnable() {

			@Override
			public void run() {
				// Set new text to pintable
				ControlButton cb = ((ControlButton) menuButtons[0][0]);
				cb.setPintableText("...");
				cb.selecting = true;
			}
		});
		addObjeto(menuButtons[0][0]);

		menuButtons[0][1] = new PintableButton(x2, y + sprite.getHeight() / 2 - sprite.getCenterY(), this, null, null,
				"Up");
		addObjeto(menuButtons[0][1]);
		((ControlButton) menuButtons[0][0]).setPintable((PintableButton) menuButtons[0][1]);

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// DOWN button
		sprite = ControlsRepository.down;
		menuButtons[1][0] = new Button(x, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				// TODO
			}
		});
		addObjeto(menuButtons[1][0]);

		menuButtons[1][1] = new PintableButton(x2, y + sprite.getHeight() / 2 - sprite.getCenterY(), this, null, null,
				"Down");
		addObjeto(menuButtons[1][1]);

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// LEFT button
		sprite = ControlsRepository.left;
		menuButtons[2][0] = new Button(x, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				// TODO
			}
		});
		addObjeto(menuButtons[2][0]);

		menuButtons[2][1] = new PintableButton(x2, y + sprite.getHeight() / 2 - sprite.getCenterY(), this, null, null,
				"Left");
		addObjeto(menuButtons[2][1]);

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// RIGHT button
		sprite = ControlsRepository.right;
		menuButtons[3][0] = new Button(x, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				// TODO
			}
		});
		addObjeto(menuButtons[3][0]);

		menuButtons[3][1] = new PintableButton(x2, y + sprite.getHeight() / 2 - sprite.getCenterY(), this, null, null,
				"Right");
		addObjeto(menuButtons[3][1]);

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// BOMB button
		sprite = ControlsRepository.bomb;
		menuButtons[4][0] = new Button(x, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				// TODO
			}
		});
		addObjeto(menuButtons[4][0]);

		menuButtons[4][1] = new PintableButton(x2, y + sprite.getHeight() / 2 - sprite.getCenterY(), this, null, null,
				"Space");
		addObjeto(menuButtons[4][1]);

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// PAUSE button
		sprite = ControlsRepository.pause;
		menuButtons[5][0] = new Button(x, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				// TODO
			}
		});
		addObjeto(menuButtons[5][0]);

		menuButtons[5][1] = new PintableButton(x2, y + sprite.getHeight() / 2 - sprite.getCenterY(), this, null, null,
				"Escape");
		addObjeto(menuButtons[5][1]);

		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// CONFIRM button
		sprite = ControlsRepository.confirm;
		menuButtons[6][0] = new Button(x, y + sprite.getHeight() / 2, this, sprite, new Runnable() {

			@Override
			public void run() {
				// TODO
			}
		});
		addObjeto(menuButtons[6][0]);

		menuButtons[6][1] = new PintableButton(x2, y + sprite.getHeight() / 2 - sprite.getCenterY(), this, null, null,
				"enter");
		addObjeto(menuButtons[6][1]);

		Sprite next = ControlsRepository.continueButton;
		y = this.height - PADDING_BORDER - next.getHeight() / 2;

		menuButtons[7][0] = new Button(width / 2, y, this, next, new Runnable() {

			@Override
			public void run() {
				StatesMachine.goToRoom(STATE.MAIN_MENU, false);
			}
		});

		menuButtons[7][1] = menuButtons[7][0];
		addObjeto(menuButtons[7][0]);
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
}
