/**
 * Loader's resources
 */
package graphics.rooms;

import logic.Sprite;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class LoaderRepository {

	static Sprite background = null;
	static Sprite loading = null;

	/**
	 * Load resources
	 */
	public static void load() {
		loadBackground();
		loadLoading();
	}

	/**
	 * Load background
	 */
	private static void loadBackground() {
	}

	/**
	 * Load loading logo
	 */
	private static void loadLoading() {
		if (loading == null) {
			loading = Initialization
					.getSpriteFromMenu(Initialization.MENUS.LOADING.toString());
		}
	}
}
