/**
 * Class representing the Super-Bomber game mode screen
 */
package graphics.D2.rooms;

import java.awt.Graphics;

import logic.Input.KEY;
import logic.characters.BlueDoll;
import logic.characters.PinkDoll;
import logic.characters.Player;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class SB_Game extends Game{

	public SB_Game(int w, int h, String n) {
		super(w, h, n);
		// TODO Auto-generated constructor stub
		addObjeto(new Player(200, 200, this, 0));
		addObjeto(new PinkDoll(400, 400, this));
		addObjeto(new BlueDoll(100, 400, this));
		System.out.println("SB_GAME");
	}

	@Override
	public void drawBackground(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0, 0, width, height);
	}

	@Override
	public void step(KEY key) {
		// TODO Auto-generated method stub
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).step(key);
		}
	}

}
