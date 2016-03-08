package logic.characters;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import logic.collisions.NoPerspectiveBoundingBox;
import main.Initialization;

public class Block extends Objeto{
	
	public Block(int x, int y, Room r, Initialization.COLOR c) {
		super(x, y, r);
		
		sprite_index = Initialization.getSpriteFromMap(c.toString() + "_BLOCK");
		image_speed = 0;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
	}

	@Override
	public void create() {
	}

	@Override
	public void customStep(KEY key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alarm(int alarmNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processKey(KEY key) {
		// TODO Auto-generated method stub
		
	}

}