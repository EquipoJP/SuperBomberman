package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;
import logic.Input.KEY;
import logic.Objeto;
import logic.collisions.NoPerspectiveBoundingBox;

public class Block extends Objeto{
	
	public Block(int x, int y, Room r) {
		super(x, y, r);
		
		sprite_index = GameRepository.block;
		image_speed = 0.5;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
	}

	@Override
	public void create() {
	}

	@Override
	public void customStep(KEY key, KEY direction) {
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
	public void processKey(KEY key, KEY direction) {
		// TODO Auto-generated method stub
		
	}

}
