/**
 * Class representing the Intro screen
 */
package graphics.D2.rooms;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.Animation;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Intro implements Room {

	private BufferedImage[] sprites;
	private int i;
	private int frames;
	private double iD;
	private double spriteSpeed;

	public int x = 200;
	public int y = 200;

	public Intro() {
		BufferedImage img;
		try {
//			System.out.println(getClass().getResource("../../../whiteBomber/"));
			img = ImageIO.read(getClass().getResource("../../../whiteBomber/walkdown.png"));
			sprites = Animation.getSpritesFromImage(img, 8, 14, 24);
			frames = 8;
			i = 0;
			iD = 0;
			spriteSpeed = 0.15;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(sprites[i], x, y, null);
		iD = iD + spriteSpeed;
		i = (((int) Math.floor(iD))) % frames;
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
