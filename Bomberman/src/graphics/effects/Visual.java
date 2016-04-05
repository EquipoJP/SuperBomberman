package graphics.effects;

import graphics.rooms.Room;
import logic.Objeto;
import logic.Sprite;

public class Visual extends Objeto {

	public Visual(int x, int y, Room r, Sprite spr) {
		super(x, y, 0, r);
		
		sprite_index = spr;
		
		if(sprite_index.getSubimages() == 0){
			image_speed = 0;
		}
		else{
			image_speed = 0.1; //TODO 1 second each frame
		}
	}
}
