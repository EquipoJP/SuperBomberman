package logic.characters;

import graphics.rooms.Room;
import graphics.rooms.game.GameRepository;
import logic.Objeto;

public class Stairs extends Objeto {

	public Stairs(int x, int y, int z, Room r) {
		super(x, y, z, r);
		
		sprite_index = GameRepository.stairs;
		image_speed = 0;
		image_index = 0;
	}

}
