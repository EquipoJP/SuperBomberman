/**
 * Class representing the generic game screen
 */
package graphics.D2.rooms.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import graphics.D2.rooms.Room;
import graphics.effects.Visual;
import kuusisto.tinysound.Music;
import logic.Global;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.StatesMachine;
import logic.characters.DestroyableBlock;
import logic.characters.Enemy;
import logic.characters.Player;
import logic.collisions.Point2D;
import logic.misc.Level;
import logic.misc.Map;
import main.Initialization;
import main.Initialization.STAGE;
import sound.MusicRepository;
import utils.ConvertTimeService;
import utils.PaintService;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Game extends Room {

	private int enemiesDestroyed;
	private int blocksDestroyed;

	protected Sprite tiles;
	protected Level level;

	private long seconds;
	private long secondsVictory;

	private enum STATE {
		INIT, GAME, DESTRUCTION, VICTORY
	};

	private STATE state;

	private Timer timer;
	private TimerTask task;

	private final long SECONDS_PHASE = 50; // TODO

	private String file;
	private STAGE stage;

	private Sprite hud;
	private Sprite victory;
	private Visual victoryVisual;

	private Music intro = null;
	private Music defeat = null;
	private Music victoryMsc = null;
	private boolean startedLevel = false;
	private boolean defeatedMusic = false;

	public Game(int w, int h, String n) {
		super(w, h, n);

		this.enemiesDestroyed = 0;
		this.blocksDestroyed = 0;
	}

	@Override
	public void load() {
		this.file = Global.levels.actualLevel().getFile();
		this.stage = Global.levels.actualLevel().getStage();

		GameRepository.load(stage);

		this.background = GameRepository.background;

		state = STATE.INIT;
		seconds = SECONDS_PHASE;
		secondsVictory = -1;

		tiles = GameRepository.tiles;
		List<Objeto> objetos = Map.getMap(file, this, stage);

		for (Objeto obj : objetos) {
			if (obj instanceof Level) {
				level = (Level) obj;
			} else {
				addObjeto(obj);
			}
		}

		hud = GameRepository.hud;
		victory = GameRepository.victory;
		victoryVisual = null;
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

		drawHUD(g);

		if (level != null) {
			for (int x = level.mapInitX; x < level.mapInitX + level.mapWidth; x += tiles.getWidth()) {
				for (int y = level.mapInitY; y < level.mapInitY + level.mapHeight; y += tiles.getHeight()) {
					if (tiles != null) {
						g.drawImage(tiles.getSubsprites()[0], x - tiles.getCenterX(), y - tiles.getCenterY(), null);
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (!loadComplete()) {
			super.render(g);
			return;
		}

		switch (state) {
		case INIT:
			super.render(g);

			// transparency
			Color transparent = new Color(0, 0, 0, 128);
			g.setColor(transparent);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.black);

			Point2D initial_position = new Point2D(width / 2 - Initialization.ALPHANUM_WIDTH / 2,
					height / 2 + Initialization.ALPHANUM_HEIGHT / 2);
			String stage = "Stage123:.";
			Point2D initial_position_text = new Point2D(
					width / 2 - (Initialization.ALPHANUM_WIDTH * stage.length()) / 2,
					height / 2 - Initialization.ALPHANUM_HEIGHT / 2);
			int lvl = Global.levels.level() + 1;
			PaintService.paintTextColor(stage, initial_position_text, g, PaintService.DIGITS_ORANGE.getRGB());
			PaintService.paintDigits("" + lvl, initial_position, g);
			break;
		default:
			super.render(g);
			break;
		}
	}

	private void drawHUD(Graphics g) {
		g.drawImage(hud.getSubsprites()[0], 0, 0, null);

		// TODO change values here
		int x = (width / 2) - (32 * 5 / 2);
		int y = ((124 - 32 / 2) / 2) - (32 / 2);
		Point2D init_pos = new Point2D(x, y);

		PaintService.paintDigits(ConvertTimeService.timeToString(seconds), init_pos, g);
	}

	@Override
	public void step(KEY key, KEY direction) {
		if (!loadComplete()) {
			return;
		}

		switch (state) {
		case INIT:
			if (intro == null) {
				intro = MusicRepository.intro;
			}
			if (!startedLevel) {
				intro.rewind();
				intro.play(false);
				startedLevel = true;
			}
			if (intro.done()) {
				state = STATE.GAME;
			}
			break;
		case GAME:
			super.step(key, direction);

			if (startedLevel) {
				startedLevel = false;
			}

			if (!MusicRepository.battle.done())
				setMusic(MusicRepository.battle, true);

			if ((key == KEY.ENTER || key == KEY.ESCAPE) && secondsVictory < 0) {
				// Pause menu being persistent
				StatesMachine.goToRoom(StatesMachine.STATE.PAUSE, true);
			} else if (checkTime()) {
				callForDestruction();
			} else if (noEnemies()) {
				callForVictory();
			} else {
				setTimer();
			}
			break;
		case DESTRUCTION:
			super.step(key, direction);

			if (defeat == null) {
				defeat = MusicRepository.defeat;
			}

			if (!defeatedMusic) {
				stopMusic();
				defeat.rewind();
				defeat.play(false);
				defeatedMusic = true;
			}
			if (noPlayer()) {
				if (defeat.done()) {
					defeatedMusic = false;
					Global.levels.resetLevel();
					terminate();
					StatesMachine.goToRoom(StatesMachine.STATE.TOP10, false);
				}
			}
			break;
		case VICTORY:
			for (Objeto obj : objetos) {
				if (obj instanceof Player) {
					Player player = (Player) obj;
					player.step(KEY.NO_KEY, KEY.NO_KEY);
				}
			}

			if (victoryMsc.done()) {
				Global.levels.nextLevel();
				terminate();
				StatesMachine.goToRoom(StatesMachine.STATE.GAME, false);
			}
			break;
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		cancelTimer();
	}

	@Override
	public void destroy(Objeto o) {
		super.destroy(o);

		if (o instanceof Enemy) {
			enemiesDestroyed++;
		}
		if (o instanceof DestroyableBlock) {
			blocksDestroyed++;
		}
	}

	@Override
	public void pause() {
		super.pause();
		cancelTimer();
	}

	@Override
	public void resume() {
		super.resume();
		setTimer();
	}

	public void callForDestruction() {
		state = STATE.DESTRUCTION;
		cancelTimer();
		for (Objeto obj : objetos) {
			if (obj instanceof Player) {
				Player player = (Player) obj;
				System.out.println("Time out!");
				player.callForDestruction();
			}
		}
	}

	public void callForVictory() {
		state = STATE.VICTORY;
		if (victoryVisual == null) {
			stopMusic();

			victoryMsc = MusicRepository.victory;
			victoryMsc.rewind();
			victoryMsc.play(false);

			cancelTimer();

			victoryVisual = new Visual(width / 2, height / 2, this, victory);
			victoryVisual.depth = Global.EFFECTS_DEPTH;
			addObjeto(victoryVisual);
		}
	}

	private void terminate() {
		if (state != STATE.DESTRUCTION) {
			Global.scoreManager.updateScoreSeconds(seconds);
		}
		Global.scoreManager.updateScoreEnemies(enemiesDestroyed);
		Global.scoreManager.updateScoreBlocks(blocksDestroyed);
	}

	private boolean noPlayer() {
		for (Objeto obj : objetos) {
			if (obj instanceof Player) {
				return false;
			}
		}
		return true;
	}

	private boolean noEnemies() {
		for (Objeto obj : objetos) {
			if (obj instanceof Enemy) {
				return false;
			}
		}
		return true;
	}

	private void setTimer() {
		if (!loadComplete()) {
			return;
		}
		if (seconds < 0) {
			seconds = SECONDS_PHASE;
		}
		if (timer == null || task == null) {
			timer = new Timer();

			task = new TimerTask() {
				@Override
				public void run() {
					seconds--;
				}
			};

			timer.scheduleAtFixedRate(task, 0, (1 * 1000));
		}
	}

	private boolean checkTime() {
		return (seconds <= 0);
	}

	private void cancelTimer() {
		if (timer != null) {
			timer.cancel();
		}
		if (task != null) {
			task.cancel();
		}
		timer = null;
		task = null;
	}

}
