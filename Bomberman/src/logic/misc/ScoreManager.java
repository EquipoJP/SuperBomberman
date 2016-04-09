/**
 * Class for the management of the player's score in one gameplay
 */
package logic.misc;

public class ScoreManager {

	private int score;

	private final double VALUE_SECOND = 1;
	private final double VALUE_ENEMY = 1;
	private final double VALUE_BLOCK = 1;

	/**
	 * Creates a new score manager
	 */
	public ScoreManager() {
		score = 0;
	}

	/**
	 * Updates the score with the leftover seconds of the level (only when
	 * winning)
	 * 
	 * @param secondsLeft
	 *            seconds left on the level after winning
	 */
	public void updateScoreSeconds(long secondsLeft) {
		score = (int) (score + (secondsLeft * VALUE_SECOND));
	}

	/**
	 * Updates the score with the enemies destroyed by the player
	 * 
	 * @param enemiesDestroyed
	 *            number of enemies destroyed by the player
	 */
	public void updateScoreEnemies(int enemiesDestroyed) {
		score = (int) (score + (enemiesDestroyed * VALUE_ENEMY));
	}

	/**
	 * Updates the score with the destroyable blocks destroyed by the player
	 * 
	 * @param blocksDestroyed
	 *            number of destroyable block destroyed by the player
	 */
	public void updateScoreBlocks(int blocksDestroyed) {
		score = (int) (score + (blocksDestroyed * VALUE_BLOCK));
	}

	/**
	 * @return new record from the current score
	 */
	public Record record() {
		return new Record(score);
	}

	/**
	 * Resets the score to 0
	 */
	public void reset() {
		score = 0;
	}

}
