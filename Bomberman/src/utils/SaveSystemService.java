package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logic.misc.Ranking;

public class SaveSystemService {

	public static boolean save(Ranking object, String file) {
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
	
	public static Ranking load (String file){
		Ranking object = null;
		if(file.contains(".dat")){
	        try {
	            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
	            object = (Ranking) ois.readObject();
	            ois.close();
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        }
	    }
		return object;
	}

}
