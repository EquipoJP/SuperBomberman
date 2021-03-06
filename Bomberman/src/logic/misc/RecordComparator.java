/**
 * Class defining the logic behind the comparison of two records
 */
package logic.misc;

import java.util.Comparator;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class RecordComparator implements Comparator<Record> {

	@Override
	public int compare(Record arg0, Record arg1) {
		if (arg0.getScore() > arg1.getScore()) {
			return -1;
		}
		if (arg0.getScore() < arg1.getScore()) {
			return 1;
		}

		return 0;
	}
}
