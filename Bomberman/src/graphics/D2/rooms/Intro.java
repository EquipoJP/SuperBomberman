/**
 * Class representing the Intro screen
 */
package graphics.D2.rooms;

import graphics.effects.Fade;
import graphics.effects.IntroTemporizer;
import graphics.effects.Logo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import logic.Input.KEY;
import logic.StatesMachine;
import logic.StatesMachine.STATE;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Intro extends Room {

	public int x = 200;
	public int y = 200;
	public boolean created;
	public boolean fadeOutCreated;

	public Intro(int w, int h, String n) {
		super(w, h, n);
		addObjeto(new Fade(0,0,this,false));
		addObjeto(new Logo(w/2,h/2,this));
		created = false;
		fadeOutCreated = false;
	}

	@Override
	public void step(KEY key) {
		boolean fadeInFound = false;
		boolean fadeOutFound = false;
		for(int i = 0; i < objetos.size(); i++){
			if(objetos.get(i) instanceof Fade){
				Fade f = (Fade) objetos.get(i);
				if(!f.isFadeOut())
					fadeInFound = true;
				else {
					fadeOutCreated = true;
					fadeOutFound = true;
				}
			}
			objetos.get(i).step(key);
		}
		
		if(fadeInFound && !created){
			created = true;
			objetos.add(new IntroTemporizer(0,0,this));
		}
		
		if(!fadeOutFound && fadeOutCreated){
			StatesMachine.goToRoom(STATE.MAIN_MENU, false);
		}
	}

	@Override
	public void drawBackground(Graphics g) {
		BufferedImage curtain = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) curtain.getGraphics();
		graphics.setPaint(new Color(255, 255, 255));
		graphics.fillRect(0, 0, curtain.getWidth(), curtain.getHeight());
		graphics.dispose();
		g.drawImage(curtain, 0, 0, null);
	}

}
