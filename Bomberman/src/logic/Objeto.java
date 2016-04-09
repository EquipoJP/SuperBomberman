/**
 * Class representing an abstract object
 */
package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import graphics.rooms.Room;
import logic.Input.KEY;
import logic.collisions.BoundingBox;

public abstract class Objeto {

	public int x;
	public int y;
	public int z;
	public int depth;

	public Sprite sprite_index;
	private Sprite prev_sprite_index;
	public BoundingBox boundingBox;
	protected double image_index;
	protected double image_speed;
	protected boolean animation_end;

	public Room myRoom;

	public int alarm[];
	public int alarmsSet;

	private List<Integer> alarmsOff;
	private double previous_image_index;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param r
	 *            room
	 */
	public Objeto(int x, int y, int z, Room r) {
		this.x = x;
		this.y = y;
		this.z = z;

		depth = 0;
		alarm = new int[20];
		for (int i = 0; i < alarm.length; i++) {
			alarm[i] = -1;
		}
		alarmsSet = 0;
		myRoom = r;
		alarmsOff = new LinkedList<Integer>();
		sprite_index = null;
		boundingBox = null;
		prev_sprite_index = sprite_index;
		image_index = -1;
		previous_image_index = image_index;
		animation_end = false;
		image_speed = 0;
		create();
	}

	/**
	 * Create event
	 */
	public void create() {
		// You must override this to use it
	}

	/**
	 * Custom step event
	 * 
	 * @param key
	 *            no-rebound key
	 * @param direction
	 *            direction key
	 */
	public void customStep(KEY key, KEY direction) {
		// You must override this to use it
	}

	/**
	 * @param alarmNo
	 *            alarm triggered
	 */
	public void alarm(int alarmNo) {
		// You must override this to use it
	}

	/**
	 * @param colision
	 *            other object
	 */
	public void customCollision(Objeto colision) {
		// You must override this to use it
	}

	/**
	 * Custom destroy event
	 */
	public void customDestroy() {
		// You must override this to use it
	}

	/**
	 * @param key
	 *            no-rebound key
	 * @param direction
	 *            direction key
	 */
	public void processKey(KEY key, KEY direction) {
		// You must override this to use it
	}

	/**
	 * Render the animated sprite of the object
	 * 
	 * @param g graphics
	 */
	public void render(Graphics g) {
		if (sprite_index != null) {
			if (image_speed != 0
					&& (prev_sprite_index == null || (prev_sprite_index != null && !sprite_index
							.equals(prev_sprite_index)))) {
				prev_sprite_index = sprite_index;
				image_index = 0;
				previous_image_index = 0;
			}
			if (image_index < 0) {
				image_index = 0;
				previous_image_index = 0;
			}
			int temp_image_index = (int) Math.floor(image_index);
			g.drawImage(sprite_index.getSubsprites()[temp_image_index], x
					- sprite_index.getCenterX(), y - sprite_index.getCenterY(),
					null);

			image_index = (image_index + image_speed)
					% sprite_index.getSubimages();

			if (Global.DEBUG && boundingBox != null) {
				g.setColor(Color.red);
				g.drawRect(boundingBox.getX(), boundingBox.getY(),
						boundingBox.getWidth(), boundingBox.getHeight());
				g.setColor(Color.black);
			}
		}
	}

	/**
	 * Step event
	 * 
	 * @param key key
	 * @param direction direction
	 */
	public void step(KEY key, KEY direction) {
		alarmHandling();
		alarmCode();
		checkAnimationEnd();
		customStep(key, direction);
		List<Objeto> colisiones = collision();
		if (colisiones != null)
			for (Objeto obj : colisiones)
				customCollision(obj);
		processKey(key, direction);
	}

	/**
	 * Alarm handler
	 */
	private void alarmHandling() {
		if (alarmsSet > 0) {
			for (int i = 0; i < alarm.length; i++) {
				if (alarm[i] > 0) {
					alarm[i]--;
				}
				if (alarm[i] == 0) {
					alarm[i]--;
					alarmsOff.add(i);
					alarmsSet--;
				}
			}
		}
	}

	/**
	 * Triggers the alarm code
	 */
	private void alarmCode() {
		for (int i : alarmsOff) {
			alarm(i);
		}

		for (int i = 0; i < alarmsOff.size();) {
			alarmsOff.remove(i);
		}
	}

	/**
	 * @param alarmNo
	 *            alarm number
	 * @return true if the alarm is set, false otherwise
	 */
	public boolean isAlarmSet(int alarmNo) {
		return (alarm[alarmNo] > 0);
	}

	/**
	 * Unsets the alarm
	 * 
	 * @param alarmNo
	 *            alarm number
	 */
	public void unsetAlarm(int alarmNo) {
		if (alarm[alarmNo] > 0) {
			alarm[alarmNo] = -1;
			alarmsSet--;
		}
	}

	/**
	 * Destroy event
	 */
	public void destroy() {
		customDestroy();
		myRoom.destroy(this);
	}

	/**
	 * Sets an alarm
	 * 
	 * @param alarmNo
	 *            alarm number
	 * @param steps
	 *            number of steps until triggered
	 */
	public void setAlarm(int alarmNo, int steps) {
		if (alarmNo >= 0 && steps >= 0) {
			alarm[alarmNo] = steps;
			alarmsSet++;
		}
	}

	/**
	 * @param modX
	 *            x modification
	 * @param modY
	 *            y modification
	 * @return true if the object has moved, false otherwise
	 */
	public boolean tryToMove(int modX, int modY) {
		boundingBox.update(modX, modY);
		if (collision() != null) {
			boundingBox.update(-modX, -modY);
			return false;
		} else {
			x = x + modX;
			y = y + modY;
			return true;
		}
	}

	/**
	 * Collision event
	 * 
	 * @return list of objects collided with
	 */
	public List<Objeto> collision() {
		List<Objeto> returned = null;
		List<Objeto> objects = myRoom.objetos;

		for (Objeto obj : objects) {
			if (!obj.equals(this)) {
				boolean collision = BoundingBox.collision(boundingBox,
						obj.boundingBox);
				if (collision) {
					if (returned == null) {
						returned = new LinkedList<Objeto>();
					}
					returned.add(obj);
				}
			}
		}

		return returned;
	}

	/**
	 * Check the animation end
	 */
	public void checkAnimationEnd() {
		if (image_index < previous_image_index
				&& sprite_index == prev_sprite_index) {
			animation_end = true;
		}
		previous_image_index = image_index;
	}

	/**
	 * Resets the animation end
	 */
	public void resetAnimationEnd() {
		animation_end = false;
	}

}
