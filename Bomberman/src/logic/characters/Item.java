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

public class Item extends Objeto {

	public enum TYPE {
		BOMB, POWER, SPEED
	};

	private TYPE type;

	private boolean pickedup = false;
	private boolean pickedupSpriteStarted = false;
	private boolean destroying = false;
	private Sprite destroySprite = GameRepository.destroyItem;

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
				sprite_index.setCenterX(
						Initialization.getCenterXFromSpriteName(Initialization.SPRITES.BOMB_PICKUP.toString()));
				sprite_index.setCenterY(
						Initialization.getCenterYFromSpriteName(Initialization.SPRITES.BOMB_PICKUP.toString()));
				break;
			case POWER:
				sprite_index = GameRepository.pickupPower;
				sprite_index.setCenterX(
						Initialization.getCenterXFromSpriteName(Initialization.SPRITES.POWER_PICKUP.toString()));
				sprite_index.setCenterY(
						Initialization.getCenterYFromSpriteName(Initialization.SPRITES.POWER_PICKUP.toString()));
				break;
			case SPEED:
				sprite_index = GameRepository.pickupSpeed;
				sprite_index.setCenterX(
						Initialization.getCenterXFromSpriteName(Initialization.SPRITES.SPEED_PICKUP.toString()));
				sprite_index.setCenterY(
						Initialization.getCenterYFromSpriteName(Initialization.SPRITES.SPEED_PICKUP.toString()));
				break;
			}

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

	public void pickUp() {
		if (!destroying) {
			Sound powerup = MusicRepository.powerup;
			powerup.play();
			pickedup = true;
		}
	}

	public void callForDestruction() {
		if (!pickedup) {
			destroying = true;
		}
	}

	public TYPE getType() {
		return type;
	}

	public boolean hasBeenPickedUp() {
		return pickedup;
	}
}
