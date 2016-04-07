package logic.misc.objectives;

import graphics.rooms.Room;
import logic.Objeto;
import logic.characters.Enemy;
import logic.characters.Player;
import logic.characters.Stairs;
import logic.collisions.BoundingBox;
import logic.collisions.PerspectiveBoundingBox;

public class GetToTheStairsObjective extends Objective{

	@Override
	public boolean test(Room room) {
		return noMoreEnemies(room) && getToTheStairs(room);
	}
	
	public boolean noMoreEnemies(Room room){
		for (Objeto obj : room.objetos) {
			if (obj instanceof Enemy) {
				return false;
			}
		}
		return true;
	}
	
	public boolean getToTheStairs(Room room){
		Stairs stairs = null;
		Player player = null;
		
		for(Objeto obj : room.objetos){
			if(obj instanceof Stairs){
				stairs = (Stairs) obj;
			}
			else if(obj instanceof Player){
				player = (Player) obj;
			}
		}
		
		if(stairs == null){
			return false;
		}
		else{
			BoundingBox bb = PerspectiveBoundingBox.createBoundingBox(stairs.sprite_index);
			bb.update(stairs.x, stairs.y);
			return BoundingBox.collision(bb, player.boundingBox);
		}
	}
	
}
