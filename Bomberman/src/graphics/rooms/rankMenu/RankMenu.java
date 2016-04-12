/**
 * Class representing the ranking menu screen
 */
package graphics.rooms.rankMenu;

import java.awt.Graphics;
import java.util.List;

import graphics.effects.Button;
import graphics.effects.Visual;
import graphics.rooms.Room;
import logic.Global;
import logic.Input.KEY;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import logic.collisions.Point2D;
import logic.misc.Record;
import sound.MusicRepository;
import utils.PaintService;

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

	/**
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @param n
	 *            name
	 * @param record
	 *            new record, or null
	 */
	public RankMenu(int w, int h, String n, Record record) {
		super(w, h, n);

		this.record = record;
	}

	@Override
	public void load() {
		RankMenuRepository.load();

		this.background = RankMenuRepository.background;

		int x = width / 2;
		boolean gameOver = false;

		if (this.record != null && this.record.getScore() > 0) {
			gameOver = true;
			newRecord = Global.ranking.newRecord(record);

			Sprite next = RankMenuRepository.continueButton;
			int y = this.height - PADDING_BORDER - next.getHeight() / 2;

			Button next_obj = new Button(x, y, this, next);
			next_obj.select();
			addObjeto(next_obj);
		} else {
			Sprite back = RankMenuRepository.backButton;
			int y = this.height - PADDING_BORDER - back.getHeight() / 2;

			Button back_obj = new Button(x, y, this, back);
			back_obj.select();
			addObjeto(back_obj);
		}

		Sprite title = RankMenuRepository.titleButton;
		int y = PADDING_BORDER + title.getHeight() / 2;
		addObjeto(new Visual(x, y, this, title));

		if (gameOver) {
			setMusic(MusicRepository.gameOver, true);
		} else {
			setMusic(MusicRepository.menu, true);
		}

	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		if (!loadComplete()) {
			return;
		}

		List<Record> records = Global.ranking.getRecords();

		int x = 150;
		int y = PADDING_BORDER + RankMenuRepository.titleButton.getHeight()
				+ TITLEBUTTON_BORDER;

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

			PaintService.paintDigits(str, initial_position, g);
		}

		if (record != null && !newRecord) {
			String str = "NEW " + this.record.getScore();
			x = width / 2;
			Point2D initial_position = new Point2D(x, y + modY
					* (records.size() + 1));
			PaintService.paintDigits(str, initial_position, g);
		}

	}

	@Override
	public void drawBackground(Graphics g) {
		if (!loadComplete()) {
			return;
		}
		g.clearRect(0, 0, width, height);
		if (background != null) {
			g.drawImage(background.getSubsprites()[0], 0, 0, null);
		}
	}

	/**
	 * Changes state
	 */
	private void confirm() {
		StatesMachine.goToRoom(STATE.MAIN_MENU, false);
	}

	@Override
	public void step(KEY key, KEY direction) {
		super.step(key, direction);
		if (!loadComplete()) {
			return;
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
