package sound;

import java.io.File;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

public class MusicRepository {
	
	public static Music battle = null;
	
	public static void load(){
		loadBattleMusic();
	}

	private static void loadBattleMusic() {
		if(battle == null){
			battle = TinySound.loadMusic(new File(SoundTrack.BATTLE_MUSIC));
		}
	}

}
