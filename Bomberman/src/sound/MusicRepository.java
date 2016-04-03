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
	public static Music defeat = null;
	public static Music intro = null;
	public static Music victory = null;
	
	public static Sound explosion = null;
	public static Sound putBomb = null;
	public static Sound select = null;
	
	public static void load(){
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
	
	public static void unload(){
		unloadBattleMusic();
		unloadCreditsMusic();
		unloadGameOverMusic();
		unloadMenuMusic();
		unloadPauseMusic();
		
		unloadDefeatSound();
		unloadIntroSound();
		unloadVictorySound();
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
	
	private static void loadDefeatMusic() {
		if(defeat == null){
			defeat = TinySound.loadMusic(new File(SoundTrack.DEFEAT_MUSIC));
		}
	}
	
	private static void loadIntroMusic() {
		if(intro == null){
			intro = TinySound.loadMusic(new File(SoundTrack.INTRO_MUSIC));
		}
	}
	
	private static void loadVictoryMusic() {
		if(victory == null){
			victory = TinySound.loadMusic(new File(SoundTrack.VICTORY_MUSIC));
		}
	}
	
	private static void loadSFX(){
		if(explosion == null){
			explosion = TinySound.loadSound(new File(SoundTrack.EXPLOSION_SND));
		}
		
		if(putBomb == null){
			putBomb = TinySound.loadSound(new File(SoundTrack.PUTBOMB_SND));
		}
		
		if(select == null){
			select = TinySound.loadSound(new File(SoundTrack.SELECT_SND));
		}
	}
	
	private static void unloadBattleMusic() {
		if(battle == null){
			battle.unload();
		}
	}
	
	private static void unloadCreditsMusic() {
		if(credits == null){
			credits.unload();
		}
	}
	
	private static void unloadGameOverMusic() {
		if(gameOver == null){
			gameOver.unload();
		}
	}
	
	private static void unloadMenuMusic() {
		if(menu == null){
			menu.unload();
		}
	}
	
	private static void unloadPauseMusic() {
		if(pause == null){
			pause.unload();
		}
	}
	
	private static void unloadDefeatSound() {
		if(defeat == null){
			defeat.unload();
		}
	}
	
	private static void unloadIntroSound() {
		if(intro == null){
			intro.unload();
		}
	}
	
	private static void unloadVictorySound() {
		if(victory == null){
			victory.unload();
		}
	}

}
