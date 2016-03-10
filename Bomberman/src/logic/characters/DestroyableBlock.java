package logic.characters;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.collisions.NoPerspectiveBoundingBox;
import main.Initialization;
import main.Initialization.STAGE;
import main.Initialization.TYPE;

public class DestroyableBlock extends Objeto{
	
	private Sprite destroyed;

	public DestroyableBlock(int x, int y, Room r, STAGE stage) {
		super(x, y, r);
		
		sprite_index = Initialization.getSpriteFromMap(stage.toString() + "_" + TYPE.DESTROYABLE_BLOCK.toString());
		image_speed = 0.5;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		
		destroyed = Initialization.getSpriteFromMap(stage.toString() + "_" + TYPE.DESTROY_BLOCK.toString());
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
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
