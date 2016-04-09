/**
 * Class representing a level
 */
package logic.misc;

import logic.Objeto;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Level extends Objeto {

	public int mapInitX, mapInitY;
	public int mapWidth, mapHeight;

	/**
	 * @param x
	 *            x initial position of the map
	 * @param y
	 *            y initial position of the map
	 * @param widthPX
	 *            width (pixels) of the map
	 * @param heightPX
	 *            height (pixels) of the map
	 */
	public Level(int x, int y, int widthPX, int heightPX) {
		super(x, y, 0, null);
		mapInitX = x;
		mapInitY = y;

		mapWidth = widthPX;
		mapHeight = heightPX;
	}
}
