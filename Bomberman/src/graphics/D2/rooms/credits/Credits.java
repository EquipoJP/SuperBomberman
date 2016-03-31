package graphics.D2.rooms.credits;

import graphics.D2.rooms.Room;
import graphics.effects.Visual;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;

public class Credits extends Room {

	private static final int PADDING_BORDER = 25;

	public Credits(int w, int h, String n) {
		super(w, h, n);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void load() {
		CreditsRepository.load();

		background = CreditsRepository.background;

		int x = width / 2;

		Sprite back = CreditsRepository.backButton;
		back.setSubsprites(new BufferedImage[] { back.getSubsprites()[back
				.getSubimages() - 1] });
		back.setSubimages(1);
		int y = this.height - PADDING_BORDER - back.getHeight() / 2;
		addObjeto(new Visual(x, y, this, back));

		Sprite title = CreditsRepository.titleButton;
		y = PADDING_BORDER + title.getHeight() / 2;
		addObjeto(new Visual(x, y, this, title));

		y = height / 2;
		Sprite credits = CreditsRepository.credits;
		addObjeto(new Visual(x, y, this, credits));
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

	private void confirm() {
		StatesMachine.goToRoom(STATE.MAIN_MENU, false);
	}

	@Override
	public void step(KEY key, KEY direction) {
		super.step(key, direction);
		if (!loadComplete()) {
			return;
		}
		switch (key) {
		case ENTER:
			confirm();
			break;
		case ESCAPE:
			confirm();
			break;
		default:
			break;
		}
	}
}
