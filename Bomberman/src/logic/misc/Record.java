/**
 * Class representing a record
 */
package logic.misc;

import java.io.Serializable;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Record implements Serializable {

	private static final long serialVersionUID = -6830830726234208232L;

	private int score;

	/**
	 * Creates a new record from a score
	 * 
	 * @param score
	 */
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
