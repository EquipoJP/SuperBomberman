/**
 * Class to save and load savegames
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logic.misc.Ranking;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class SaveSystemService {

	public static final String PATH = System.getProperty("user.home")
			+ "/Documents/SuperBomberman/";

	/**
	 * Method to save the ranking onto a file
	 * 
	 * @param object
	 *            ranking to save
	 * @param file
	 *            file in which the object will be stored
	 * @return true if everything goes right, false otherwise
	 */
	public static boolean save(Ranking object, String file) {
		File directory = new File(PATH);
		if (directory.exists()) {
			; // directory exists
		} else if (directory.mkdirs()) {
			; // directory created
		} else {
			return false;
		}

		file = PATH + file;

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(file));
			oos.writeObject(object);
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Method to load from file or create a ranking object
	 * 
	 * @param file
	 *            file to load the ranking
	 * @return a ranking object
	 */
	public static Ranking load(String file) {
		Ranking object = null;
		file = PATH + file;
		if (file.contains(".sav") && new File(file).exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(file));
				object = (Ranking) ois.readObject();
				ois.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			object = new Ranking();
		}
		return object;
	}

}
