package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.collisions.NoPerspectiveBoundingBox;
import main.Initialization.STAGE;

public class DestroyableBlock extends Objeto{
	
	private Sprite destroyed;
	private boolean destruction;
	private boolean animation_end;

	public DestroyableBlock(int x, int y, Room r, STAGE stage) {
		super(x, y, r);
		
		sprite_index = GameRepository.destroyableBlock1;
		image_speed = 0.5;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		
		destroyed = GameRepository.destroyableBlock2;
		destruction = false;
		animation_end = false;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if(destruction){
			if(sprite_index != destroyed){
				sprite_index = destroyed;
				image_index = 0;
			}
			else{
				if(image_index >= sprite_index.getSubimages()-1){
					animation_end = true;
				}
				if(animation_end && image_index == 0){
					destroy();
				}
			}
		}
		
	}
	
	public void callForDestruction(){
		destruction = true;
	}
}
