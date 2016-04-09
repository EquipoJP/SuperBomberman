/**
 * Class containing methods for the acess, read and write of ini files (based on ini4j jar)
 */
package utils;

import java.io.IOException;
import java.io.InputStream;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class IniUtils {

	/**
	 * Method to get the value of a term on a specific section of the ini file
	 * 
	 * @param file
	 *            path to the ini file
	 * @param section
	 *            section to look for the term
	 * @param term
	 *            term to look
	 * @return value of the term
	 */
	public static String getValue(String file, String section, String term) {
		String value = null;
		InputStream is = IniUtils.class.getClassLoader().getResourceAsStream(file);
		try {
			Ini ini = new Ini(is);
			Section iniSection = ini.get(section);
			value = iniSection.get(term);

		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * Method to add a value for a term on a section on the ini file
	 * 
	 * @param file
	 *            ini file
	 * @param section
	 *            section within the ini file
	 * @param term
	 *            term within the section
	 * @param value
	 *            value to put
	 */
	public static void addValue(String file, String section, String term, String value) {
		InputStream is = IniUtils.class.getClassLoader().getResourceAsStream(file);
		try {
			Ini ini = new Ini(is);
			ini.put(section, term, value);
			ini.store();

		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
