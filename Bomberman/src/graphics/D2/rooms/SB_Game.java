/**
 * Class representing the Super-Bomber game mode screen
 */
package graphics.D2.rooms;

import logic.Input.KEY;
import main.Initialization.COLOR;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class SB_Game extends Game {

	public SB_Game(int w, int h, String n, String file, COLOR color) {
		super(w, h, n, file, color);

		System.out.println("SB_GAME");
	}

	@Override
	public void step(KEY key) {
		// TODO Auto-generated method stub
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).step(key);
		}
	}

}
