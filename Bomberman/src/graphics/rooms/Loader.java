/**
 * Loader thread
 */
package graphics.rooms;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Loader implements Runnable{
	
	private Room room;
	
	/**
	 * Creates a loader for a room
	 * @param room room to load
	 */
	public Loader(Room room){
		this.room = room;
	}

	@Override
	public void run() {
		System.out.print("Running " + room.name + " loader" + "... ");
		room.load();
		System.out.println("Done loading");
	}

}
