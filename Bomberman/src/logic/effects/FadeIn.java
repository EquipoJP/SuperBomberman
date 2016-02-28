package logic.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import logic.Objeto;
import main.Game;

public class FadeIn extends Objeto {

	private int alarm0 = -1;
	private BufferedImage curtain;
	private float alpha = 1.0f;
	private float resta = 0.0005f;

	public FadeIn(int x, int y) {
		super(x, y);
	}

	public FadeIn(int x, int y, int depth) {
		super(x, y, depth);
	}

	@Override
	public void create() {
		// Create black curtain for further usage
		curtain = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = (Graphics2D) curtain.getGraphics();
		graphics.setPaint(new Color(0, 0, 0));
		graphics.fillRect(0, 0, curtain.getWidth(), curtain.getHeight());
	}

	@Override
	public void step() {
		// Alarms update
		if (alarm0 > 0) {
			alarm0--;
		}

		if (alpha > 0) {
			alpha = alpha - resta;
		}
		if (alpha < 0) {
			alpha = 0;
		}

		// Alarms deactivation
		if (alarm0 == 0) {
			alarm0 = -1;
		}
	}

	@Override
	public void render(Graphics g) {
		BufferedImage tmpImg = new BufferedImage(curtain.getWidth(), curtain.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) tmpImg.getGraphics();
		g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
		g2d.drawImage(curtain, 0, 0, null);
		curtain = tmpImg;
		g.drawImage(curtain, x, y, null);
	}

}
