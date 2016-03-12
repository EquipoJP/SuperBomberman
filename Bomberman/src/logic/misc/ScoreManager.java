package logic.misc;

public class ScoreManager {

	private int score;
	private final double VALUE_SECOND = 0.001;
	private final double VALUE_ENEMY = 0.1;

	public ScoreManager() {
		score = 0;
	}

	public void updateScore(long secondsLeft) {
		score = (int) (score + (secondsLeft * VALUE_SECOND));
	}

	public void updateScore(int enemiesDestroyed) {
		score = (int) (score + (enemiesDestroyed * VALUE_ENEMY));
	}

	public Record record() {
		return new Record(score);
	}

}
