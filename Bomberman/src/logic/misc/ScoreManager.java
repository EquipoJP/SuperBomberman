package logic.misc;

public class ScoreManager {

	private int score;
	
	private final double VALUE_SECOND = 1;
	private final double VALUE_ENEMY = 1;
	private final double VALUE_BLOCK = 1;

	public ScoreManager() {
		score = 0;
	}

	public void updateScoreSeconds(long secondsLeft) {
		score = (int) (score + (secondsLeft * VALUE_SECOND));
	}

	public void updateScoreEnemies(int enemiesDestroyed) {
		score = (int) (score + (enemiesDestroyed * VALUE_ENEMY));
	}
	
	public void updateScoreBlocks(int blocksDestroyed){
		score = (int) (score + (blocksDestroyed * VALUE_BLOCK));
	}

	public Record record() {
		return new Record(score);
	}
	
	public void reset(){
		score = 0;
	}

}
