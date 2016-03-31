package sound;

import java.io.File;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class MusicRepository {
	
	public static Music battle = null;
	public static Music credits = null;
	public static Music gameOver = null;
	public static Music menu = null;
	public static Music pause = null;
	
	public static Sound defeat = null;
	public static Sound intro = null;
	public static Sound victory = null;
	
	public static void load(){
		loadBattleMusic();
		loadCreditsMusic();
		loadGameOverMusic();
		loadMenuMusic();
		loadPauseMusic();
		
		loadDefeatSound();
		loadIntroSound();
		loadVictorySound();
	}

	private static void loadBattleMusic() {
		if(battle == null){
			battle = TinySound.loadMusic(new File(SoundTrack.BATTLE_MUSIC));
		}
	}
	
	private static void loadCreditsMusic() {
		if(credits == null){
			credits = TinySound.loadMusic(new File(SoundTrack.CREDITS_MUSIC));
		}
	}
	
	private static void loadGameOverMusic() {
		if(gameOver == null){
			gameOver = TinySound.loadMusic(new File(SoundTrack.GAMEOVER_MUSIC));
		}
	}
	
	private static void loadMenuMusic() {
		if(menu == null){
			menu = TinySound.loadMusic(new File(SoundTrack.MENU_MUSIC));
		}
	}
	
	private static void loadPauseMusic() {
		if(pause == null){
			pause = TinySound.loadMusic(new File(SoundTrack.PAUSE_MUSIC));
		}
	}
	
	private static void loadDefeatSound() {
		if(defeat == null){
			defeat = TinySound.loadSound(new File(SoundTrack.DEFEAT_SND));
		}
	}
	
	private static void loadIntroSound() {
		if(intro == null){
			intro = TinySound.loadSound(new File(SoundTrack.INTRO_SND));
		}
	}
	
	private static void loadVictorySound() {
		if(victory == null){
			victory = TinySound.loadSound(new File(SoundTrack.VICTORY_SND));
		}
	}

}
