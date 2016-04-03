/**
 * Class representing the main menu screen
 */
package graphics.D2.rooms.mainMenu;

import java.awt.Graphics;

import sound.MusicRepository;
import graphics.D2.rooms.Room;
import graphics.effects.Button;
import graphics.effects.Visual;
import kuusisto.tinysound.Sound;
import logic.Global;
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

	private Button[] menuButtons;
	private int selected;

	private static final int PADDING_BORDER = 25;
	private static final int INTERBUTTON_BORDER = 25;
	private static final int TITLEBUTTON_BORDER = 64;

	public MainMenu(int w, int h, String n) {
		super(w, h, n);

		System.out.println("MAIN MENU");
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
		select(0);
		
		setMusic(MusicRepository.menu, true);
	}

	private void createButtons() {
		menuButtons = new Button[selection.values().length];

		// variables
		int x = this.width / 2;
		int y = PADDING_BORDER + MainMenuRepository.titleButton.getHeight() + TITLEBUTTON_BORDER;

		// Game button
		Sprite sprite = MainMenuRepository.gameButton;
		menuButtons[0] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// Options button
		sprite = MainMenuRepository.optionsButton;
		menuButtons[1] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// Ranking button
		sprite = MainMenuRepository.rankingButton;
		menuButtons[2] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// Credits button
		sprite = MainMenuRepository.creditsButton;
		menuButtons[3] = new Button(x, y + sprite.getHeight() / 2, this, sprite);
		y = y + sprite.getHeight() + INTERBUTTON_BORDER;

		// Quit button
		sprite = MainMenuRepository.quitButton;
		menuButtons[4] = new Button(x, y + sprite.getHeight() / 2, this, sprite);

		for (Button b : menuButtons) {
			addObjeto(b);
		}
	}

	private void select(int no) {
		menuButtons[selected].unselect();
		selected = no;
		menuButtons[no].select();
	}

	private void next() {
		int no = (selected + 1) % menuButtons.length;
		select(no);
	}

	private void previous() {
		// -1 % 5 = -1. With this thing it gets 4
		int no = ((selected - 1) % menuButtons.length + menuButtons.length) % menuButtons.length;
		select(no);
	}

	private void confirm() {
		switch (selected) {
		case 0:
			StatesMachine.goToRoom(STATE.GAME, false);
			break;
		case 1:
			StatesMachine.goToRoom(STATE.OPTIONS_MENU, false);
			break;
		case 2:
			StatesMachine.goToRoom(STATE.RANKS, false);
			break;
		case 3:
			StatesMachine.goToRoom(STATE.CREDITS, false);
			break;
		case 4:
			Global.stopGame();
			System.exit(0);
			break;
		default:
			Global.stopGame();
			System.exit(-1);
			break;
		}
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
		case ENTER:
			confirm();
			break;
		default:
			break;
		}
	}
}
