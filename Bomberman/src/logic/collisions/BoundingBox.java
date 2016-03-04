package logic.collisions;

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

		return Point2D.lessThan(bb1.min, bb2.min) && Point2D.greaterThan(bb1.max, bb2.min);
	}
	
	public BoundingBox copy(){
		return new BoundingBox(min, max);
	}

}
