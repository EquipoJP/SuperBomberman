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

	/**
	 * Method to get the sprites from a spritesheet (only one row)
	 * adding transparency to pixels with 'transparency' color
	 * @param sheet sheet from which the method gets the sprites
	 * @param frames number of sprites on the sheet
	 * @param width width of each sprite
	 * @param height height of each sprite
	 * @param transparency color of the pixel to apply transparency
	 * @return an array of sprites
	 */
	public static BufferedImage[] getSpritesFromImage(BufferedImage sheet, int frames, 
			int width, int height, int transparency){
		BufferedImage[] sprites = new BufferedImage[frames];
		for(int i = 0; i < frames; i++){
			sprites[i] = sheet.getSubimage(i*width, 0, width, height);
			for(int j = 0;j<width;j++){
				for(int k = 0;k<height;k++){
					if(sprites[i].getRGB(j, k) == transparency){
						sprites[i].setRGB(j, k, 0x00);
					}
				}
			}
		}
		return sprites;
	}	
}