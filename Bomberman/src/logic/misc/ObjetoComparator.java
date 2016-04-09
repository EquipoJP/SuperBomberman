/**
 * Class defining the logic behind the comparison between two objects
 */
package logic.misc;

import java.util.Comparator;

import logic.Objeto;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class ObjetoComparator implements Comparator<Objeto> {

	@Override
	public int compare(Objeto arg0, Objeto arg1) {
		if (arg0.depth > arg1.depth) {
			return -1;
		}
		if (arg0.depth < arg1.depth) {
			return 1;
		}

		if (arg0.y > arg1.y) {
			return 1;
		}
		if (arg0.y < arg1.y) {
			return -1;
		}
		return 0;
	}
}
