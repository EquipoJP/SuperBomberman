/**
 * Class representing an item
 */
package logic.characters;

import graphics.rooms.Room;
import graphics.rooms.game.GameRepository;
import kuusisto.tinysound.Sound;
import logic.Objeto;
import logic.Sprite;
import logic.Input.KEY;
import logic.collisions.NoPerspectiveBoundingBox;
import main.Initialization;
import sound.MusicRepository;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Item extends Objeto {

	public enum TYPE {
		BOMB, POWER, SPEED
	};

	private TYPE type;

	private boolean pickedup = false;
	private boolean pickedupSpriteStarted = false;
	private boolean destroying = false;
	private Sprite destroySprite = GameRepository.destroyItem;
	private int centerX;
	private int centerY;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param r
	 *            room
	 * @param t
	 *            type of item
	 */
	public Item(int x, int y, int z, Room r, TYPE t) {
		super(x, y, z, r);

		switch (t) {
		case BOMB:
			sprite_index = GameRepository.itemBomb;
			break;
		case POWER:
			sprite_index = GameRepository.itemPower;
			break;
		case SPEED:
			sprite_index = GameRepository.itemSpeed;
			break;
		}

		type = t;
		boundingBox = NoPerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		image_speed = 0;

		switch (type) {
		case BOMB:
			centerX = Initialization.getCenterXFromSpriteName(Initialization.SPRITES.BOMB_PICKUP.toString());
			centerY = Initialization.getCenterYFromSpriteName(Initialization.SPRITES.BOMB_PICKUP.toString());
			break;
		case POWER:
			centerX = Initialization.getCenterXFromSpriteName(Initialization.SPRITES.POWER_PICKUP.toString());
			centerY = Initialization.getCenterYFromSpriteName(Initialization.SPRITES.POWER_PICKUP.toString());
			break;
		case SPEED:
			centerX = Initialization.getCenterXFromSpriteName(Initialization.SPRITES.SPEED_PICKUP.toString());
			centerY = Initialization.getCenterYFromSpriteName(Initialization.SPRITES.SPEED_PICKUP.toString());
			break;
		}
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if (pickedup && !pickedupSpriteStarted) {
			pickedupSpriteStarted = true;
			image_speed = 0.2;
			resetAnimationEnd();
			switch (type) {
			case BOMB:
				sprite_index = GameRepository.pickupBomb;
				break;
			case POWER:
				sprite_index = GameRepository.pickupPower;
				break;
			case SPEED:
				sprite_index = GameRepository.pickupSpeed;
				break;
			}

			sprite_index.setCenterX(centerX);
			sprite_index.setCenterY(centerY);

			depth = Initialization.getDepth("Item");
		}

		if (destroying && !sprite_index.equals(destroySprite)) {
			image_speed = 0.2;
			resetAnimationEnd();
			sprite_index = destroySprite;
		}

		if (animation_end) {
			destroy();
		}
	}

	/**
	 * Pick up the item
	 */
	public void pickUp() {
		if (!destroying) {
			Sound powerup = MusicRepository.powerup;
			powerup.play();
			pickedup = true;
			boundingBox = null;
		}
	}

	/**
	 * Calls for the destruction of the item
	 */
	public void callForDestruction() {
		if (!pickedup) {
			destroying = true;
		}
	}

	/**
	 * @return type of item
	 */
	public TYPE getType() {
		return type;
	}

	/**
	 * @return true if it has been picked up, false otherwise
	 */
	public boolean hasBeenPickedUp() {
		return pickedup;
	}
}
