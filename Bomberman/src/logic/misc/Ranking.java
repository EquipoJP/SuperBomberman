package logic.misc;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Ranking implements Serializable{
	
	private static final long serialVersionUID = -8277000633749652572L;
	
	private List<Record> records;
	
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
	
}
