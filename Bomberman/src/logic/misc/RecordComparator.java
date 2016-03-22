package logic.misc;

import java.util.Comparator;

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
