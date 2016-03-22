package logic.misc;

import main.Initialization;

public class LevelManager {
	
	private LevelFile[] files;
	private int level;
	private final int NO_LEVELS = 10;
	
	public LevelManager(){
		level = 0;
		files = new LevelFile[NO_LEVELS];
		
		for(int i = 0; i < files.length; i++){
			files[i] = new LevelFile("maps/level1.txt", Initialization.STAGE.GREENVILLAGE);
		}
	}
	
	public void nextLevel(){
		level = (level++) % NO_LEVELS;
	}
	
	public int level(){
		return level;
	}
	
	public LevelFile actualLevel(){
		return files[level];
	}

}
