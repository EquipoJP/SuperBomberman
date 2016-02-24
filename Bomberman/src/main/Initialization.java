/**
 * Class for initialization and stuff
 */
package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import utils.Animation;
import utils.IniUtils;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Initialization {

	/* attributes */
	public static final String SPRITE_FILE = "../iniFiles/sprites.ini";
	public static final String[] SPRITE_NAMES = { "IDLE", "WALKDOWN",
			"WALKSIDE", "WALKUP", "VICTORY" };
	public static final String[] SPRITE_TERMS = { "SPRITESHEET",
			"WIDTH_SPRITE", "HEIGHT_SPRITE", "NO_SPRITES" };
	public static final String[] SPRITES = { "WHITE_BOMBER" };

	/**
	 * Method to get the sprites from an ini file
	 * 
	 * @param name
	 *            name of the sprite
	 * @return a map with the different sprites
	 */
	public static Map<String, BufferedImage[]> getSprites(String name) {
		Map<String, BufferedImage[]> sprites = new HashMap<String, BufferedImage[]>();

		for (int i = 0; i < SPRITE_NAMES.length; i++) {
			String section = name + "_" + SPRITE_NAMES[i];

			String spriteSheet = IniUtils.getValue(SPRITE_FILE, section,
					SPRITE_TERMS[0]);
			String strWidth = IniUtils.getValue(SPRITE_FILE, section,
					SPRITE_TERMS[1]);
			String strHeight = IniUtils.getValue(SPRITE_FILE, section,
					SPRITE_TERMS[2]);
			String strNoSprites = IniUtils.getValue(SPRITE_FILE, section,
					SPRITE_TERMS[3]);

			int frames = Integer.parseInt(strNoSprites);
			int width = Integer.parseInt(strWidth);
			int height = Integer.parseInt(strHeight);

			BufferedImage sheet = null;
			try {
				sheet = ImageIO.read(new File("../" + spriteSheet));
			} catch (IOException e) {
				e.printStackTrace();
			}

			BufferedImage[] sprSheet = Animation.getSpritesFromImage(sheet,
					frames, width, height);
			sprites.put(SPRITE_NAMES[i], sprSheet);
		}

		return sprites;
	}
}
