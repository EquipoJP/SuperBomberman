package logic.misc;

import logic.misc.objectives.Objective;
import main.Initialization.STAGE;

public class LevelFile {
	
	private STAGE stage;
	private Objective objective;
	
	public LevelFile(STAGE stg, Objective obj){
		stage = stg;
		objective = obj;
	}

	public STAGE getStage() {
		return stage;
	}
	
	public Objective getObjective(){
		return objective;
	}
}
