package graphics.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Global;
import logic.Objeto;
import main.Game;

public class FadeIn extends Objeto {

	private BufferedImage curtain;
	private float alpha = 1.0f;
	private float resta = 0.02f;

	public FadeIn(int x, int y, Room r) {
		super(x, y, r);
		depth = Global.EFFECTS_DEPTH;
	}

	@Override
	public void create() {
		// Create black curtain for further usage
		curtain = new BufferedImage(myRoom.width, myRoom.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = (Graphics2D) curtain.getGraphics();
		graphics.setPaint(new Color(0, 0, 0));
		graphics.fillRect(0, 0, curtain.getWidth(), curtain.getHeight());
	}

	@Override
	public void render(Graphics g) {
		// Dibuja la cortina negra
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
		// Nothing
	}

	@Override
	public void customStep() {
		if (alpha > 0) {
			alpha = alpha - resta;
		}
		if (alpha < 0) {
			alpha = 0;
		}
		
		// Destroy if alpha = 0
		if(alpha==0){
			destroy();
		}
	}

	@Override
	public void alarm(int alarmNo) {
		// Nothing
	}

	@Override
	public void processKey(KEY key) {
		// Nothing		
	}

}
