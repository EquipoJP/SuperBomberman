/**
 * Interface for the definition of the 'room' concept (from GameMaker)
 */
package graphics.D2.rooms;

import graphics.effects.Visual;

import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import kuusisto.tinysound.Music;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
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
	
	public Visual loadSymbol;
	
	private Music music;
	public Sprite background;
	
	protected Thread loader = null;
	
	public Room(int w, int h, String n) {
		width = w;
		height = h;
		
		background = null;
		
		name = n;
		objetos = new LinkedList<Objeto>();
		
		loader = null;
		loadResources();
	}
	
	public void setMusic(Music music){
		this.music = music;
		this.music.play(true);
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
			LoaderRepository.load();
			loadSymbol = new Visual(width / 2, height / 2, this, LoaderRepository.loading);
			
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
			g.clearRect(0, 0, width, height);
			loadSymbol.render(g);
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
