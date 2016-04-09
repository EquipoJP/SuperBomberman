/**
 * Global variables
 */
package logic;

import kuusisto.tinysound.TinySound;
import logic.misc.LevelManager;
import logic.misc.Ranking;
import logic.misc.ScoreManager;
import sound.MusicRepository;
import utils.RunFromJar;
import utils.SaveSystemService;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Global {

	public static final int EFFECTS_DEPTH = -3000;
	public static final boolean DEBUG = !RunFromJar.runningFromJar();

	public static final String SAVE = "savefile.sav";

	public static ScoreManager scoreManager;
	public static Ranking ranking;

	public static LevelManager levels;
	public static boolean is2D;

	/**
	 * Execute at the start of the game
	 */
	public static void startGame() {
		createRanking();
		TinySound.init();

		scoreManager = new ScoreManager();
		levels = new LevelManager();
		is2D = true;
	}

	/**
	 * Execute at the end of the game
	 */
	public static void stopGame() {
		saveRanking();
		MusicRepository.unload();
		TinySound.shutdown();
	}

	/**
	 * Get the ranking from the save file or create a new one
	 */
	public static void createRanking() {
		ranking = SaveSystemService.load(SAVE);
	}

	/**
	 * Save the ranking onto the save file
	 */
	public static void saveRanking() {
		SaveSystemService.save(ranking, SAVE);
	}
}
