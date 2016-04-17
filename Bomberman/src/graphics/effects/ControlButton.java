package graphics.effects;

import java.awt.event.KeyEvent;

import graphics.rooms.Room;
import logic.Input;
import logic.Sprite;
import logic.StatesMachine;
import logic.Input.KEY;
import logic.KeyMapper;

public class ControlButton extends Button {

	private PintableButton myPintable;
	private KEY myKey;
	public boolean selecting = false;
	
	public ControlButton(int x, int y, Room r, Sprite sprite, KEY myKey, Runnable run) {
		super(x, y, r, sprite, run);
		myPintable = null;
		this.myKey = myKey;
	}

	public void setPintableText(String text){
		if(myPintable != null)
			myPintable.key = text;
	}
	
	public void setPintable(PintableButton newPintable){
		myPintable = newPintable;
	}
	
	public PintableButton getPintable(){
		return myPintable;
	}
	
	@Override
	public void customStep(KEY key, KEY direction){
		super.customStep(key,direction);
		if(selecting){
			if(key != KEY.NO_KEY){
				selecting = false;
				int code = Input.lastCode;
				String newName = KeyEvent.getKeyText(code);
				myPintable.key = newName;
				StatesMachine.input.mapper.changeKeyCode(myKey, code);
				StatesMachine.input.reloadMapping();
				System.out.println(code);
			}
		}
	}
	
	private String defaultTextes(int code){
		String returned = null;
		// FIXME Hardcodeada lol
		switch(code){
		case(38):
			returned = "Up";
		case(40):
			returned = "Down";
		case(39):
			returned = "Right";
		case(37):
			returned = "Left";
		}
		return returned;
	}
}
