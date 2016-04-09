/**
 * Class for converting seconds to several formats
 */
package utils;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class ConvertTimeService {

	public static long SECONDS_IN_MIN = 60;

	/**
	 * Method to pass from seconds to mm:ss format
	 * 
	 * @param seconds
	 *            seconds to transform
	 * @return string formatted like mm:ss
	 */
	public static String timeToString(long seconds) {
		long minutes = seconds / SECONDS_IN_MIN;
		long secs = seconds % SECONDS_IN_MIN;

		String result = "";
		if (minutes < 10) {
			result += "0";
		}
		
		result += minutes + ":";
		if (secs < 10) {
			result += "0";
		}
		
		result += secs;
		return result;
	}

}
