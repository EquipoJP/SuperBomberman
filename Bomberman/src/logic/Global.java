package logic;

import logic.misc.Ranking;
import utils.SaveSystemService;

public class Global {

	public static final int EFFECTS_DEPTH = -3000;
	public static final boolean DEBUG = true;
	public static final String SAVE = "";
	
	public static Ranking ranking;
	
	public static void stopGame(){
		saveRanking();
	}
	
	public static void createRanking(){
		// Get the ranking from the save file or create one new
		ranking = SaveSystemService.load(SAVE);
	}
	
	public static void saveRanking(){
		SaveSystemService.save(ranking, SAVE);
	}
}
