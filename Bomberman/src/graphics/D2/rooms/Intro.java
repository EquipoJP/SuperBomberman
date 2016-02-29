/**
 * Class representing the Intro screen
 */
package graphics.D2.rooms;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import graphics.effects.FadeIn;
import logic.Objeto;
import main.Game;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Intro extends Room {

	private BufferedImage[] sprites;
	private int i;
	private int frames;
	private double iD;
	private double spriteSpeed;
	private FadeIn f;

	public int x = 200;
	public int y = 200;

	public Intro(int w, int h, String n, List<Objeto> objs) {
		super(w, h, n, objs);
		// System.out.println(getClass().getResource("../../../whiteBomber/"));
		// img =
		// ImageIO.read(getClass().getResource("../../../whiteBomber/walkdown.png"));
		// sprites = Animation.getSpritesFromImage(img, 8, 14, 24);
		sprites = Initialization.getSprites(Initialization.SPRITES[0]).get(
				Initialization.SPRITE_NAMES[1]);
		frames = 8;
		i = 0;

		iD = 0;
		spriteSpeed = 0.15;

		f = new FadeIn(0, 0);
	}

	@Override
	public void render(Graphics g) {
		// TODO Draw Background
		/*
		 * g.drawImage(sprites[i], x, y, null); iD = iD + spriteSpeed; i =
		 * (((int) Math.floor(iD))) % frames;
		 */
		f.step();
		BufferedImage curtain = new BufferedImage(Game.WIDTH, Game.HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) curtain.getGraphics();
		graphics.setPaint(new Color(255, 255, 255));
		graphics.fillRect(0, 0, curtain.getWidth(), curtain.getHeight());
		g.drawImage(curtain, 0, 0, null);
		f.render(g);
	}

	@Override
	public void action(int key) {
		x += 200;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

}
