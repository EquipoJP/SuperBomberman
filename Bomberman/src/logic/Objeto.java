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

	public Objeto(int x, int y, Room r) {
		this.x = x;
		this.y = y;
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

	public void create() {
		// You must override this to use it
	}

	public void customStep(KEY key, KEY direction) {
		// You must override this to use it
	}

	public void alarm(int alarmNo) {
		// You must override this to use it
	}

	public void customCollision(Objeto colision) {
		// You must override this to use it
	}

	public void customDestroy() {
		// You must override this to use it
	}

	public void processKey(KEY key, KEY direction) {
		// You must override this to use it
	}

	public void render(Graphics g) {
		if (sprite_index != null) {
			if (image_speed != 0 && (prev_sprite_index == null || (prev_sprite_index != null && !sprite_index.equals(prev_sprite_index)))) {
				prev_sprite_index = sprite_index;
				image_index = 0;
				previous_image_index = 0;
			}
			if (image_index < 0) {
				image_index = 0;
				previous_image_index = 0;
			}
			int temp_image_index = (int) Math.floor(image_index);
			g.drawImage(sprite_index.getSubsprites()[temp_image_index], x - sprite_index.getCenterX(),
					y - sprite_index.getCenterY(), null);

			image_index = (image_index + image_speed) % sprite_index.getSubimages();

			if (Global.DEBUG && boundingBox != null) {
				g.setColor(Color.red);
				g.drawRect(boundingBox.getX(), boundingBox.getY(), boundingBox.getWidth(), boundingBox.getHeight());
				g.setColor(Color.black);
			}
		}
	}

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

	private void alarmCode() {
		for (int i : alarmsOff) {
			alarm(i);
		}

		for (int i = 0; i < alarmsOff.size();) {
			alarmsOff.remove(i);
		}
	}
	
	public boolean isAlarmSet(int alarmNo){
		return (alarm[alarmNo] > 0);
	}
	
	public void unsetAlarm(int alarmNo){
		if(alarm[alarmNo] > 0){
			alarm[alarmNo] = -1;
			alarmsSet--;
		}
	}

	public void destroy() {
		customDestroy();
		myRoom.destroy(this);
	}

	public void setAlarm(int alarmNo, int steps) {
		if (alarmNo >= 0 && steps >= 0) {
			alarm[alarmNo] = steps;
			alarmsSet++;
		}
	}

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

	public List<Objeto> collision() {
		List<Objeto> returned = null;
		List<Objeto> objects = myRoom.objetos;

		for (Objeto obj : objects) {
			if (!obj.equals(this)) {
				boolean collision = BoundingBox.collision(boundingBox, obj.boundingBox);
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

	public void checkAnimationEnd() {
		if (image_index < previous_image_index && sprite_index == prev_sprite_index) {
			animation_end = true;
		}
		previous_image_index = image_index;
	}

	public void resetAnimationEnd() {
		animation_end = false;
	}
	
}
