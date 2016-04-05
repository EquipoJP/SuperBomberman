package logic.characters;

import graphics.rooms.Room;
import logic.Objeto;
import logic.collisions.BoundingBox;
import logic.collisions.Point2D;
import main.Initialization;

public class ForceDeath extends Objeto {

	public ForceDeath(int x, int y, int z, Room r) {
		super(x, y, z, r);

		int w = (Initialization.TILE_WIDTH/2) - 5;
		int h = (Initialization.TILE_HEIGHT/2) - 5;
		Point2D min = new Point2D(- w, - h);
		Point2D max = new Point2D(w, h);
		boundingBox = new BoundingBox(min, max);
		boundingBox.update(x, y);
	}
	
	@Override
	public void customCollision(Objeto collision){
		if(collision instanceof Player){
			Player p = (Player) collision;
			p.callForDestruction();
		}
	}

}
