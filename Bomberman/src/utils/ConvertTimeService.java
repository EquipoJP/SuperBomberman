package utils;

public class ConvertTimeService {
	
	public static long SECONDS_IN_MIN = 60;
	
	public static String timeToString(long seconds){
		long minutes = seconds / SECONDS_IN_MIN;
		long secs = seconds % SECONDS_IN_MIN;
		
		return minutes + ":" + secs;
	}

}
