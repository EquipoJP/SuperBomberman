/**
 * Class representing a bounding box
 */
package logic.collisions;

import java.awt.Rectangle;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class BoundingBox {

	private Point2D min;
	private Point2D max;

	/**
	 * @param min
	 *            up-left corner
	 * @param max
	 *            down-right corner
	 */
	public BoundingBox(Point2D min, Point2D max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Updates the position of the bounding box
	 * 
	 * @param x
	 *            x movement
	 * @param y
	 *            y movement
	 */
	public void update(int x, int y) {
		this.min.update(x, y);
		this.max.update(x, y);
	}

	/**
	 * Check if two bounding boxes collide
	 * 
	 * @param bb1
	 *            first bounding box
	 * @param bb2
	 *            seconds bounding box
	 * @return
	 */
	public static boolean collision(BoundingBox bb1, BoundingBox bb2) {
		if (bb1 == null || bb2 == null) {
			return false;
		}
		Rectangle r1 = bb1.getRectangle();
		Rectangle r2 = bb2.getRectangle();

		return r1.intersects(r2);
	}

	/**
	 * @param bb1
	 *            first bounding box
	 * @param bb2
	 *            seconds bounding box
	 * @return rectangle shared by the two bounding boxes
	 */
	public static Rectangle collisionRectangle(BoundingBox bb1, BoundingBox bb2) {
		if (bb1 == null || bb2 == null) {
			return new Rectangle();
		}
		Rectangle r1 = bb1.getRectangle();
		Rectangle r2 = bb2.getRectangle();

		return r1.intersection(r2);
	}

	/**
	 * Transforms the bounding box to a rectangle
	 * 
	 * @return rectangle created from bounding box
	 */
	private Rectangle getRectangle() {
		int x = min.getX();
		int y = min.getY();

		int width = max.getX() - min.getX();
		int height = max.getY() - min.getY();

		return new Rectangle(x, y, width, height);
	}

	/**
	 * @return minimum x coordinate
	 */
	public int getX() {
		return min.getX();
	}

	/**
	 * @return minimum y coordinate
	 */
	public int getY() {
		return min.getY();
	}

	/**
	 * @return width of the bounding box
	 */
	public int getWidth() {
		return max.getX() - min.getX();
	}

	/**
	 * @return height of the bounding box
	 */
	public int getHeight() {
		return max.getY() - min.getY();
	}

	/**
	 * @return new bounding box with the same parameters as the first
	 */
	public BoundingBox copy() {
		return new BoundingBox(min, max);
	}

	@Override
	public String toString() {
		return "[ " + min.toString() + " , " + max.toString() + " ]";
	}
}
