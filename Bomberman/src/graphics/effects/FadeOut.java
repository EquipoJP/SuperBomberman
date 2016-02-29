package graphics.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.D2.rooms.Room;
import logic.Objeto;
import main.Game;

public class FadeOut extends Objeto {

	private BufferedImage curtain;
	private float alpha = 0.0f;
	private float suma = 0.02f;

	public FadeOut(int x, int y, Room r) {
		super(x, y, r);
	}

	public FadeOut(int x, int y, Room r, int depth) {
		super(x, y, r, depth);
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
		if (alpha < 1.0f) {
			alpha = alpha + suma;
		}
		if (alpha > 1.0f) {
			alpha = 1.0f;
		}
		
		// Destroy if alpha = 1
		if(alpha == 1.0f){
			destroy();
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D graphics = (Graphics2D) curtain.getGraphics();
		graphics.setPaint(new Color(0, 0, 0));
		graphics.fillRect(0, 0, curtain.getWidth(), curtain.getHeight());
		BufferedImage tmpImg = new BufferedImage(curtain.getWidth(), curtain.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) tmpImg.getGraphics();
		g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
		g2d.drawImage(curtain, 0, 0, null);
		curtain = tmpImg;
		g.drawImage(curtain, x, y, null);
	}

	@Override
	public void customDestroy() {
		
	}

}
