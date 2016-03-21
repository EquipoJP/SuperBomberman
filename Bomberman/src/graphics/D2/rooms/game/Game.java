/**
 * Class representing the generic game screen
 */
package graphics.D2.rooms.game;

import graphics.D2.rooms.Room;
import graphics.effects.Visual;

import java.awt.Graphics;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import logic.Global;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import logic.characters.Enemy;
import logic.characters.Player;
import logic.collisions.Point2D;
import logic.misc.Level;
import logic.misc.Map;
import main.Initialization.STAGE;
import sound.SoundTrack;
import utils.ConvertTimeService;
import utils.PaintDigitsService;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public abstract class Game extends Room {

	protected Sprite tiles;
	protected Level level;
	
	protected long seconds;
	protected long secondsVictory;

	private Timer timer;
	private TimerTask task;

	private final long SECONDS_PHASE = 50; // TODO
	private final long VICTORY_SECONDS = 5;	// TODO
	
	private String file;
	private STAGE stage;
	
	private Sprite hud;
	private Sprite victory;
	private Visual victoryVisual;

	public Game(int w, int h, String n, String file, STAGE stage) {
		super(w, h, n);
		
		this.file = file;
		this.stage = stage;
	}
	
	@Override
	public void load() {
		GameRepository.load(stage);
		seconds = -1;
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
		
		setMusic(SoundTrack.BATTLE_MUSIC);
	}

	@Override
	public void drawBackground(Graphics g) {
		if(!loadComplete()){
			return ;
		}
		g.clearRect(0, 0, width, height);
		
		drawHUD(g);

		if (level != null) {
			for (int x = level.mapInitX; x < level.mapInitX + level.mapWidth; x += tiles
					.getWidth()) {
				for (int y = level.mapInitY; y < level.mapInitY
						+ level.mapHeight; y += tiles.getHeight()) {
					if(tiles != null){
						g.drawImage(tiles.getSubsprites()[0],
								x - tiles.getCenterX(), y - tiles.getCenterY(),
								null);
					}
				}
			}
		}
	}
	
	private void drawHUD(Graphics g) {
		g.drawImage(hud.getSubsprites()[0], 0, 0, null);
		
		//TODO change values here
		int x = 180;
		int y = 40;
		Point2D init_pos = new Point2D(x, y);
		
		PaintDigitsService.paint(ConvertTimeService.timeToString(seconds), init_pos, g);
	}

	@Override
	public void step(KEY key, KEY direction) {
		super.step(key, direction);
		
		if(!loadComplete()){
			return ;
		}

		setTimer();

		if ((key == KEY.ENTER || key == KEY.ESCAPE) && secondsVictory < 0) {
			// Pause menu being persistent
			StatesMachine.goToRoom(STATE.PAUSE, true);
		}

		if (checkTime()) {
			callForDestruction();
		}
		
		if(noPlayer()){
			StatesMachine.goToRoom(STATE.MAIN_MENU, false);
		}
		
		if(noEnemies()){
			callForVictory();
			if(secondsVictory < 0){
				StatesMachine.goToRoom(STATE.MAIN_MENU, false);
			}
		}
	}
	
	private boolean noPlayer(){
		for(Objeto obj : objetos){
			if(obj instanceof Player){
				return false;
			}
		}
		return true;
	}
	
	private boolean noEnemies(){
		for(Objeto obj : objetos){
			if(obj instanceof Enemy){
				return false;
			}
		}
		return true;
	}

	@Override
	public void destroy() {
		super.destroy();
		cancelTimer();
	}

	private boolean checkTime() {
		return (seconds <= 0);
	}

	private void setTimer() {
		if(!loadComplete()){
			return ;
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
//					System.out.println("Seconds left: " + seconds);
				}
			};

			timer.scheduleAtFixedRate(task, 0, (1 * 1000));
		}
	}

	private void cancelTimer() {
		if(timer != null){
			timer.cancel();
		}
		if(task != null){
			task.cancel();
		}
		timer = null;
		task = null;
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
	
	public void callForDestruction(){
		cancelTimer();
		for(Objeto obj : objetos){
			if(obj instanceof Player){
				Player player = (Player) obj;
				player.callForDestruction();
			}
		}
	}
	
	public void callForVictory(){
		if(victoryVisual == null){
			cancelTimer();
			setVictoryTimer();
			victoryVisual = new Visual(width/2, height/2, this, victory);
			victoryVisual.depth = Global.EFFECTS_DEPTH;
			addObjeto(victoryVisual);
		}
	}

	private void setVictoryTimer() {
		if (secondsVictory < 0) {
			secondsVictory = VICTORY_SECONDS;
		}
		task = null;
		timer = null;
		
		timer = new Timer();

		task = new TimerTask() {
			@Override
			public void run() {
				secondsVictory--;
				System.out.println("Seconds left: " + secondsVictory);
			}
		};

		timer.scheduleAtFixedRate(task, 0, (1 * 1000));
		
	}

}
