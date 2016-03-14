package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import graphics.D2.rooms.Room;
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

	public Room myRoom;

	public int alarm[];
	public int alarmsSet;

	private List<Integer> alarmsOff;

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
		image_speed = 1;
		create();
	}

	public abstract void create();

	public abstract void customStep(KEY key, KEY direction);

	public abstract void alarm(int alarmNo);

	public void render(Graphics g) {
		if (sprite_index != null) {
			if (prev_sprite_index == null || (prev_sprite_index != null && !sprite_index.equals(prev_sprite_index))) {
				prev_sprite_index = sprite_index;
				image_index = 0;
			}
			if (image_index < 0) {
				image_index = 0;
			}
			int temp_image_index = (int) Math.floor(image_index);
			g.drawImage(sprite_index.getSubsprites()[temp_image_index], x - sprite_index.getCenterX(),
					y - sprite_index.getCenterY(), null);

			image_index = (image_index + image_speed) % sprite_index.getSubimages();
			
			if(Global.DEBUG && boundingBox != null){
				g.setColor(Color.red);
				g.drawRect(boundingBox.getX(), boundingBox.getY(), boundingBox.getWidth(), boundingBox.getHeight());
				g.setColor(Color.black);
			}
		}
	}

	public abstract void customDestroy();

	public abstract void processKey(KEY key, KEY direction);

	public void step(KEY key, KEY direction) {
		alarmHandling();
		alarmCode();
		customStep(key, direction);
		// Collision?
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
	
	public void tryToMove(int modX, int modY){
		boundingBox.update(modX, modY);
		if(collision()){
			boundingBox.update(-modX, -modY);
		}
		else{
			x = x + modX;
			y = y + modY;
		}
	}
	
	public boolean collision(){
		boolean collision = false;
		List<Objeto> objects = myRoom.objetos;
		
		for(Objeto obj : objects){
			if(!obj.equals(this)){
				collision = BoundingBox.collision(boundingBox, obj.boundingBox);
				if(collision){
					break;
				}
			}
		}
		
		return collision;
	}
}
