package graphics.effects;

import logic.Input.KEY;
import graphics.rooms.Room;
import logic.Objeto;
import logic.Sprite;

public class Button extends Objeto {
	
	private boolean selected;
	
	public Button(int x, int y, Room r, Sprite sprite) {
		super(x, y, r);
		
		sprite_index = sprite;
		image_speed = 0;
		image_index = 0;
		
		selected = false;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if(selected){
			image_index = 1;
		}
		else{
			image_index = 0;
		}
	}
	
	public void select(){
		this.selected = true;
	}
	
	public void unselect(){
		this.selected = false;
	}
	
	public boolean isSelected(){
		return this.selected;
	}
}
