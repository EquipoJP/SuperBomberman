/**
 * Class defining a point in two dimensions
 */
package logic.collisions;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Point2D {

	private int x;
	private int y;

	/**
	 * Creates a point in (0,0)
	 */
	public Point2D() {
		x = 0;
		y = 0;
	}

	/**
	 * Creates a point in (x,y)
	 * @param x
	 * @param y
	 */
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Updates a point
	 * @param xMov x update
	 * @param yMov y update
	 */
	public void update(int xMov, int yMov) {
		this.x = this.x + xMov;
		this.y = this.y + yMov;
	}
	
	/**
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
