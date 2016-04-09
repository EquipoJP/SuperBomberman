/**
 * Class for defining a non-perspective bounding box
 */
package logic.collisions;

import logic.Sprite;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class NoPerspectiveBoundingBox extends BoundingBox {

	/**
	 * Creates a new bounding box covering the sprite's area
	 * 
	 * @param s
	 *            sprite
	 * @return bounding box centered on (0,0) and of the size of the sprite
	 */
	public static NoPerspectiveBoundingBox createBoundingBox(Sprite s) {
		if (s == null) {
			return null;
		}

		int minX = -s.getWidth() / 2;
		int minY = -s.getHeight() / 2;
		Point2D min = new Point2D(minX, minY);

		int maxX = s.getWidth() / 2;
		int maxY = s.getHeight() / 2;
		Point2D max = new Point2D(maxX, maxY);

		return new NoPerspectiveBoundingBox(min, max);
	}

	/**
	 * Creates a bounding box from two points
	 * 
	 * @param min
	 *            up-left corner
	 * @param max
	 *            down-right corner
	 */
	public NoPerspectiveBoundingBox(Point2D min, Point2D max) {
		super(min, max);
	}
}
