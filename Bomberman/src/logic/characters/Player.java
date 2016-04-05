package logic.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import graphics.rooms.Room;
import graphics.rooms.game.GameRepository;
import kuusisto.tinysound.Music;
import logic.Input.KEY;
import logic.Objeto;
import logic.Sprite;
import logic.collisions.BoundingBox;
import logic.collisions.PerspectiveBoundingBox;
import logic.collisions.Point2D;
import main.Game;
import main.Initialization;
import sound.MusicRepository;

public class Player extends Objeto {

	/* Info to get Sprites */
	private Map<String, Sprite> sprites;

	private double speed;

	private boolean destruction;
	private boolean modSwitch;

	public int bombs;
	public int bombRadius;
	public int bombsLimit;
	private ArrayList<Objeto> ownBombs;

	public Player(int x, int y, Room r, int depth) {
		super(x, y, r);
	}

	@Override
	public void create() {
		sprites = GameRepository.player;
		sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[0]); // idle

		boundingBox = PerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);
		System.out.println("PLAYER " + boundingBox);

		speed = 1.5;
		modSwitch = false;

		bombs = 0;
		bombsLimit = 1;
		bombRadius = 1;
		ownBombs = new ArrayList<Objeto>();

		destruction = false;
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if (!destruction) {
			boolean keyed = false;
			switch (key) {
			case SPACE:
				keyed = true;
				unsetAlarm(0);
				break;
			default:
				break;
			}

			boolean stop = false;

			if (!keyed) {
				switch (direction) {
				case DOWN:
					sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[1]);
					unsetAlarm(0);
					break;
				case UP:
					sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[4]);
					unsetAlarm(0);
					break;
				case LEFT:
					sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[3]);
					unsetAlarm(0);
					break;
				case RIGHT:
					sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[2]);
					unsetAlarm(0);
					break;
				case NO_KEY:
					stop = true;
					if (!isAlarmSet(0) && sprite_index != sprites.get(Initialization.BOMBERMAN_SPRS[0])) {
						int seconds = 5;
						setAlarm(0, seconds * (int) Game.FPS);
					}
					break;
				default:
					break;
				}
			}

			// change speed
			if (sprite_index.equals(sprites.get(Initialization.BOMBERMAN_SPRS[0]))) {
				image_speed = 0.1;
			} else if (stop) {
				image_speed = 0;
				image_index = 0;
			} else {
				image_speed = 0.2;
			}
		} else {
			System.out.println("Animation end: " + animation_end);
			if (animation_end) {
				destroy();
			}
		}
	}

	@Override
	public void processKey(KEY key, KEY direction) {
		if (!destruction) {
			int actualSpeed = 0;
			if (Math.floor(speed) != Math.round(speed)) {
				if (modSwitch) {
					modSwitch = !modSwitch;
					actualSpeed = (int) (Math.floor(speed));
				} else {
					modSwitch = !modSwitch;
					actualSpeed = (int) (Math.round(speed));
				}
			} else {
				actualSpeed = (int) speed;
			}
			switch (direction) {
			case DOWN:
				tryToMove(0, actualSpeed);
				break;
			case UP:
				tryToMove(0, (-1 * actualSpeed));
				break;
			case LEFT:
				tryToMove((-1 * actualSpeed), 0);
				break;
			case RIGHT:
				tryToMove(actualSpeed, 0);
				break;
			case NO_KEY:
				break;
			default:
				break;
			}

			switch (key) {
			case SPACE:
				putBomb();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void alarm(int alarmNo) {
		switch (alarmNo) {
		case 0:
			sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[0]);
			image_index = 0;
			image_speed = 0.1;
			break;
		default:
			break;
		}
	}

	private void putBomb() {
		if (bombs < bombsLimit) {
			// center of the tile in which the player is standing
			int row = logic.misc.Map.getRow(y + Initialization.TILE_WIDTH / 2);
			int col = logic.misc.Map.getCol(x);

			int _x = logic.misc.Map.getX(col);
			int _y = logic.misc.Map.getY(row);

			if (!checkCollision(_x, _y)) {
				Bomb bomb = new Bomb(_x, _y, myRoom, bombRadius, this);
				myRoom.addObjeto(bomb);
				ownBombs.add(bomb);
				bombs++;
			}
		}
	}

	private boolean checkCollision(int x, int y) {
		BoundingBox bb = new BoundingBox(
				new Point2D(x - Initialization.TILE_HEIGHT / 4, y - Initialization.TILE_WIDTH / 4),
				new Point2D(x + Initialization.TILE_HEIGHT / 4, y + Initialization.TILE_WIDTH / 4));
		for (Objeto obj : myRoom.objetos) {
			if (!obj.equals(this)) {
				boolean collision = BoundingBox.collision(bb, obj.boundingBox);
				if (collision) {
					return true;
				}
			}
		}
		return false;
	}

	public void callForDestruction() {
		if (!destruction) {
			resetAnimationEnd();
			destruction = true;
			sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[6]);
			image_index = 0;
			image_speed = 0.15;
			Music defeat = MusicRepository.defeat;
			defeat.play(false);
		}
	}

	@Override
	public boolean tryToMove(int modX, int modY) {
		boundingBox.update(modX, modY);
		List<Objeto> collided = collision();
		if (checkPlayerCollisions(collided)) {
			boundingBox.update(-modX, -modY);
			return false;
		} else {
			x = x + modX;
			y = y + modY;
			return true;
		}
	}

	public boolean checkPlayerCollisions(List<Objeto> toCheck) {
		if (toCheck != null) {
			for (int i = 0; i < ownBombs.size(); i++) {
				// Booleano para comprobar si seguimos colisionando con una
				// bomba
				boolean bombFound = false;
				for (int j = 0; j < toCheck.size() && !bombFound; j++) {
					if (toCheck.get(j).equals(ownBombs.get(i))) {
						toCheck.remove(j);
						bombFound = true;
						j--;
					}
				}
				if (!bombFound) {
					ownBombs.remove(i);
					i--;
				}
			}

			// Check power ups
			for (int j = 0; j < toCheck.size(); j++) {
				if (toCheck.get(j) instanceof Item) {
					getPowerUp((Item) toCheck.get(j));
					toCheck.remove(j);
					j--;
				}
			}

			return toCheck.size() > 0;
		} else {
			ownBombs = new ArrayList<Objeto>();
			return false;
		}
	}

	private void getPowerUp(Item item) {
		if (!item.hasBeenPickedUp()) {
			switch (item.getType()) {
			case BOMB:
				bombsLimit++;
				break;
			case POWER:
				bombRadius++;
				break;
			case SPEED:
				speed += 0.5;
				break;
			default:
				break;
			}
			item.pickUp();
		}
	}
}
