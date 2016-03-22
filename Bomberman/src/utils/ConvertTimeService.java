package utils;

public class ConvertTimeService {
	
	public static long SECONDS_IN_MIN = 60;
	
	public static String timeToString(long seconds){
		long minutes = seconds / SECONDS_IN_MIN;
		long secs = seconds % SECONDS_IN_MIN;
		
		String result = "";
		if(minutes < 10){
			result += "0";
		}
		result += minutes + ":";
		if(secs < 10){
			result += "0";
		}
		result += secs;
		return result;
	}

}
