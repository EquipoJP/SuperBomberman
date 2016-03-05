package logic.characters;

import java.util.List;

import logic.Input.KEY;
import logic.collisions.PerspectiveBoundingBox;
import main.Initialization;
import graphics.D2.rooms.Room;

public class PinkDoll extends Enemy {

	public PinkDoll(int x, int y, Room r) {
		super(x, y, r);
	}

	@Override
	public void create() {
		super.create();

		sprite_index = Initialization.getSprite(Initialization.SPRITES[2]);
		image_speed = 0.05;
		
		boundingBox = PerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		System.out.println("PINK DOLL " + boundingBox);

		route = createRoute();
	}

	@Override
	protected List<KEY> createRoute() {
		// TODO Auto-generated method stub
		return super.createRoute();
	}

}
