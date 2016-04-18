/**
 * Interface for the definition of the 'room' concept (from GameMaker)
 */
package graphics.rooms;

import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import graphics.effects.Visual;
import kuusisto.tinysound.Music;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.misc.ObjetoComparator;
import main.Game;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public abstract class Room {

	public Game g;
	public int width;
	public int height;
	public String name;
	public List<Objeto> objetos;

	public Visual loadSymbol;

	private Music music;
	public Sprite background;
	
	private Timer timerInit;
	private long seconds;

	protected Thread loader = null;

	/**
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @param n
	 *            name
	 */
	public Room(int w, int h, String n) {
		width = w;
		height = h;

		background = null;

		name = n;
		objetos = new LinkedList<Objeto>();
		
		seconds = 0;
		timerInit = new Timer();
		timerInit.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				seconds++;
			}
		}, 0, (1 * 1000));

		loader = null;
		loadResources();
	}

	/**
	 * @param music
	 *            music to play
	 * @param replay
	 *            replay or sound just once
	 */
	public void setMusic(Music music, boolean replay) {
		this.music = music;
		this.music.play(replay);
	}

	/**
	 * Stops the music
	 */
	public void stopMusic() {
		this.music.stop();
	}

	/**
	 * Adds an object to the room
	 * 
	 * @param o
	 *            object to add
	 */
	public void addObjeto(Objeto o) {
		objetos.add(o);
		Collections.sort(objetos, new ObjetoComparator());
	}

	/**
	 * Destroy and object
	 * 
	 * @param o
	 *            object to destroy
	 */
	public void destroy(Objeto o) {
		objetos.remove(o);
		Collections.sort(objetos, new ObjetoComparator());
	}

	/**
	 * Load resources
	 */
	public void loadResources() {
		if (loader == null) {
			LoaderRepository.load();
			loadSymbol = new Visual(width / 2, height / 2, this,
					LoaderRepository.loading);

			loader = new Thread(new Loader(this));
			loader.start();
		}
	}

	public abstract void load();

	/**
	 * @return true if the loading is complete
	 */
	public boolean loadComplete() {
		if (loader != null) {
			return !loader.isAlive();
		} else {
			return true;
		}
	}

	/**
	 * Method to paint the screen
	 * 
	 * @param g
	 *            graphics section to paint
	 */
	public void render(Graphics g) {
		if (loadComplete()) {
			drawBackground(g);
			Collections.sort(objetos, new ObjetoComparator());
			for (int i = 0; i < objetos.size(); i++) {
				Objeto o = objetos.get(i);
				o.render(g);
			}
		} else {
			if(seconds > 0){
				timerInit.cancel();
				g.clearRect(0, 0, width, height);
				loadSymbol.render(g);
			}
		}
	}

	public abstract void drawBackground(Graphics g);

	/**
	 * Method to process a step in the actual room
	 * 
	 * @param key
	 *            key
	 * @param direction
	 *            direction
	 */
	public void step(KEY key, KEY direction) {
		if (!loadComplete()) {
			return;
		}
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).step(key, direction);
		}
	}

	/**
	 * Destroy the room
	 */
	public void destroy() {
		if (music != null) {
			music.stop();
		}
	}

	/**
	 * Pause the room
	 */
	public void pause() {
		if (music != null) {
			music.pause();
		}
	}

	/**
	 * Resume the room
	 */
	public void resume() {
		if (music != null) {
			music.resume();
		}
	}

}
