package logic.characters;

import java.awt.image.BufferedImage;
import java.util.Map;

import main.Initialization;

public class Player {
	
	/* Info to get Sprites */
	private Map<String, BufferedImage[]> sprites;

	public Player() {
		sprites = Initialization.getSprites(Initialization.SPRITES[0]);
	}

}
