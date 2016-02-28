package logic;

import java.awt.Graphics;

public abstract class Objeto{

	public int x;
	public int y;
	public int depth;
	public Sprite sprite_index;
	
	public Objeto(int x, int y, int depth) {
		this.x = x;
		this.y = y;
		this.depth = depth;
		create();
	}
	
	public Objeto(int x, int y){
		this.x = x;
		this.y = y;
		depth = 0;
		create();
	}
	
	public abstract void create();
	
	public abstract void step();
	
	public abstract void render(Graphics g);
}
