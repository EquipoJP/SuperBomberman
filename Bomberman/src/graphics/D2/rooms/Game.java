/**
 * Class representing the generic game screen
 */
package graphics.D2.rooms;

import java.awt.Graphics;
import java.util.List;

import logic.Level;
import logic.Map;
import logic.Objeto;
import logic.Sprite;
import main.Initialization;
import main.Initialization.STAGE;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public abstract class Game extends Room {
	
	protected Sprite tiles;
	protected Level level;

	public Game(int w, int h, String n, String file, STAGE stage) {
		super(w, h, n);
		
		tiles = Initialization.getSpriteFromMap(stage.toString() + "_" + Initialization.TYPE.TILE.toString());
		List<Objeto> objetos = Map.getMap(file, this, stage);

		for (Objeto obj : objetos) {
			if (obj instanceof Level) {
				level = (Level) obj;
			} else {
				addObjeto(obj);
			}
		}
	}
	
	@Override
	public void drawBackground(Graphics g) {
		g.clearRect(0, 0, width, height);

		if (level != null) {
			for (int x = level.mapInitX; x < level.mapInitX + level.mapWidth; x += tiles.getWidth()) {
				for (int y = level.mapInitY; y < level.mapInitY + level.mapHeight; y += tiles.getHeight()) {
					g.drawImage(tiles.getSubsprites()[0], x - tiles.getCenterX(), y - tiles.getCenterY(), null);
				}
			}
		}
	}

}
