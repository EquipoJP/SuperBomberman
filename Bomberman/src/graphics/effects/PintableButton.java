package graphics.effects;

import graphics.rooms.Room;

import java.awt.Graphics;

import logic.Sprite;
import logic.collisions.Point2D;
import utils.PaintService;

public class PintableButton extends Button{
	
	public String key;

	public PintableButton(int x, int y, Room r, Sprite sprite, Runnable run, String key) {
		super(x, y, r, sprite, null);
		this.key = key;
	}
	
	
	@Override
	public void render(Graphics g) {
		PaintService.paintTextColor(key, new Point2D(x, y), g, PaintService.DIGITS_ORANGE.getRGB());
	}

}
