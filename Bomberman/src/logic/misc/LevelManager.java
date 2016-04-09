/**
 * Class that manages the levels on the game
 */
package logic.misc;

import logic.misc.objectives.GetToTheStairsObjective;
import logic.misc.objectives.KillEnemiesObjective;
import main.Initialization.STAGE;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class LevelManager {

	private LevelFile[] files;
	private int level;
	private final int NO_LEVELS = 10;

	/**
	 * Initializes the levels
	 */
	public LevelManager() {
		level = 0;
		files = new LevelFile[NO_LEVELS];

		files[0] = new LevelFile(STAGE.GREENVILLAGE, new KillEnemiesObjective());
		files[1] = new LevelFile(STAGE.GREENVILLAGE, new KillEnemiesObjective());
		files[2] = new LevelFile(STAGE.GREENVILLAGE, new KillEnemiesObjective());
		files[3] = new LevelFile(STAGE.GREENVILLAGE, new KillEnemiesObjective());

		files[4] = new LevelFile(STAGE.GREENVILLAGE,
				new GetToTheStairsObjective());

		files[5] = new LevelFile(STAGE.PEACETOWN, new KillEnemiesObjective());
		files[6] = new LevelFile(STAGE.PEACETOWN, new KillEnemiesObjective());
		files[7] = new LevelFile(STAGE.PEACETOWN, new KillEnemiesObjective());
		files[8] = new LevelFile(STAGE.PEACETOWN, new KillEnemiesObjective());

		files[9] = new LevelFile(STAGE.PEACETOWN, new GetToTheStairsObjective());

	}

	/**
	 * Get to the next level
	 */
	public void nextLevel() {
		level = (level + 1) % NO_LEVELS;
	}

	/**
	 * Resets the level
	 */
	public void resetLevel() {
		level = 0;
	}

	/**
	 * @return number of the level
	 */
	public int level() {
		return level;
	}

	/**
	 * @return information of the level
	 */
	public LevelFile actualLevel() {
		return files[level];
	}

}
