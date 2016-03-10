/**
 * Interface for the definition of the 'room' concept (from GameMaker)
 */
package graphics.D2.rooms;

import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import logic.Input.KEY;
import logic.Objeto;
import logic.misc.ObjetoComparator;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public abstract class Room {

	public int width;
	public int height;
	public String name;
	public List<Objeto> objetos;
	
	public Room(int w, int h, String n) {
		width = w;
		height = h;
		name = n;
		objetos = new LinkedList<Objeto>();
	}
	
	public void addObjeto(Objeto o){
		objetos.add(o);
		Collections.sort(objetos,new ObjetoComparator());
	}
	
	public void destroy(Objeto o){
		objetos.remove(o);
		Collections.sort(objetos, new ObjetoComparator());
	}

	/**
	 * Method to paint the screen
	 * 
	 * @param g
	 *            graphics section to paint
	 */
	public void render(Graphics g){
		drawBackground(g);
		Collections.sort(objetos, new ObjetoComparator());
		for(int i = 0; i < objetos.size(); i++){
			Objeto o = objetos.get(i);
			o.render(g);
		}
	}
	
	/**
	 * 
	 */
	public abstract void drawBackground(Graphics g);
	
	/**
	 * Method to process a step in the actual room
	 * 
	 * @param g
	 *            graphics section to paint
	 */
	public void step(KEY key){
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).step(key);
		}
	}
	
}
