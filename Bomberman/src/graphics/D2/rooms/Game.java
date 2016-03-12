/**
 * Class representing the generic game screen
 */
package graphics.D2.rooms;

import java.awt.Graphics;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import logic.misc.Level;
import logic.misc.Map;
import main.Initialization;
import main.Initialization.STAGE;
import sound.SoundTrack;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public abstract class Game extends Room {
	
	protected Sprite tiles;
	protected Level level;
	protected long seconds;
	
	private Timer timer;
	private TimerTask task;
	
	private final long SECONDS_PHASE = 50;	// TODO 

	public Game(int w, int h, String n, String file, STAGE stage) {
		super(w, h, n);
		
		seconds = -1;
		
		tiles = Initialization.getSpriteFromMap(stage.toString() + "_" + Initialization.TYPE.TILE.toString());
		List<Objeto> objetos = Map.getMap(file, this, stage);

		for (Objeto obj : objetos) {
			if (obj instanceof Level) {
				level = (Level) obj;
			} else {
				addObjeto(obj);
			}
		}
		
		setMusic(SoundTrack.BATTLE_MUSIC);
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
	
	@Override
	public void step(KEY key) {
		super.step(key);
		
		setTimer();
		
		if ((key == KEY.ENTER && lastKey != KEY.ENTER) ||
				(key == KEY.ESCAPE && lastKey != KEY.ESCAPE)){
			// Pause menu being persistent
			StatesMachine.goToRoom(STATE.PAUSE, true);
		}
		
		lastKey = key;
		
		if(checkTime()){
			// TODO things to do before destroying the room
			StatesMachine.goToRoom(STATE.MAIN_MENU, false);
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		cancelTimer();
	}
	
	private boolean checkTime(){
		return (seconds <= 0);
	}
	
	private void setTimer(){
		if(seconds < 0 || timer == null || task == null){
			seconds = SECONDS_PHASE;
			
			timer = new Timer();
			
			task = new TimerTask() {
	            @Override
	            public void run() {
	            	seconds--;
	            	System.out.println("Seconds left: " + seconds);
	            }
	        };
	        
			timer.scheduleAtFixedRate(task, 0, (1 * 1000));
		}
	}
	
	private void cancelTimer(){
		task.cancel();
		timer.cancel();
	}

}
