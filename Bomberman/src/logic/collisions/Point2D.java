package logic.collisions;

public class Point2D {

	private int x;
	private int y;

	public Point2D() {
		x = 0;
		y = 0;
	}

	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void update(int xMov, int yMov) {
		this.x = this.x + xMov;
		this.y = this.y + yMov;
	}
	
	public static boolean lessThan(Point2D small, Point2D big){
		if(small == null || big == null){
			return false;
		}
		return small.x < big.x && small.y < big.y;
	}
	
	public static boolean greaterThan(Point2D big, Point2D small){
		return lessThan(big, small);
	}
}
