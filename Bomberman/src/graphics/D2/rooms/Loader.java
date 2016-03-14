package graphics.D2.rooms;

public class Loader implements Runnable{
	
	private Room room;
	
	public Loader(Room room){
		this.room = room;
	}

	@Override
	public void run() {
		System.out.println("Running");
		room.load();
		System.out.println("Done");
	}

}
