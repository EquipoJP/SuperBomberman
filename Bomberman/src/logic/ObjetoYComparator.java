package logic;

import java.util.Comparator;

public class ObjetoYComparator implements Comparator<Objeto>{

	@Override
	public int compare(Objeto arg0, Objeto arg1) {
		if(arg0.depth > arg1.depth){
			return -1;
		}
		if(arg0.depth < arg1.depth){
			return 1;
		}
		
		if(arg0.y > arg1.y){
			return 1;
		}
		if(arg0.y < arg1.y){
			return -1;
		}
		return 0;
	}
}
