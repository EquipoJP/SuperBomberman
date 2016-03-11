package utils;

import java.io.File;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;
import sound.SoundTrack;

public class Test {
	
	public static void main(String[] args) {

		TinySound.init();
		Music music = TinySound.loadMusic(new File(SoundTrack.BATTLE_MUSIC));
		music.play(true);
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		music.unload();
		
		int mod = 5;
		for(int i = 0; i < 2*mod; i++){
			System.out.println(i%mod);
		}
		TinySound.shutdown();
	}

}
