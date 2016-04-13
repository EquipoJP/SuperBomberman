/**
 * Class for initialization. Has the sprite names and most of the resources properties.
 */
package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
	public static final String SPRITE_FILE = "iniFiles/sprites.ini";
	public static final String MAP_FILE = "iniFiles/maps.ini";
	public static final String MENU_FILE = "iniFiles/menus.ini";

	public static final String[] BOMBERMAN_SPRS = { "IDLE", "WALKDOWN",
			"WALKSIDE_RIGHT", "WALKSIDE_LEFT", "WALKUP", "VICTORY", "DEATH" };
	public static final String[] ENEMIES_SPRS = { "DEATH", "RIGHT", "LEFT" };
	public static final String[] SPRITE_TERMS = { "SPRITESHEET",
			"WIDTH_SPRITE", "HEIGHT_SPRITE", "NO_SPRITES", "CENTER_X",
			"CENTER_Y" };

	public static enum MENUS {
		TITLE_BUTTON, GAME_BUTTON, OPTIONS_BUTTON, RANKING_BUTTON, CREDITS_BUTTON, QUIT_BUTTON, CONTROLS_BUTTON, ALPHABET, DIGITS, CONTINUE_BUTTON, NEW, BACK_BUTTON, LOADING, HUD, VICTORY, BACKGROUND, CREDITS, SLIDER
	};
	
	public static enum CTRLS{
		CTRL_UP, CTRL_DOWN, CTRL_LEFT, CTRL_RIGHT, CTRL_BOMB, CTRL_PAUSE, CTRL_CONFIRM
	};

	public static enum SPRITES {
		WHITE_BOMBER, ENEMY, EXPLOSION_CORE, EXPLOSION_MID_VER, EXPLOSION_MID_HOR, EXPLOSION_EDGE_UP, EXPLOSION_EDGE_DOWN, EXPLOSION_EDGE_LEFT, EXPLOSION_EDGE_RIGHT, BOMB, ITEM_BOMB, ITEM_POWER, ITEM_SPEED, SPEED_PICKUP, POWER_PICKUP, BOMB_PICKUP, ITEM_DESTROY, STAIRS
	};

	public static enum STAGE {
		PEACETOWN, GREENVILLAGE
	};

	public static enum TYPE {
		BLOCK, TILE, DESTROYABLE_BLOCK, DESTROY_BLOCK
	}

	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;

	public static final int ALPHANUM_WIDTH = 32;
	public static final int ALPHANUM_HEIGHT = 32;

	public static final int MAP_X_OFFSET = 26;
	public static final int MAP_Y_OFFSET = 124;

	public static final int MAP_WIDTH = 15;
	public static final int MAP_HEIGHT = 15;

	// Custom Depths
	private static boolean initialized = false;
	private static final Map<String, Integer> DEPTHS = new HashMap<String, Integer>();

	/**
	 * Method to get the sprites from the sprite_file ini file
	 * 
	 * @param name
	 *            name of the sprite
	 *            @param sprs sprite terms to look for
	 * @return a map with the different sprites
	 */
	public static Map<String, Sprite> getSpritesFromTableSprites(String name,
			String[] sprs) {
		Map<String, Sprite> sprites = new HashMap<String, Sprite>();

		for (int i = 0; i < sprs.length; i++) {
			String section = name + "_" + sprs[i];
			Sprite spr = getSpriteFromFile(section, SPRITE_FILE);
			sprites.put(sprs[i], spr);
		}

		return sprites;
	}

	/**
	 * Method to get the sprite named name from the map_file ini file
	 * 
	 * @param name
	 *            name of the sprite
	 * @return sprite
	 */
	public static Sprite getSpriteFromMap(String name) {
		return getSpriteFromFile(name, MAP_FILE);
	}

	/**
	 * Method to get a sprite from the sprite_file ini file
	 * 
	 * @param name
	 *            name of the sprite
	 * @return a sprite
	 */
	public static Sprite getSpriteFromSprites(String name) {
		return getSpriteFromFile(name, SPRITE_FILE);
	}

	/**
	 * Method to get a sprite from the menu_file ini file
	 * 
	 * @param name
	 *            name of the sprite
	 * @return a sprite
	 */
	public static Sprite getSpriteFromMenu(String name) {
		return getSpriteFromFile(name, MENU_FILE);
	}

	/**
	 * Method to get a sprite from an ini file
	 * 
	 * @param name
	 *            name of the sprite
	 * @param file
	 *            name of the file
	 * @return sprite
	 */
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
			InputStream is = Initialization.class.getClassLoader()
					.getResourceAsStream(spriteSheet);
			sheet = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage[] sprSheet = Animation.getSpritesFromImage(sheet, frames,
				width, height);
		return new Sprite(sprSheet, frames, width, height);
	}

	/**
	 * Method to get the predefined depth of an object
	 * 
	 * @param objectName
	 *            name of the object
	 * @return depth
	 */
	public static int getDepth(String objectName) {
		if (!initialized) {
			initializeDepths();
		}

		return DEPTHS.get(objectName);
	}

	/**
	 * Initializes to depth of some objects
	 */
	private static void initializeDepths() {
		DEPTHS.put("Bomb", 15);
		DEPTHS.put("Item", -15);
	}

	/**
	 * Method to get the center-x from a sprite
	 * 
	 * @param name
	 *            name of the sprite
	 * @return the center-x of the sprite
	 */
	public static int getCenterXFromSpriteName(String name) {
		return Integer.parseInt(IniUtils.getValue(SPRITE_FILE, name,
				SPRITE_TERMS[4]));
	}

	/**
	 * Method to get the center-y from a sprite
	 * 
	 * @param name
	 *            name of the sprite
	 * @return the center-y of the sprite
	 */
	public static int getCenterYFromSpriteName(String name) {
		return Integer.parseInt(IniUtils.getValue(SPRITE_FILE, name,
				SPRITE_TERMS[5]));
	}
}
