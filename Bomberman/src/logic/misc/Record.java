package logic.misc;

import java.io.Serializable;

public class Record implements Serializable{

	private static final long serialVersionUID = -6830830726234208232L;
	
	private int score;

	public Record(int score) {
		this.score = score;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

}
