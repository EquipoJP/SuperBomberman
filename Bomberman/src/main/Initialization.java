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
	public static final String MENU_FILE = "resources/iniFiles/menus.ini";

	public static final String[] BOMBERMAN_SPRS = { "IDLE", "WALKDOWN",
			"WALKSIDE_RIGHT", "WALKSIDE_LEFT", "WALKUP", "VICTORY" };
	public static final String[] ENEMIES_SPRS = { "DEATH", "RIGHT",
		"LEFT" };
	public static final String[] SPRITE_TERMS = { "SPRITESHEET",
			"WIDTH_SPRITE", "HEIGHT_SPRITE", "NO_SPRITES" };
	
	public static enum BUTTONS{
		TITLE_BUTTON, GAME_BUTTON, OPTIONS_BUTTON, RANKING_BUTTON, CREDITS_BUTTON, QUIT_BUTTON, DIGITS, CONTINUE_BUTTON, NEW, BACK_BUTTON, LOADING, HUD, VICTORY
	};

	public static enum SPRITES {
		WHITE_BOMBER, ENEMY, EXPLOSION_CORE, EXPLOSION_MID_VER, EXPLOSION_MID_HOR, EXPLOSION_EDGE_UP, EXPLOSION_EDGE_DOWN, EXPLOSION_EDGE_LEFT, EXPLOSION_EDGE_RIGHT, BOMB
	};

	public static enum STAGE {
		PEACETOWN, GREENVILLAGE
	};
	
	public static enum TYPE{
		BLOCK, TILE, DESTROYABLE_BLOCK, DESTROY_BLOCK
	}

	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;

	public static final int MAP_X_OFFSET = 26;
	public static final int MAP_Y_OFFSET = 124;
	
	/**
	 * Method to get the sprites from an ini file
	 * 
	 * @param name
	 *            name of the sprite
	 * @return a map with the different sprites
	 */
	public static Map<String, Sprite> getSpritesFromTableSprites(String name, String[] sprs) {
		Map<String, Sprite> sprites = new HashMap<String, Sprite>();

		for (int i = 0; i < sprs.length; i++) {
			String section = name + "_" + sprs[i];
			Sprite spr = getSpriteFromFile(section, SPRITE_FILE);
			sprites.put(sprs[i], spr);
		}

		return sprites;
	}

	public static Sprite getSpriteFromMap(String name) {
		return getSpriteFromFile(name, MAP_FILE);
	}

	/**
	 * Method to get a sprite from an ini file
	 * 
	 * @param name
	 *            name of the sprite
	 * @return a sprite
	 */
	public static Sprite getSpriteFromSprites(String name) {
		return getSpriteFromFile(name, SPRITE_FILE);
	}
	
	public static Sprite getSpriteFromMenu(String name) {
		return getSpriteFromFile(name, MENU_FILE);
	}

	public static Sprite getSpriteFromFile(String name, String file) {
		String spriteSheet = IniUtils.getValue(file, name, SPRITE_TERMS[0]);
		String strWidth = IniUtils.getValue(file, name, SPRITE_TERMS[1]);
		String strHeight = IniUtils.getValue(file, name, SPRITE_TERMS[2]);
		String strNoSprites = IniUtils.getValue(file, name, SPRITE_TERMS[3]);

		int frames = Integer.parseInt(strNoSprites);
		int width = Integer.parseInt(strWidth);
		int height = Integer.parseInt(strHeight);

		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/resources/" + spriteSheet));
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage[] sprSheet = Animation.getSpritesFromImage(sheet, frames,
				width, height);
		return new Sprite(sprSheet, frames, width, height);
	}
}
