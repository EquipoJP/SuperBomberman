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
	private boolean destruction;
	private boolean animation_end;

	public DestroyableBlock(int x, int y, Room r, STAGE stage) {
		super(x, y, r);
		
		sprite_index = Initialization.getSpriteFromMap(stage.toString() + "_" + TYPE.DESTROYABLE_BLOCK.toString());
		image_speed = 0.5;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		
		destroyed = Initialization.getSpriteFromMap(stage.toString() + "_" + TYPE.DESTROY_BLOCK.toString());
		destruction = false;
		animation_end = false;
	}

	@Override
	public void create() {
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		// TODO chequear colision con explosiones y poner destruction = true si toca
		
		if(destruction){
			if(sprite_index != destroyed){
				sprite_index = destroyed;
				image_index = 0;
			}
			else{
				if(image_index == sprite_index.getSubimages()-1){
					animation_end = true;
				}
				if(animation_end && image_index == 0){
					destroy();
				}
			}
		}
		
	}

	@Override
	public void alarm(int alarmNo) {
	}

	@Override
	public void customDestroy() {
	}

	@Override
	public void processKey(KEY key, KEY direction) {
	}

}
