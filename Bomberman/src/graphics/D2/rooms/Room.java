/**
 * Interface for the definition of the 'room' concept (from GameMaker)
 */
package graphics.D2.rooms;

import java.awt.Graphics;
import java.util.Collections;
import java.util.List;

import logic.Objeto;
import logic.ObjetoComparator;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public abstract class Room {

	public int width;
	public int height;
	public String name;
	public List<Objeto> objetos;
	
	public Room(int w, int h, String n, List<Objeto> objs) {
		width = w;
		height = h;
		name = n;
	}
	
	public void addObjeto(Objeto o){
		objetos.add(o);
		Collections.sort(objetos,new ObjetoComparator());
	}
	
	/**
	 * Method to process a key in the actual state of the room/screen
	 * 
	 * @param key
	 *            key to be processed
	 */
	public abstract void action(int key);

	/**
	 * Method to paint the screen
	 * 
	 * @param g
	 *            graphics section to paint
	 */
	public abstract void render(Graphics g);
	
	/**
	 * Method to process a step in the actual room
	 * 
	 * @param g
	 *            graphics section to paint
	 */
	public abstract void step();
}
