package logic;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import graphics.D2.rooms.Room;
import logic.Input.KEY;

public abstract class Objeto{

	public int x;
	public int y;
	public int depth;
	
	public Sprite sprite_index;
	protected int index;
	
	public Room myRoom;
	
	public int alarm[];
	public int alarmsSet;
	
	private List<Integer> alarmsOff;
	
	public Objeto(int x, int y, Room r){
		this.x = x;
		this.y = y;
		depth = 0;
		alarm = new int[20];
		for(int i = 0; i < alarm.length; i++){
			alarm[i] = -1;
		}
		alarmsSet = 0;
		myRoom = r;
		alarmsOff = new LinkedList<Integer>();
		sprite_index = null;
		index = -1;
		create();
	}
	
	public abstract void create();
	
	public abstract void customStep(KEY key);
	
	public abstract void alarm(int alarmNo);
	
	public void render(Graphics g){
		if(sprite_index != null){
			if(index < 0){
				index = 0;
			}
			g.drawImage(sprite_index.getSubsprites()[index], x - sprite_index.getCenterX(), y - sprite_index.getCenterY(),
				null);
			
			index = (index + 1) % sprite_index.getSubimages();
		}
	}
	
	public abstract void customDestroy();
	
	public abstract void processKey(KEY key);
	
	public void step(KEY key){
		alarmHandling();
		alarmCode();
		customStep(key);
		// Collision?
		processKey(key);
	}
	
	private void alarmHandling(){
		if(alarmsSet > 0){
			for(int i = 0; i < alarm.length; i++){
				if(alarm[i]>0){
					alarm[i]--;
				}
				if(alarm[i]==0){
					alarm[i]--;
					alarmsOff.add(i);
					alarmsSet--;
				}
			}
		}
	}
	
	private void alarmCode(){
		for(int i : alarmsOff){
			alarm(i);
		}
		
		for(int i = 0; i < alarmsOff.size();){
			alarmsOff.remove(i);
		}
	}
	
	public void destroy(){
		customDestroy();
		myRoom.destroy(this);
	}
	
	public void setAlarm(int alarmNo, int steps){
		if(alarmNo>=0 && steps >=0){
			alarm[alarmNo] = steps;
			alarmsSet++;
		}
	}
}
