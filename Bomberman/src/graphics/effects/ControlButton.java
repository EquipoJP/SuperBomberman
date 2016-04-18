package graphics.effects;

import java.awt.event.KeyEvent;

import graphics.rooms.Room;
import logic.Input;
import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;

public class ControlButton extends Button {

	private PintableButton myPintable;
	private KEY myKey;
	public String originalText;
	public boolean selecting = false;

	public ControlButton(int x, int y, Room r, Sprite sprite, KEY myKey, Runnable run) {
		super(x, y, r, sprite, run);
		myPintable = null;
		this.myKey = myKey;
	}

	public void setPintableText(String text) {
		if (myPintable != null)
			myPintable.key = text;
	}

	public void setPintable(PintableButton newPintable) {
		myPintable = newPintable;
		originalText = myPintable.key;
	}

	public PintableButton getPintable() {
		return myPintable;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		super.customStep(key, direction);
		if (selecting) {
			if (key != KEY.NO_KEY && Input.lastCode != -1) {
				selecting = false;
				int code = Input.lastCode;
				if (StatesMachine.input.mapper.isAlreadyIntoTheMapper(code)) {
					myPintable.key = originalText;
				} else {
					String newName = defaultTextes(code);
					if (newName == null) {
						newName = KeyEvent.getKeyText(code);
					}
					myPintable.key = newName;
					StatesMachine.input.mapper.changeKeyCode(myKey, code);
				}
			}
		}
	}

	public static String defaultTextes(int code) {
		String returned = null;
		// FIXME Hardcodeada lol
		switch (code) {
		case (38):
			returned = "Up";
			break;
		case (40):
			returned = "Down";
			break;
		case (39):
			returned = "Right";
			break;
		case (37):
			returned = "Left";
			break;
		case (32):
			returned = "Space";
			break;
		case (27):
			returned = "Escape";
			break;
		case (10):
			returned = "Enter";
		}
		return returned;
	}
}
