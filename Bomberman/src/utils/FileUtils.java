package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sound.MusicRepository;

public class FileUtils {
	
	public static File getFile(String file) {
	    try {
	        InputStream in = MusicRepository.class.getClassLoader().getResourceAsStream(file);
	        if (in == null) {
	            return null;
	        }

	        File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
	        tempFile.deleteOnExit();

	        try (FileOutputStream out = new FileOutputStream(tempFile)) {
	            //copy stream
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = in.read(buffer)) != -1) {
	                out.write(buffer, 0, bytesRead);
	            }
	        }
	        return tempFile;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public static int numberOfFiles(String folder, String initFile, String endFile){
		int numberOfFiles = 0;
		boolean nullValue = false;
		
		while (!nullValue){
			InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(folder + initFile + numberOfFiles + endFile);
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				numberOfFiles++;
			}
			else{
				nullValue = true;
			}
		}
		return numberOfFiles;
	}
}
