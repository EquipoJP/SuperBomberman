/**
 * Class for animating characters and so
 */
package utils;

import java.awt.image.BufferedImage;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Animation {
	
	/**
	 * Method to get the sprites from a spritesheet (only one row)
	 * @param sheet sheet from which the method gets the sprites
	 * @param frames number of sprites on the sheet
	 * @param width width of each sprite
	 * @param height height of each sprite
	 * @return an array of sprites
	 */
	public static BufferedImage[] getSpritesFromImage(BufferedImage sheet, int frames, 
			int width, int height){
		BufferedImage[] sprites = new BufferedImage[frames];
		
		for(int i = 0; i < frames; i++){
			sprites[i] = sheet.getSubimage(i*width, 0, width, height);
		}
		
		return sprites;
	}

}