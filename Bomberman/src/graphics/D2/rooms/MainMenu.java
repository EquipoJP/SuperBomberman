/**
 * Class representing the main menu screen
 */
package graphics.D2.rooms;

import java.awt.Graphics;
import java.util.List;

import logic.Input.KEY;
import logic.Objeto;
import logic.StatesMachine;
import logic.StatesMachine.STATE;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class MainMenu extends Room{

	public MainMenu(int w, int h, String n, List<Objeto> objs) {
		super(w, h, n);
		// TODO Auto-generated constructor stub
		System.out.println("MAIN MENU");
	}

	@Override
	public void drawBackground(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0, 0, width, height);
	}

	@Override
	public void step(KEY key) {
		// TODO Auto-generated method stub
		StatesMachine.goToRoom(STATE.SB_MODE);
	}

}
