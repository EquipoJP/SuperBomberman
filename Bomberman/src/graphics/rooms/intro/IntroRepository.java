package graphics.rooms.intro;

import logic.Sprite;
import main.Initialization;

public class IntroRepository {
	
	static Sprite logo = null;
	
	public static void load(){
		loadLogo();
	}
	
	private static void loadLogo(){
		if(logo == null){
			logo = Initialization.getSpriteFromSprites("LOGO");
		}
	}

}
