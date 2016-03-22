package logic.misc;

import main.Initialization.STAGE;

public class LevelFile {
	
	private String file;
	private STAGE stage;
	
	public LevelFile(String fileName, STAGE stg){
		file = fileName;
		stage = stg;
	}

	public String getFile() {
		return file;
	}

	public STAGE getStage() {
		return stage;
	}
}
