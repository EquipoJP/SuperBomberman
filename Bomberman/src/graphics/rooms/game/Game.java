/**
 * Class representing the game screen
 */
package graphics.rooms.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import graphics.d3.trazador.Trazador;
import graphics.effects.Visual;
import graphics.rooms.Room;
import kuusisto.tinysound.Music;
import logic.Global;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.StatesMachine;
import logic.characters.DestroyableBlock;
import logic.characters.Enemy;
import logic.characters.Player;
import logic.characters.Stairs;
import logic.collisions.Point2D;
import logic.misc.Level;
import logic.misc.Map;
import logic.misc.objectives.GetToTheStairsObjective;
import logic.misc.objectives.Objective;
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
	protected graphics.d3.objetos.Objeto plano;
	protected Level level;

	private long seconds;
	private long secondsVictory;

	private enum STATE {
		INIT, GAME, DESTRUCTION, VICTORY
	};

	private STATE state;

	private Timer timer;
	private TimerTask task;

	private final long SECONDS_PHASE = 180; // TODO

	private Objective objective;

	private Sprite hud;
	private Sprite victory;
	private Visual victoryVisual;

	private Music intro = null;
	private Music defeat = null;
	private Music victoryMsc = null;
	private boolean startedLevel = false;
	private boolean defeatedMusic = false;

	/**
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @param n
	 *            name
	 */
	public Game(int w, int h, String n) {
		super(w, h, n);

		this.enemiesDestroyed = 0;
		this.blocksDestroyed = 0;
	}

	@Override
	public void load() {
		STAGE stage = Global.levels.actualLevel().getStage();
		this.objective = Global.levels.actualLevel().getObjective();

		GameRepository.load(stage);

		this.background = GameRepository.background;
		
		tiles = GameRepository.tiles;
		plano = Game3DRepository.plano;

		state = STATE.INIT;
		seconds = SECONDS_PHASE;
		secondsVictory = -1;

		List<Objeto> objetos = Map.generateMap(this);

		for (Objeto obj : objetos) {
			if (obj instanceof Level) {
				level = (Level) obj;
			} else {
				addObjeto(obj);
			}
			
			if(obj.d3Object != null){
				Trazador.escena.objetos.add(obj.d3Object);
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

		if (Global.is2D && level != null) {
			for (int x = level.mapInitX; x < level.mapInitX + level.mapWidth; x += tiles
					.getWidth()) {
				for (int y = level.mapInitY; y < level.mapInitY
						+ level.mapHeight; y += tiles.getHeight()) {
					if (tiles != null) {
						g.drawImage(tiles.getSubsprites()[0],
								x - tiles.getCenterX(), y - tiles.getCenterY(),
								null);
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
			if(Global.is2D){
				super.render(g);
			}
			else{
				Trazador.work();
			}

			// transparency
			Color transparent = new Color(0, 0, 0, 128);
			g.setColor(transparent);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.black);

			Point2D initial_position = new Point2D(width / 2
					- Initialization.ALPHANUM_WIDTH / 2, height / 2
					+ Initialization.ALPHANUM_HEIGHT);
			String stage = "Stage";
			Point2D initial_position_text = new Point2D(width / 2
					- (Initialization.ALPHANUM_WIDTH * stage.length()) / 2,
					height / 2 - Initialization.ALPHANUM_HEIGHT);
			int lvl = Global.levels.level() + 1;
			PaintService.paintTextColor(stage, initial_position_text, g,
					PaintService.DIGITS_ORANGE.getRGB());
			PaintService.paintDigits("" + lvl, initial_position, g);
			break;
		default:
			if(Global.is2D){
				super.render(g);
			}
			else{
				Trazador.work();
			}
			break;
		}
	}

	/**
	 * Draws the HUD, including the timer
	 * 
	 * @param g
	 *            graphics to paint into
	 */
	private void drawHUD(Graphics g) {
		g.drawImage(hud.getSubsprites()[0], 0, 0, null);

		int x = (width / 2) - (32 * 5 / 2);
		int y = ((124 - 32 / 2) / 2) - (32 / 2);
		Point2D init_pos = new Point2D(x, y);

		PaintService.paintDigits(ConvertTimeService.timeToString(seconds),
				init_pos, g);
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

			if (key == KEY.PAUSE && secondsVictory < 0) {
				// Pause menu being persistent
				StatesMachine.goToRoom(StatesMachine.STATE.PAUSE, true);
			} else if (checkTime()) {
				callForDestruction();
			} else if (objective.test(this)) {
				if (!noStairs()) {
					Objeto stairs = null;
					for (Objeto obj : objetos) {
						if (obj instanceof Stairs) {
							stairs = obj;
						}
					}
					if (stairs != null) {
						stairs.destroy();
					}
				}
				callForVictory();
			} else if (objective.gameOver(this)) {
				callForDestruction();
			} else if (objective instanceof GetToTheStairsObjective
					&& noStairs()) {
				GetToTheStairsObjective gttso = (GetToTheStairsObjective) objective;
				if (gttso.noMoreEnemies(this)) {
					Point2D position = Map.randomPosition(this);

					Stairs stairs = new Stairs(position.getX(),
							position.getY(), 0, this);
					addObjeto(stairs);
				}
			}
			setTimer();

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
			if (objective.gameOver(this)) {
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
					if (!player.sprite_index.equals(GameRepository.player
							.get(Initialization.BOMBERMAN_SPRS[5]))) {
						player.sprite_index = GameRepository.player
								.get(Initialization.BOMBERMAN_SPRS[5]);

					}
					player.step(KEY.NO_KEY, KEY.NO_KEY);
				}
			}

			if (victoryMsc.done()) {
				Global.levels.nextLevel();
				terminate();
				if(Global.levels.continueLevel()){
					StatesMachine.goToRoom(StatesMachine.STATE.GAME, false);
				}
				else{
					StatesMachine.goToRoom(StatesMachine.STATE.TOP10, false);
				}
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

	/**
	 * Calls for destruction
	 */
	public void callForDestruction() {
		state = STATE.DESTRUCTION;
		cancelTimer();
		for (Objeto obj : objetos) {
			if (obj instanceof Player) {
				Player player = (Player) obj;
				player.callForDestruction();
			}
		}
	}

	/**
	 * Calls for victory
	 */
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

	/**
	 * Check if there is no stairs
	 * 
	 * @return true if there is no stairs, false otherwise
	 */
	private boolean noStairs() {
		for (Objeto obj : objetos) {
			if (obj instanceof Stairs) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Terminates the game, updating the score
	 */
	private void terminate() {
		if (state != STATE.DESTRUCTION) {
			Global.scoreManager.updateScoreSeconds(seconds);
		}
		Global.scoreManager.updateScoreEnemies(enemiesDestroyed);
		Global.scoreManager.updateScoreBlocks(blocksDestroyed);
	}

	/**
	 * Sets the primary timer
	 */
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

	/**
	 * @return true if there is no more time left, false otherwise
	 */
	private boolean checkTime() {
		return (seconds <= 0);
	}

	/**
	 * Cancels the timer
	 */
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
