package logic.characters;

import logic.collisions.PerspectiveBoundingBox;
import main.Initialization;
import graphics.D2.rooms.Room;

public class BlueDoll extends Enemy {

	public BlueDoll(int x, int y, Room r) {
		super(x, y, r);
	}

	@Override
	public void create() {
		super.create();

		sprite_index = Initialization.getSpriteFromSprites(Initialization.SPRITES.BLUE_DOLL.toString());
		image_speed = 0.05;

		boundingBox = PerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		System.out.println("BLUE DOLL: " + boundingBox);
		
		route = createRoute();
	}

}
