package graphics.D2.rooms;

import logic.Sprite;
import main.Initialization;

public class LoaderRepository {
	
	static Sprite background = null;
	static Sprite loading = null;

	public static void load(){
		loadBackground();
		loadLoading();
	}

	private static void loadBackground() {
		// TODO Auto-generated method stub
		
	}

	private static void loadLoading() {
		if(loading == null){
			loading = Initialization.getSpriteFromMenu(Initialization.MENUS.LOADING.toString());
		}
	}
}
