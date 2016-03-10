package logic.collisions;

import java.awt.Rectangle;

public class BoundingBox {

	private Point2D min;
	private Point2D max;

	public BoundingBox(Point2D min, Point2D max) {
		this.min = min;
		this.max = max;
	}

	public void update(int xMov, int yMov) {
		this.min.update(xMov, yMov);
		this.max.update(xMov, yMov);
	}

	public static boolean collision(BoundingBox bb1, BoundingBox bb2) {
		if (bb1 == null || bb2 == null) {
			return false;
		}
		Rectangle r1 = bb1.getRectangle();
		Rectangle r2 = bb2.getRectangle();
		
		return r1.intersects(r2);
	}

	private Rectangle getRectangle() {
		int x = min.getX();
		int y = min.getY();
		
		int width = max.getX() - min.getX();
		int height = max.getY() - min.getY();
		
		return new Rectangle(x, y, width, height);
	}
	
	public int getX(){
		return min.getX();
	}
	
	public int getY(){
		return min.getY();
	}
	
	public int getWidth(){
		return max.getX() - min.getX();
	}
	
	public int getHeight(){
		return max.getY() - min.getY();
	}

	public BoundingBox copy() {
		return new BoundingBox(min, max);
	}

	@Override
	public String toString() {
		return "[ " + min.toString() + " , " + max.toString() + " ]";
	}
}
