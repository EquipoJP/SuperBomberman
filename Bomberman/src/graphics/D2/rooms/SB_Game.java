/**
 * Class representing the Super-Bomber game mode screen
 */
package graphics.D2.rooms;

import java.awt.Graphics;
import java.util.List;

import logic.Input.KEY;
import logic.Level;
import logic.Map;
import logic.Objeto;
import logic.Sprite;
import main.Initialization;
import main.Initialization.COLOR;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class SB_Game extends Game {

	private Sprite tiles;
	private Level level;

	public SB_Game(int w, int h, String n) {
		super(w, h, n);

		String file = "maps/level1.txt";
		COLOR c = Initialization.COLOR.GREEN;

		tiles = Initialization.getSpriteFromMap(c.toString() + "_TILE");
		List<Objeto> objetos = Map.getMap(file, this, c);

		for (Objeto obj : objetos) {
			if (obj instanceof Level) {
				level = (Level) obj;
			} else {
				addObjeto(obj);
			}
		}
		System.out.println("SB_GAME");
	}

	@Override
	public void drawBackground(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0, 0, width, height);

		if (level != null) {
			for (int x = level.mapInitX; x < level.mapInitX + level.mapWidth; x += tiles.getWidth()) {
				for (int y = level.mapInitY; y < level.mapInitY + level.mapHeight; y += tiles.getHeight()) {
					g.drawImage(tiles.getSubsprites()[0], x - tiles.getCenterX(), y - tiles.getCenterY(), null);
				}
			}
		}
	}

	@Override
	public void step(KEY key) {
		// TODO Auto-generated method stub
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).step(key);
		}
	}

}
