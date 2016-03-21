package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;
import logic.collisions.PerspectiveBoundingBox;

public class PinkDoll extends Enemy {

	public PinkDoll(int x, int y, Room r) {
		super(x, y, r);
	}

	@Override
	public void create() {
		super.create();

		sprite_index = GameRepository.pinkDoll;
		image_speed = 0.05;
		
		boundingBox = PerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		System.out.println("PINK DOLL " + boundingBox);
	}

}
