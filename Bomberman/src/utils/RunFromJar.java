package utils;

import java.net.URL;

public class RunFromJar {
	
	private static final String JAR = "jar";
	private static final String RSRC = "rsrc";
	private static final String FILE = "file";
	
	public static boolean runningFromJar() {
		URL url = RunFromJar.class.getResource("RunFromJar.class");
		String protocol = url.getProtocol();
		
		if(protocol.equalsIgnoreCase(FILE)){
			return false;
		}
		else if(protocol.equalsIgnoreCase(JAR) || protocol.equalsIgnoreCase(RSRC)){
			return true;
		}
		else{
			return false;
		}
	}

}
