/**
 * Class containing the game loop
 */
package main;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class GameLoop implements Runnable {
	
	boolean stoped;

	/**
	 * Creates the GameLoop object
	 */
	public GameLoop() {
		stoped = false;
	}

	/**
	 * Game loop of the game. It will do the following: update characters,
	 * update environment, repaint the scene and wait X miliseconds
	 */
	@Override
	public void run() {
		while(!stoped){
			// update characters
			
			// update environment
			
			// repaint scene
			
			// wait 17 miliseconds (60 fps) -> 1.000 / 60
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
