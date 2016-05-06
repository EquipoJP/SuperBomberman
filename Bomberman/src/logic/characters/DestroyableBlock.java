/**
 * Class representing a destroyable block
 */
package logic.characters;

import graphics.rooms.Room;
import graphics.rooms.game.Game3DRepository;
import graphics.rooms.game.GameRepository;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.characters.Item.TYPE;
import logic.collisions.NoPerspectiveBoundingBox;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class DestroyableBlock extends Objeto {

	private Sprite destroyed;
	private boolean destruction;
	private TYPE type;

	/**
	 * Creates a destroyable block
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param r
	 *            room
	 * @param t
	 *            type of destroyable block
	 */
	public DestroyableBlock(int x, int y, int z, Room r, TYPE t, graphics.d3.objetos.Objeto d3Object) {
		super(x, y, z, r);

		sprite_index = GameRepository.destroyableBlock1;
		image_speed = 0.2;

		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);

		destroyed = GameRepository.destroyableBlock2;
		destruction = false;

		type = t;
		
		super.d3Object = d3Object;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if (destruction) {
			if (sprite_index != destroyed) {
				sprite_index = destroyed;
				image_index = 0;
				resetAnimationEnd();
			} else {
				if (animation_end) {
					destroy();
				}
			}
		}

	}

	@Override
	public void customDestroy() {
		if (type != null) {
			Item i = new Item(x, y, z, myRoom, type, Game3DRepository.bloque);
			myRoom.addObjeto(i);
		}
	}

	/**
	 * Calls for the destruction of the destroyable block
	 */
	public void callForDestruction() {
		destruction = true;
	}
}
