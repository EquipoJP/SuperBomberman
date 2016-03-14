/**
 * Interface for the definition of the 'room' concept (from GameMaker)
 */
package graphics.D2.rooms;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;
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
	
	private Music music;
	
	protected Thread loader = null;
	
	public Room(int w, int h, String n) {
		width = w;
		height = h;
		name = n;
		objetos = new LinkedList<Objeto>();
		
		loader = null;
		loadResources();
	}
	
	public void setMusic(String filename){
		music = TinySound.loadMusic(new File(filename));
		music.play(true);
	}
	
	public void addObjeto(Objeto o){
		objetos.add(o);
		Collections.sort(objetos,new ObjetoComparator());
	}
	
	public void destroy(Objeto o){
		objetos.remove(o);
		Collections.sort(objetos, new ObjetoComparator());
	}
	
	public void loadResources(){
		if (loader == null){
			loader = new Thread(new Loader(this));
			loader.start();
		}
	}
	
	public abstract void load();
	
	public boolean loadComplete(){
		if(loader != null){
			return !loader.isAlive();
		}
		else{
			return true;
		}
	}

	/**
	 * Method to paint the screen
	 * 
	 * @param g
	 *            graphics section to paint
	 */
	public void render(Graphics g){
		if(loadComplete()){
			drawBackground(g);
			Collections.sort(objetos, new ObjetoComparator());
			for(int i = 0; i < objetos.size(); i++){
				Objeto o = objetos.get(i);
				o.render(g);
			}
		}
		else{
			// TODO
			g.setColor(Color.red);
			g.drawRect(0, 0, 100, 100);
			g.setColor(Color.black);
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
	public void step(KEY key, KEY direction){
		if(!loadComplete()){
			return ;
		}
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).step(key, direction);
		}
	}
	
	public void destroy(){
		if(music != null){
			music.stop();
			music.unload();
		}
		objetos = null;
	}
	
	public void pause(){
		if(music != null){
			music.pause();
		}
	}
	
	public void resume(){
		if(music != null){
			music.resume();
		}
	}
	
}
