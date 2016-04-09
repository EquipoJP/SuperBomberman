/**
 * Class representing a level with objective and theme
 */
package logic.misc;

import logic.misc.objectives.Objective;
import main.Initialization.STAGE;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class LevelFile {

	private STAGE stage;
	private Objective objective;

	/**
	 * @param stg
	 *            theme of the level
	 * @param obj
	 *            objective of the level
	 */
	public LevelFile(STAGE stg, Objective obj) {
		stage = stg;
		objective = obj;
	}

	/**
	 * @return theme of the level
	 */
	public STAGE getStage() {
		return stage;
	}

	/**
	 * @return objective of the level
	 */
	public Objective getObjective() {
		return objective;
	}
}
