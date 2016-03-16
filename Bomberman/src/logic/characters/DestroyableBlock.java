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

	public DestroyableBlock(int x, int y, Room r, STAGE stage) {
		super(x, y, r);
		
		sprite_index = GameRepository.destroyableBlock1;
		image_speed = 0.2;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		
		destroyed = GameRepository.destroyableBlock2;
		destruction = false;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if(destruction){
			if(sprite_index != destroyed){
				sprite_index = destroyed;
				image_index = 0;
				resetAnimationEnd();
			}
			else{
				if(animation_end){
					destroy();
				}
			}
		}
		
	}
	
	public void callForDestruction(){
		destruction = true;
	}
}
