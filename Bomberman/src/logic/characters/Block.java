package logic.characters;

import graphics.rooms.Room;
import graphics.rooms.game.GameRepository;
import logic.Objeto;
import logic.collisions.NoPerspectiveBoundingBox;

public class Block extends Objeto{
	
	public Block(int x, int y, int z, Room r) {
		super(x, y, z, r);
		
		sprite_index = GameRepository.block;
		image_speed = 0.5;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
	}
}
