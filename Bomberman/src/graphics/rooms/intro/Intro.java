/**
 * Class representing the Intro screen
 */
package graphics.rooms.intro;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Date;

import graphics.effects.Fade;
import graphics.effects.IntroTemporizer;
import graphics.effects.Visual;
import graphics.rooms.Room;
import graphics.rooms.controlsMenu.ControlsRepository;
import graphics.rooms.credits.CreditsRepository;
import graphics.rooms.game.GameRepository;
import graphics.rooms.mainMenu.MainMenuRepository;
import graphics.rooms.optionsMenu.OptionsMenuRepository;
import graphics.rooms.pauseMenu.PauseMenuRepository;
import graphics.rooms.rankMenu.RankMenuRepository;
import logic.Input.KEY;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import sound.MusicRepository;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Intro extends Room {

	public int x = 200;
	public int y = 200;
	public boolean created;
	public boolean fadeOutCreated;

	/**
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @param n
	 *            name
	 */
	public Intro(int w, int h, String n) {
		super(w, h, n);
	}

	@Override
	public void load() {
		System.out.println(new Date().toString() + " Before music");
		MusicRepository.load();
		
		System.out.println(new Date().toString() + " Before intro");
		IntroRepository.load();
		
		System.out.println(new Date().toString() + " Before pause menu");
		PauseMenuRepository.load();
		
		System.out.println(new Date().toString() + " Before credits");
		CreditsRepository.load();
		
		System.out.println(new Date().toString() + " Before game");
		GameRepository.load(null);
		
		System.out.println(new Date().toString() + " Before options menu");
		OptionsMenuRepository.load();
		
		System.out.println(new Date().toString() + " Before main menu");
		MainMenuRepository.load();
		
		System.out.println(new Date().toString() + " Before rank menu");
		RankMenuRepository.load();
		
		System.out.println(new Date().toString() + " Before controls menu");
		ControlsRepository.load();
		
		System.out.println(new Date().toString() + " After verything");

		addObjeto(new Fade(0, 0, this, false));
		addObjeto(new Visual(width / 2, height / 2, this, IntroRepository.logo));
		created = false;
		fadeOutCreated = false;
	}

	@Override
	public void step(KEY key, KEY direction) {
		if (!loadComplete()) {
			return;
		}

		boolean fadeInFound = false;
		boolean fadeOutFound = false;
		for (int i = 0; i < objetos.size(); i++) {
			if (objetos.get(i) instanceof Fade) {
				Fade f = (Fade) objetos.get(i);
				if (!f.isFadeOut())
					fadeInFound = true;
				else {
					fadeOutCreated = true;
					fadeOutFound = true;
				}
			}
			objetos.get(i).step(key, direction);
		}

		if (fadeInFound && !created) {
			created = true;
			objetos.add(new IntroTemporizer(0, 0, this));
		}

		if (!fadeOutFound && fadeOutCreated) {
			StatesMachine.goToRoom(STATE.MAIN_MENU, false);
		}
	}

	@Override
	public void drawBackground(Graphics g) {
		if (!loadComplete()) {
			return;
		}

		BufferedImage curtain = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) curtain.getGraphics();
		graphics.setPaint(new Color(255, 255, 255));
		graphics.fillRect(0, 0, curtain.getWidth(), curtain.getHeight());
		graphics.dispose();
		g.drawImage(curtain, 0, 0, null);
	}
}
