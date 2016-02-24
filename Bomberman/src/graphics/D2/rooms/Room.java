/**
 * Interface for the definition of the 'room' concept (from GameMaker)
 */
package graphics.D2.rooms;

import java.awt.Graphics;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public interface Room {

	public int width = 0;
	public int height = 0;
	
	/**
	 * Method to process a key in the actual state of the room/screen
	 * 
	 * @param key
	 *            key to be processed
	 */
	public void action(int key);

	/**
	 * Method to paint the screen
	 * 
	 * @param g
	 *            graphics section to paint
	 */
	public void render(Graphics g);
	
	/**
	 * Method to process a step in the actual room
	 * 
	 * @param g
	 *            graphics section to paint
	 */
	public void step();
}
