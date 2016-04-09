/**
 * Class defining the ranking system of the game
 */
package logic.misc;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Ranking implements Serializable {

	private static final long serialVersionUID = -8277000633749652572L;

	private List<Record> records;
	public static final int MAX_RECORDS = 3;

	/**
	 * Creates a ranking
	 */
	public Ranking() {
		records = new LinkedList<Record>();
	}

	/**
	 * Creates a ranking from a set of records
	 * 
	 * @param records
	 */
	public Ranking(List<Record> records) {
		this.records = records;
	}

	/**
	 * @return the records
	 */
	public List<Record> getRecords() {
		Collections.sort(records, new RecordComparator());
		return records;
	}

	/**
	 * Checks if the record passed as argument is on the top MAX_RECORDS
	 * 
	 * @param record
	 *            new record
	 * @return true if the record is on the top MAX_RECORDS, false otherwise
	 */
	public boolean newRecord(Record record) {
		records.add(record);
		Collections.sort(records, new RecordComparator());

		Record discarded = null;
		if (records.size() > MAX_RECORDS) {
			discarded = records.remove(records.size() - 1);
		}

		return !record.equals(discarded);
	}

}
