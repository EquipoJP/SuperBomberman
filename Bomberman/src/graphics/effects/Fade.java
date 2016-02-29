package graphics.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.D2.rooms.Room;
import logic.Global;
import logic.Objeto;
import logic.Input.KEY;

public class Fade extends Objeto {

	private BufferedImage curtain;
	private float alpha = 0.0f;
	private boolean sum = true;
	
	private final float MOD = 0.02f;
	private final float MIN = 0.0f;
	private final float MAX = 1.0f;

	public Fade(int x, int y, Room r, boolean isFadeOut) {
		super(x, y, r);
		depth = Global.EFFECTS_DEPTH;
		sum = isFadeOut;
		if(sum){
			alpha = MIN;
		}
		else{
			alpha = MAX;
		}
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
		// Paints black curtain
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
	public void customStep(KEY key) {
		if(sum){
			if(alpha < MAX){
				alpha = alpha + MOD;
			}
			if(alpha > MAX){
				alpha = MAX;
			}
			// Destroy if alpha = MAX
			if(alpha == MAX){
				destroy();
			}
		}
		else{
			if(alpha > MIN){
				alpha = alpha - MOD;
			}
			if(alpha < MIN){
				alpha = MIN;
			}
			// Destroy if alpha = MIN
			if(alpha == MIN){
				destroy();
			}
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
	
	public boolean isFadeOut(){
		return sum;
	}
}
