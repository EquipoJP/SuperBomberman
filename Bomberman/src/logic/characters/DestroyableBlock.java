package logic.characters;

import logic.Input.KEY;
import graphics.rooms.Room;
import graphics.rooms.game.GameRepository;
import logic.Objeto;
import logic.Sprite;
import logic.characters.Item.TYPE;
import logic.collisions.NoPerspectiveBoundingBox;
import main.Initialization.STAGE;

public class DestroyableBlock extends Objeto{
	
	private Sprite destroyed;
	private boolean destruction;
	private TYPE type;

	public DestroyableBlock(int x, int y, Room r, STAGE stage, TYPE t) {
		super(x, y, r);
		
		sprite_index = GameRepository.destroyableBlock1;
		image_speed = 0.2;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		
		destroyed = GameRepository.destroyableBlock2;
		destruction = false;
		
		type = t;
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
	
	@Override
	public void customDestroy(){
		if(type != null){
			Item i = new Item(x, y, myRoom, type);
			myRoom.addObjeto(i);
		}
	}
	
	public void callForDestruction(){
		destruction = true;
	}
}
