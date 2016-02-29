package logic.characters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;

import graphics.D2.rooms.Room;
import logic.Objeto;
import main.Initialization;

public class Player extends Objeto{
	
	/* Info to get Sprites */
	private Map<String, BufferedImage[]> sprites;

	public Player(int x, int y, Room r, int depth) {
		super(x, y, r, depth);
		sprites = Initialization.getSprites(Initialization.SPRITES[0]);
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customDestroy() {
		
	}


}
