package logic;

import kuusisto.tinysound.TinySound;
import logic.misc.Ranking;
import logic.misc.ScoreManager;
import utils.SaveSystemService;

public class Global {

	public static final int EFFECTS_DEPTH = -3000;
	public static final boolean DEBUG = true;
	public static final String SAVE = "savefile.sav";
	public static ScoreManager scoreManager;
	
	public static Ranking ranking;
	
	public static void startGame(){
		createRanking();
		TinySound.init();
		scoreManager = new ScoreManager();
	}
	
	public static void stopGame(){
		saveRanking();
		TinySound.shutdown();
	}
	
	public static void createRanking(){
		// Get the ranking from the save file or create one new
		ranking = SaveSystemService.load(SAVE);
	}
	
	public static void saveRanking(){
		SaveSystemService.save(ranking, SAVE);
	}
}
