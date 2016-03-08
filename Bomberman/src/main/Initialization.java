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

import logic.Sprite;
import utils.Animation;
import utils.IniUtils;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Initialization {

	/* attributes */
	public static final String SPRITE_FILE = "resources/iniFiles/sprites.ini";
	public static final String MAP_FILE = "resources/iniFiles/maps.ini";
	public static final String[] SPRITE_NAMES = { "IDLE", "WALKDOWN", "WALKSIDE_RIGHT", "WALKSIDE_LEFT", "WALKUP",
			"VICTORY" };
	public static final String[] SPRITE_TERMS = { "SPRITESHEET", "WIDTH_SPRITE", "HEIGHT_SPRITE", "NO_SPRITES" };

	public static enum SPRITES {
		WHITE_BOMBER, BLUE_DOLL, PINK_DOLL, EXPLOSION_CORE, 
		EXPLOSION_MID_VER, EXPLOSION_MID_HOR, EXPLOSION_EDGE_UP, 
		EXPLOSION_EDGE_DOWN, EXPLOSION_EDGE_LEFT, EXPLOSION_EDGE_RIGHT
	};

	public static enum COLOR {
		BLACK, RED, BLUE, PURPLE, GREEN
	};

	public static final int MAP_X_OFFSET = 10;
	public static final int MAP_Y_OFFSET = 100;

	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;

	/**
	 * Method to get the sprites from an ini file
	 * 
	 * @param name
	 *            name of the sprite
	 * @return a map with the different sprites
	 */
	public static Map<String, Sprite> getSprites(String name) {
		Map<String, Sprite> sprites = new HashMap<String, Sprite>();

		for (int i = 0; i < SPRITE_NAMES.length; i++) {
			String section = name + "_" + SPRITE_NAMES[i];

			String spriteSheet = IniUtils.getValue(SPRITE_FILE, section, SPRITE_TERMS[0]);
			String strWidth = IniUtils.getValue(SPRITE_FILE, section, SPRITE_TERMS[1]);
			String strHeight = IniUtils.getValue(SPRITE_FILE, section, SPRITE_TERMS[2]);
			String strNoSprites = IniUtils.getValue(SPRITE_FILE, section, SPRITE_TERMS[3]);

			int frames = Integer.parseInt(strNoSprites);
			int width = Integer.parseInt(strWidth);
			int height = Integer.parseInt(strHeight);

			BufferedImage sheet = null;
			try {
				sheet = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/" + spriteSheet));
			} catch (IOException e) {
				e.printStackTrace();
			}

			BufferedImage[] sprSheet = Animation.getSpritesFromImage(sheet, frames, width, height);
			sprites.put(SPRITE_NAMES[i], new Sprite(sprSheet, frames, width, height));
		}

		return sprites;
	}

	public static Sprite getSpriteFromMap(String name) {

		String spriteSheet = IniUtils.getValue(MAP_FILE, name, SPRITE_TERMS[0]);
		String strWidth = IniUtils.getValue(MAP_FILE, name, SPRITE_TERMS[1]);
		String strHeight = IniUtils.getValue(MAP_FILE, name, SPRITE_TERMS[2]);
		String strNoSprites = IniUtils.getValue(MAP_FILE, name, SPRITE_TERMS[3]);

		int frames = Integer.parseInt(strNoSprites);
		int width = Integer.parseInt(strWidth);
		int height = Integer.parseInt(strHeight);

		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/" + spriteSheet));
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage[] sprSheet = Animation.getSpritesFromImage(sheet, frames, width, height);
		return new Sprite(sprSheet, frames, width, height);
	}

	/**
	 * Method to get a sprite from an ini file
	 * 
	 * @param name
	 *            name of the sprite
	 * @return a sprite
	 */
	public static Sprite getSprite(String name) {

		String spriteSheet = IniUtils.getValue(SPRITE_FILE, name, SPRITE_TERMS[0]);
		String strWidth = IniUtils.getValue(SPRITE_FILE, name, SPRITE_TERMS[1]);
		String strHeight = IniUtils.getValue(SPRITE_FILE, name, SPRITE_TERMS[2]);
		String strNoSprites = IniUtils.getValue(SPRITE_FILE, name, SPRITE_TERMS[3]);

		int frames = Integer.parseInt(strNoSprites);
		int width = Integer.parseInt(strWidth);
		int height = Integer.parseInt(strHeight);

		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/" + spriteSheet));
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage[] sprSheet = Animation.getSpritesFromImage(sheet, frames, width, height);
		return new Sprite(sprSheet, frames, width, height);
	}
}
