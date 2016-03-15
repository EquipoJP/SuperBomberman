/**
 * Class representing the ranking menu screen
 */
package graphics.D2.rooms.rankMenu;

import graphics.D2.rooms.Room;
import graphics.effects.Visual;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import logic.Global;
import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import logic.collisions.Point2D;
import logic.misc.Record;
import main.Initialization;
import utils.PaintDigitsService;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class RankMenu extends Room {

	private Sprite background;
	private Record record;
	private boolean newRecord;

	private static final int PADDING_BORDER = 25;
	private static final int INTERBUTTON_BORDER = 48;
	private static final int TITLEBUTTON_BORDER = 64;

	public RankMenu(int w, int h, String n, Record record) {
		super(w, h, n);
		
		this.record = record;
	}

	@Override
	public void load() {
		this.background = null;	//TODO
		
		int x = width / 2;

		if (this.record != null) {
			newRecord = Global.ranking.newRecord(record);

			Sprite next = Initialization
					.getSpriteFromMenu(Initialization.BUTTONS.CONTINUE_BUTTON
							.toString());
			next.setSubimages(1);
			next.setSubsprites(new BufferedImage[] { next.getSubsprites()[1] });
			int y = this.height - PADDING_BORDER - next.getHeight() / 2;

			addObjeto(new Visual(x, y, this, next));
		} else {
			Sprite back = Initialization
					.getSpriteFromMenu(Initialization.BUTTONS.BACK_BUTTON
							.toString());
			back.setSubimages(1);
			back.setSubsprites(new BufferedImage[] { back.getSubsprites()[1] });
			int y = this.height - PADDING_BORDER - back.getHeight() / 2;

			addObjeto(new Visual(x, y, this, back));
		}

		Sprite title = Initialization
				.getSpriteFromMenu(Initialization.BUTTONS.TITLE_BUTTON
						.toString());
		int y = PADDING_BORDER + title.getHeight() / 2;
		addObjeto(new Visual(x, y, this, title));

	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		
		if(!loadComplete()){
			return ;
		}

		// TODO coger el ranking global
		List<Record> records = Global.ranking.getRecords();

		// TODO dimensiones x y
		int x = 150;
		int y = PADDING_BORDER
				+ Initialization.getSpriteFromMenu(
						Initialization.BUTTONS.TITLE_BUTTON.toString())
						.getHeight() + TITLEBUTTON_BORDER;

		int modY = INTERBUTTON_BORDER;

		for (int i = 0; i < records.size(); i++) {
			String newRecord = "";
			Record record = records.get(i);

			if (this.record != null) {
				if (record.equals(this.record)) {
					newRecord = "NEW ";
				}
			}
			String str = newRecord + (i + 1) + ". " + record.getScore();
			Point2D initial_position = new Point2D(x, y + modY * i);

			PaintDigitsService.paint(str, initial_position, g);
		}

		if (record != null && !newRecord) {
			String str = "NEW " + this.record.getScore();
			x = width / 2;
			Point2D initial_position = new Point2D(x, y + modY * (records.size() + 1));
			PaintDigitsService.paint(str, initial_position, g);
		}

	}

	@Override
	public void drawBackground(Graphics g) {
		if(!loadComplete()){
			return ;
		}
		g.clearRect(0, 0, width, height);
		if (background != null) {
			g.drawImage(background.getSubsprites()[0], 0, 0, null);
		}
	}

	private void confirm() {
		StatesMachine.goToRoom(STATE.MAIN_MENU, false);
	}

	@Override
	public void step(KEY key, KEY direction) {
		super.step(key, direction);
		if(!loadComplete()){
			return ;
		}
		switch (key) {
		case ENTER:
			confirm();
			break;
		case ESCAPE:
			confirm();
			break;
		default:
			break;
		}
	}
}
