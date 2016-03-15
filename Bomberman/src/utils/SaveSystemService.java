package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logic.misc.Ranking;

public class SaveSystemService {
	
	public static final String PATH = System.getProperty("user.home") + "/Documents/SuperBomberman/";

	public static boolean save(Ranking object, String file) {
		File directory = new File(PATH);
		if(directory.exists()){
			;	// directory exists
		}
		else if(directory.mkdirs()){
			;	// directory created
		}
		else{
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
	
	public static Ranking load (String file){
		Ranking object = null;
		file = PATH + file;
		if(file.contains(".sav") && new File(file).exists()){
	        try {
	            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
	            object = (Ranking) ois.readObject();
	            ois.close();
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        }
	    }
		else{
			object = new Ranking();
		}
		return object;
	}

}
