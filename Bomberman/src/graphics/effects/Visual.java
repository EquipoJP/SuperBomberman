package graphics.effects;

import graphics.D2.rooms.Room;
import logic.Objeto;
import logic.Sprite;

public class Visual extends Objeto {

	public Visual(int x, int y, Room r, Sprite spr) {
		super(x, y, r);
		
		sprite_index = spr;
		
		if(sprite_index.getSubimages() == 0){
			image_speed = 0;
		}
		else{
			image_speed = 0.15; //TODO 1 second each frame
		}
	}
}
