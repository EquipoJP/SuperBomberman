/**
 * Class to check if the program is running from JAR or from file
 */
package utils;

import java.net.URL;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class RunFromJar {

	private static final String JAR = "jar";
	private static final String RSRC = "rsrc";
	private static final String FILE = "file";

	/**
	 * Method to check if the program is running from JAR or from file
	 * 
	 * @return true if it's running from jar, false otherwise
	 */
	public static boolean runningFromJar() {
		URL url = RunFromJar.class.getResource("RunFromJar.class");
		String protocol = url.getProtocol();

		if (protocol.equalsIgnoreCase(FILE)) {
			return false;
		} else if (protocol.equalsIgnoreCase(JAR)
				|| protocol.equalsIgnoreCase(RSRC)) {
			return true;
		} else {
			return false;
		}
	}

}
