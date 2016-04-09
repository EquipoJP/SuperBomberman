/**
 * Class for defining a perspective bounding box
 */
package logic.collisions;

import logic.Sprite;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class PerspectiveBoundingBox extends BoundingBox {

	/**
	 * Creates a perspective bounding box from a sprite
	 * 
	 * @param s
	 *            sprite
	 * @return perspective bounding box
	 */
	public static PerspectiveBoundingBox createBoundingBox(Sprite s) {
		if (s == null) {
			return null;
		}

		int minX = -s.getWidth() / 2 + s.getWidth() / 8;
		int minY = s.getHeight() / 10;
		Point2D min = new Point2D(minX, minY);

		int maxX = s.getWidth() / 2 - s.getWidth() / 8;
		int maxY = s.getHeight() / 2 - s.getHeight() / 10;
		Point2D max = new Point2D(maxX, maxY);

		return new PerspectiveBoundingBox(min, max);
	}

	/**
	 * Creates a perspective bounding box from two points
	 * 
	 * @param min
	 *            up-left corner
	 * @param max
	 *            down-right corner
	 */
	public PerspectiveBoundingBox(Point2D min, Point2D max) {
		super(min, max);
	}
}
