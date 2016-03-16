package graphics.effects;

import graphics.D2.rooms.Room;
import logic.Input.KEY;
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
	public void create() {
	}

	@Override
	public void customStep(KEY key) {
		if(selected){
			image_index = 1;
		}
		else{
			image_index = 0;
		}
	}

	@Override
	public void alarm(int alarmNo) {
	}

	@Override
	public void customDestroy() {
	}

	@Override
	public void processKey(KEY key) {
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

	@Override
	public void customCollision(Objeto colision) {
		// TODO Auto-generated method stub
		
	}

}
