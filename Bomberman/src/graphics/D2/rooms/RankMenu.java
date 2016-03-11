/**
 * Class representing the ranking menu screen
 */
package graphics.D2.rooms;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import logic.Sprite;
import logic.collisions.Point2D;
import logic.misc.Record;
import utils.PaintDigitsService;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class RankMenu extends Room {

	private Sprite background;

	public RankMenu(int w, int h, String n, Sprite background) {
		super(w, h, n);
		this.background = background;
	}

	@Override
	public void render(Graphics g) {
		drawBackground(g);

		// TODO coger el ranking global
		List<Record> records = new LinkedList<>();	//Global.ranking.getRecords();
		records.add(new Record(100));
		records.add(new Record(53));

		// TODO dimensiones x y
		int x = 100;
		int y = 100;
		int modY = 100;
		
		for(int i = 0; i < records.size(); i++){
			Record record = records.get(i);
			String str = (i+1) + ". " + record.getScore();
			Point2D initial_position = new Point2D(x, y + modY * i);
			
			PaintDigitsService.paint(str, initial_position, g);
		}
		
	}

	@Override
	public void drawBackground(Graphics g) {
		g.clearRect(0, 0, width, height);
		if (background != null) {
			g.drawImage(background.getSubsprites()[0], 0, 0, null);
		}
	}

}
