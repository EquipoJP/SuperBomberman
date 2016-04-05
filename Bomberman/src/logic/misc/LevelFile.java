package logic.misc;

import logic.misc.objectives.Objective;
import main.Initialization.STAGE;

public class LevelFile {
	
	private String file;
	private STAGE stage;
	private Objective objective;
	
	public LevelFile(String fileName, STAGE stg, Objective obj){
		file = fileName;
		stage = stg;
		objective = obj;
	}

	public String getFile() {
		return file;
	}

	public STAGE getStage() {
		return stage;
	}
	
	public Objective getObjective(){
		return objective;
	}
}
