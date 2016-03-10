package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveSystemService {

	public static boolean save(Class<? extends Serializable> object, String file) {
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
	
	public static <T extends Serializable> T load (String file){
		T object = null;
		if(file.contains(".dat")){
	        try {
	            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
	            object = (T) ois.readObject();
	            ois.close();
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        }
	    }
		return object;
	}

}
