package logic;

import java.awt.Graphics;

import graphics.D2.rooms.Room;

public abstract class Objeto <T extends Room>{

	public int x;
	public int y;
	public int depth;
	public Sprite sprite_index;
	public T room;
	
	public Objeto(int x, int y, int depth, T room) {
		this.x = x;
		this.y = y;
		this.depth = depth;
		this.room = room;
		create();
	}
	
	public Objeto(int x, int y, T room){
		this.x = x;
		this.y = y;
		depth = 0;
		this.room = room;
		create();
	}
	
	public abstract void create();
	
	public abstract void step();
	
	public abstract void render(Graphics g);
}
