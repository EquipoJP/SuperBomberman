/**
 * Class to get various resources from filesystem and from jar
 */
package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sound.MusicRepository;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class FileUtils {

	/**
	 * Gets the file referenced by the path @param file, creating a temp file
	 * that will be deleted on exit
	 * 
	 * @param file
	 *            path to the file
	 * @return file
	 */
	public static File getFile(String file) {
		try {
			InputStream in = MusicRepository.class.getClassLoader()
					.getResourceAsStream(file);
			if (in == null) {
				return null;
			}

			// creating temp file
			File tempFile = File.createTempFile(String.valueOf(in.hashCode()),
					".tmp");
			tempFile.deleteOnExit();

			// write to temp file
			try (FileOutputStream out = new FileOutputStream(tempFile)) {
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

	/**
	 * Method to get the number of files on a folder with format initFile NUMBER
	 * endFile
	 * 
	 * @param folder
	 *            folder in which count the number of files
	 * @param initFile
	 *            start name part of the file
	 * @param endFile
	 *            end name part of the file
	 * @return the number of consecutive numbered files on the folder
	 */
	public static int numberOfFiles(String folder, String initFile,
			String endFile) {
		
		int numberOfFiles = 0;
		boolean nullValue = false;

		while (!nullValue) {
			// get next file
			InputStream is = FileUtils.class.getClassLoader()
					.getResourceAsStream(
							folder + initFile + numberOfFiles + endFile);
			
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				numberOfFiles++;
			} else {
				nullValue = true;
			}
		}
		return numberOfFiles;
	}
}
