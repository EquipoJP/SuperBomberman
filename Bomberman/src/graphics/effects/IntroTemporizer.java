package graphics.effects;

import java.awt.Graphics;

import graphics.D2.rooms.Room;
import logic.Objeto;

public class IntroTemporizer extends Objeto{

	private int alarm0 = -1;
	
	public IntroTemporizer(int x, int y, Room r) {
		super(x, y, r);
	}

	@Override
	public void create() {
		alarm0 = 120;
	}

	@Override
	public void step() {
		if(alarm0 > 0){
			alarm0--;
		}
		
		if(alarm0 == 0){
			destroy();
		}
	}

	@Override
	public void render(Graphics g) {
		// Nothing
	}

	@Override
	public void customDestroy() {
		myRoom.addObjeto(new FadeOut(0,0,myRoom));
	}

}
