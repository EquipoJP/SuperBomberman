package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;
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
}
