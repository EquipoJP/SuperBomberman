/**
 * Class representing the Intro screen
 */
package graphics.D2.rooms;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.Animation;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Intro implements Room{
	
	private BufferedImage[] sprites;
	private int i;
	private int frames;
	
	public Intro(){
		BufferedImage img;
		try {
			img = ImageIO.read(Intro.class.getResourceAsStream("../../../../resources/sprites/whiteBomber/walkdown.png"));
			sprites = Animation.getSpritesFromImage(img, 8, 14, 24);
			frames = 8;
			i = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0, 0, 800, 800);
		g.drawImage(sprites[i], 200, 200, null);
		i = (i++) % frames;
	}

	@Override
	public void action(int key) {
		// TODO Auto-generated method stub
		
	}

}
