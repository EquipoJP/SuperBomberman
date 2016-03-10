package logic.misc;

public class Record {

	private String name;
	private int score;

	public Record(String name, int score) {
		this.name = name;
		this.score = score;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

}
