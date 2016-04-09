/**
 * Class to load the music onto the system
 */
package sound;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import utils.FileUtils;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class MusicRepository {

	public static Music battle = null;
	public static Music credits = null;
	public static Music gameOver = null;
	public static Music menu = null;
	public static Music pause = null;
	public static Music defeat = null;
	public static Music intro = null;
	public static Music victory = null;

	public static Sound explosion = null;
	public static Sound putBomb = null;
	public static Sound select = null;
	public static Sound powerup = null;

	/**
	 * Load the music onto the system
	 */
	public static void load() {
		loadBattleMusic();
		loadCreditsMusic();
		loadGameOverMusic();
		loadMenuMusic();
		loadPauseMusic();
		loadDefeatMusic();
		loadIntroMusic();
		loadVictoryMusic();

		loadSFX();
	}

	/**
	 * Unload the music from the system
	 */
	public static void unload() {
		unloadBattleMusic();
		unloadCreditsMusic();
		unloadGameOverMusic();
		unloadMenuMusic();
		unloadPauseMusic();

		unloadDefeatMusic();
		unloadIntroMusic();
		unloadVictoryMusic();
	}

	/**
	 * Load the battle music
	 */
	private static void loadBattleMusic() {
		if (battle == null) {
			battle = TinySound.loadMusic(FileUtils
					.getFile(SoundTrack.BATTLE_MUSIC));
		}
	}

	/**
	 * Load the credits music
	 */
	private static void loadCreditsMusic() {
		if (credits == null) {
			credits = TinySound.loadMusic(FileUtils
					.getFile(SoundTrack.CREDITS_MUSIC));
		}
	}

	/**
	 * Load the game over music
	 */
	private static void loadGameOverMusic() {
		if (gameOver == null) {
			gameOver = TinySound.loadMusic(FileUtils
					.getFile(SoundTrack.GAMEOVER_MUSIC));
		}
	}

	/**
	 * Load the menu music
	 */
	private static void loadMenuMusic() {
		if (menu == null) {
			menu = TinySound
					.loadMusic(FileUtils.getFile(SoundTrack.MENU_MUSIC));
		}
	}

	/**
	 * Load the pause menu music
	 */
	private static void loadPauseMusic() {
		if (pause == null) {
			pause = TinySound.loadMusic(FileUtils
					.getFile(SoundTrack.PAUSE_MUSIC));
		}
	}

	/**
	 * Load the defeat music
	 */
	private static void loadDefeatMusic() {
		if (defeat == null) {
			defeat = TinySound.loadMusic(FileUtils
					.getFile(SoundTrack.DEFEAT_MUSIC));
		}
	}

	/**
	 * Load the intro-to-stage music
	 */
	private static void loadIntroMusic() {
		if (intro == null) {
			intro = TinySound.loadMusic(FileUtils
					.getFile(SoundTrack.INTRO_MUSIC));
		}
	}

	/**
	 * Load the victory music
	 */
	private static void loadVictoryMusic() {
		if (victory == null) {
			victory = TinySound.loadMusic(FileUtils
					.getFile(SoundTrack.VICTORY_MUSIC));
		}
	}

	/**
	 * Load the sound effects
	 */
	private static void loadSFX() {
		if (explosion == null) {
			explosion = TinySound.loadSound(FileUtils
					.getFile(SoundTrack.EXPLOSION_SND));
		}

		if (putBomb == null) {
			putBomb = TinySound.loadSound(FileUtils
					.getFile(SoundTrack.PUTBOMB_SND));
		}

		if (select == null) {
			select = TinySound.loadSound(FileUtils
					.getFile(SoundTrack.SELECT_SND));
		}

		if (powerup == null) {
			powerup = TinySound.loadSound(FileUtils
					.getFile(SoundTrack.POWERUP_SND));
		}
	}

	/**
	 * Unload the battle music
	 */
	private static void unloadBattleMusic() {
		if (battle == null) {
			battle.unload();
		}
	}

	/**
	 * Unload the credits music
	 */
	private static void unloadCreditsMusic() {
		if (credits == null) {
			credits.unload();
		}
	}

	/**
	 * Unload the game over music
	 */
	private static void unloadGameOverMusic() {
		if (gameOver == null) {
			gameOver.unload();
		}
	}

	/**
	 * Unload the menu music
	 */
	private static void unloadMenuMusic() {
		if (menu == null) {
			menu.unload();
		}
	}

	/**
	 * Unload the pause music
	 */
	private static void unloadPauseMusic() {
		if (pause == null) {
			pause.unload();
		}
	}

	/**
	 * Unload the defeat music
	 */
	private static void unloadDefeatMusic() {
		if (defeat == null) {
			defeat.unload();
		}
	}

	/**
	 * Unload the intro-to-stage music
	 */
	private static void unloadIntroMusic() {
		if (intro == null) {
			intro.unload();
		}
	}

	/**
	 * Unload the victory music
	 */
	private static void unloadVictoryMusic() {
		if (victory == null) {
			victory.unload();
		}
	}
}
