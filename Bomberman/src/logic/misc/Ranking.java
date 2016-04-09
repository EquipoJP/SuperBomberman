package logic.misc;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Ranking implements Serializable{
	
	private static final long serialVersionUID = -8277000633749652572L;
	
	private List<Record> records;
	public static final int MAX_RECORDS = 3;
	
	public Ranking(){
		records = new LinkedList<Record>();
	}
	
	public Ranking(List<Record> records){
		this.records = records;
	}

	/**
	 * @return the records
	 */
	public List<Record> getRecords() {
		Collections.sort(records, new RecordComparator());
		return records;
	}
	
	public boolean newRecord(Record record){
		records.add(record);
		Collections.sort(records, new RecordComparator());
		
		Record discarded = null;
		if(records.size() > MAX_RECORDS){
			discarded = records.remove(records.size() - 1);
		}
		
		return !record.equals(discarded);
	}
	
}
