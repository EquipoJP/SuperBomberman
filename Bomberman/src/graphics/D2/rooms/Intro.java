/**
 * Class representing the Intro screen
 */
package graphics.D2.rooms;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import graphics.effects.FadeIn;
import graphics.effects.FadeOut;
import logic.Objeto;
import main.Game;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Intro extends Room {

	public int x = 200;
	public int y = 200;

	public Intro(int w, int h, String n) {
		super(w, h, n);
		addObjeto(new FadeIn(0,0,this));
	}

	@Override
	public void render(Graphics g) {
		// TODO Draw Background funcion
		BufferedImage curtain = new BufferedImage(Game.WIDTH, Game.HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) curtain.getGraphics();
		graphics.setPaint(new Color(255, 255, 255));
		graphics.fillRect(0, 0, curtain.getWidth(), curtain.getHeight());
		g.drawImage(curtain, 0, 0, null);
		for(Objeto o : objetos){
			o.render(g);
		}
	}

	@Override
	public void action(int key) {
		x += 200;
	}

	@Override
	public void step() {
		for(Objeto o : objetos){
			o.step();
		}
	}

}
