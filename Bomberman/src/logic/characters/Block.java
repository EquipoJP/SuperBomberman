/**
 * Class representing a block
 */
package logic.characters;

import graphics.rooms.Room;
import graphics.rooms.game.GameRepository;
import logic.Objeto;
import logic.collisions.NoPerspectiveBoundingBox;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Block extends Objeto {

	/**
	 * Creates a block
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param r
	 *            room
	 */
	public Block(int x, int y, int z, Room r, graphics.d3.objetos.Objeto d3object) {
		super(x, y, z, r);

		sprite_index = GameRepository.block;
		image_speed = 0.5;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		
		super.d3Object = d3object;
	}
}
