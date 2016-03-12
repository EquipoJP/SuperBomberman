/**
 * Class representing the Super-Bomber game mode screen
 */
package graphics.D2.rooms;

import logic.Global;
import logic.Objeto;
import logic.characters.Enemy;
import main.Initialization.STAGE;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class SB_Game extends Game {
	
	private int enemiesDestroyed;
	
	public SB_Game(int w, int h, String n, String file, STAGE stage) {
		super(w, h, n, file, stage);
		enemiesDestroyed = 0;

		System.out.println("SB_GAME");
	}
	
	@Override
	public void destroy(Objeto o) {
		super.destroy(o);
		
		if(o instanceof Enemy){
			enemiesDestroyed++;
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		Global.scoreManager.updateScore(enemiesDestroyed);
		Global.scoreManager.updateScore(seconds);
	}

}