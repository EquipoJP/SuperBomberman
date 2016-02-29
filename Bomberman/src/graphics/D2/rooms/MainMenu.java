/**
 * Class representing the main menu screen
 */
package graphics.D2.rooms;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import logic.Input.KEY;
import logic.Objeto;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class MainMenu extends Room{

	public MainMenu(int w, int h, String n, List<Objeto> objs) {
		super(w, h, n);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawBackground(Graphics g) {
		// TODO Auto-generated method stub
		BufferedImage curtain = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) curtain.getGraphics();
		graphics.setPaint(new Color(255, 255, 255));
		graphics.fillRect(0, 0, curtain.getWidth(), curtain.getHeight());
		graphics.dispose();
		g.drawImage(curtain, 0, 0, null);
	}

	@Override
	public void step(KEY key) {
		// TODO Auto-generated method stub
		
	}

}
