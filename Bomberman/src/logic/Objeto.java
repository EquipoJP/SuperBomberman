package logic;

import java.awt.Graphics;

import graphics.D2.rooms.Room;

public abstract class Objeto{

	public int x;
	public int y;
	public int depth;
	public Sprite sprite_index;
	public Room myRoom;
	
	public Objeto(int x, int y, Room r, int depth) {
		this.x = x;
		this.y = y;
		this.depth = depth;
		myRoom = r;
		create();
	}
	
	public Objeto(int x, int y, Room r){
		this.x = x;
		this.y = y;
		depth = 0;
		myRoom = r;
		create();
	}
	
	public abstract void create();
	
	public abstract void step();
	
	public abstract void render(Graphics g);
	
	public abstract void customDestroy();
	
	public void destroy(){
		customDestroy();
		myRoom.destroy(this);
	}
}
