/**
 * Class representing the generic game screen
 */
package graphics.D2.rooms;

import logic.Level;
import logic.Sprite;
import logic.TileMap;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public abstract class Game extends Room {
	
	public Sprite tiles;
	public Level level;
	
	public TileMap map;

	public Game(int w, int h, String n) {
		super(w, h, n);
		// TODO Auto-generated constructor stub
	}

}
