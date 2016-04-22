/**
 * Class representing a control button (button to change buttons' effects)
 */
package graphics.effects;

import java.awt.event.KeyEvent;

import graphics.rooms.Room;
import logic.Input;
import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class ControlButton extends Button {

	private PintableButton myPintable;
	private KEY myKey;
	public String originalText;
	public boolean selecting = false;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param r
	 *            room
	 * @param sprite
	 *            sprite
	 * @param myKey
	 *            key associated to
	 * @param run
	 *            function to run when selected
	 */
	public ControlButton(int x, int y, Room r, Sprite sprite, KEY myKey, Runnable run) {
		super(x, y, r, sprite, run);
		myPintable = null;
		this.myKey = myKey;
	}

	/**
	 * @param text
	 *            text to paint
	 */
	public void setPintableText(String text) {
		if (myPintable != null)
			myPintable.key = text;
	}

	/**
	 * @param newPintable
	 *            pintable button associated to
	 */
	public void setPintable(PintableButton newPintable) {
		myPintable = newPintable;
		originalText = myPintable.key;
	}

	/**
	 * @return pintable button associated to this button
	 */
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

	/**
	 * @param code cod eto parse
	 * @return text associated to code
	 */
	public static String defaultTextes(int code) {
		String returned = null;
		switch (code) {
		case (KeyEvent.VK_UP):
			returned = "Up";
			break;
		case (KeyEvent.VK_DOWN):
			returned = "Down";
			break;
		case (KeyEvent.VK_RIGHT):
			returned = "Right";
			break;
		case (KeyEvent.VK_LEFT):
			returned = "Left";
			break;
		case (KeyEvent.VK_SPACE):
			returned = "Space";
			break;
		case (KeyEvent.VK_ESCAPE):
			returned = "Escape";
			break;
		case (KeyEvent.VK_ENTER):
			returned = "Enter";
		}
		return returned;
	}
}
