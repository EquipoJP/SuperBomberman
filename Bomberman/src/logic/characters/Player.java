/**
 * Class representing the player
 */
package logic.characters;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import graphics.d3.utils.TransformacionesAfines;
import graphics.rooms.Room;
import graphics.rooms.game.Game3DRepository;
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

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Player extends Objeto {

	/* Info Sprites */
	private Map<String, Sprite> sprites;

	private static final int HELP_THRESHOLD = 8;
	private static final double INITIAL_ACCELERATION = 0.5;

	private double speed;
	private double acceleration;

	private boolean destruction;
	private boolean modSwitch;

	public int bombs;
	public int bombRadius;
	public int bombsLimit;
	private ArrayList<Objeto> ownBombs;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param r
	 *            room
	 */
	public Player(int x, int y, int z, Room r, graphics.d3.objetos.Objeto d3Object) {
		super(x, y, z, r);
		super.d3Object = d3Object.clone();
		
		super.d3Object.addTransformation(TransformacionesAfines.getXTraslation(x));
		super.d3Object.addTransformation(TransformacionesAfines.getYTraslation(y));
	}

	@Override
	public void create() {
		sprites = GameRepository.player;
		sprite_index = sprites.get(Initialization.BOMBERMAN_SPRS[0]); // idle

		boundingBox = PerspectiveBoundingBox.createBoundingBox(sprite_index);
		boundingBox.update(x, y);

		speed = 1.5;
		acceleration = INITIAL_ACCELERATION;
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
			case BOMB:
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
					sprite_index = sprites
							.get(Initialization.BOMBERMAN_SPRS[1]);
					unsetAlarm(0);
					break;
				case UP:
					sprite_index = sprites
							.get(Initialization.BOMBERMAN_SPRS[4]);
					unsetAlarm(0);
					break;
				case LEFT:
					sprite_index = sprites
							.get(Initialization.BOMBERMAN_SPRS[3]);
					unsetAlarm(0);
					break;
				case RIGHT:
					sprite_index = sprites
							.get(Initialization.BOMBERMAN_SPRS[2]);
					unsetAlarm(0);
					break;
				case NO_KEY:
					acceleration = INITIAL_ACCELERATION;
					stop = true;
					if (!isAlarmSet(0)
							&& sprite_index != sprites
									.get(Initialization.BOMBERMAN_SPRS[0])
							&& sprite_index != sprites
									.get(Initialization.BOMBERMAN_SPRS[5])) {
						int seconds = 5;
						setAlarm(0, seconds * (int) Game.FPS);
					}
					break;
				default:
					break;
				}
			}

			// change speed
			if (sprite_index.equals(sprites
					.get(Initialization.BOMBERMAN_SPRS[0]))) {
				image_speed = 0.1;
			} else if (sprite_index.equals(sprites
					.get(Initialization.BOMBERMAN_SPRS[5]))) {
				image_speed = 0.1;
			} else if (stop) {
				image_speed = 0;
				image_index = 0;
			} else {
				image_speed = 0.2;
			}
		} else {
			if (animation_end) {
				destroy();
			}
		}
	}

	@Override
	public void processKey(KEY key, KEY direction) {
		if (!destruction) {
			double actualSpeed = 1 + acceleration;
			double checkSpeed = actualSpeed;
			if (Math.floor(actualSpeed) != Math.round(actualSpeed)) {
				if (modSwitch) {
					modSwitch = !modSwitch;
					actualSpeed = Math.floor(actualSpeed);
				} else {
					modSwitch = !modSwitch;
					actualSpeed = Math.round(actualSpeed);
				}
			} else {
				actualSpeed = (int) actualSpeed;
			}
			int tryToMoveSpeed = (int) actualSpeed;
			switch (direction) {
			case DOWN:
				tryToMove(0, tryToMoveSpeed);
				if (checkSpeed < speed) {
					acceleration += 0.5;
				}
				break;
			case UP:
				tryToMove(0, (-1 * tryToMoveSpeed));
				if (checkSpeed < speed) {
					acceleration += 0.5;
				}
				break;
			case LEFT:
				tryToMove((-1 * tryToMoveSpeed), 0);
				if (checkSpeed < speed) {
					acceleration += 0.5;
				}
				break;
			case RIGHT:
				tryToMove(tryToMoveSpeed, 0);
				if (checkSpeed < speed) {
					acceleration += 0.5;
				}
				break;
			case NO_KEY:
				break;
			default:
				break;
			}

			switch (key) {
			case BOMB:
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

	/**
	 * Puts a bomb on the tile the player is currently standing on top of
	 */
	private void putBomb() {
		if (bombs < bombsLimit) {
			// center of the tile in which the player is standing
			int row = logic.misc.Map.getRow(y + Initialization.TILE_WIDTH / 2);
			int col = logic.misc.Map.getCol(x);

			int _x = logic.misc.Map.getX(col);
			int _y = logic.misc.Map.getY(row);

			if (!checkCollision(_x, _y)) {
				Bomb bomb = new Bomb(_x, _y, z, myRoom, bombRadius, this, Game3DRepository.bloque);
				myRoom.addObjeto(bomb);
				ownBombs.add(bomb);
				bombs++;
			}
		}
	}

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return true if there's been a collision, false otherwise
	 */
	private boolean checkCollision(int x, int y) {
		BoundingBox bb = new BoundingBox(new Point2D(x
				- Initialization.TILE_HEIGHT / 4, y - Initialization.TILE_WIDTH
				/ 4), new Point2D(x + Initialization.TILE_HEIGHT / 4, y
				+ Initialization.TILE_WIDTH / 4));
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

	/**
	 * Calls for the destruction of the player
	 */
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
		List<Objeto> newList = new ArrayList<Objeto>();
		int returned = checkPlayerCollisions(collided, newList);
		if (returned > 1) {
			boundingBox.update(-modX, -modY);
			return false;
		} else if (returned == 1) {
			Objeto o = newList.get(0);

			Rectangle r = BoundingBox.collisionRectangle(this.boundingBox,
					o.boundingBox);
			if (modX != 0) {
				// moving left or right
				if (r.height >= 1 && r.height <= HELP_THRESHOLD) {
					// hep the player
					y = y + (r.height * (int) Math.signum(this.y - o.y));
					boundingBox.update(0,
							r.height * (int) Math.signum(this.y - o.y));
					x = x + modX;
					y = y + modY;
					// TODO traslacion 3d
					super.d3Object.addTransformation(TransformacionesAfines.getXTraslation(modX));
					super.d3Object.addTransformation(TransformacionesAfines.getYTraslation(modY));
					return true;
				} else {
					// not helping the player (absolute collision)
					boundingBox.update(-modX, -modY);
					return false;
				}
			} else {
				// moving up and down
				if (r.width >= 1 && r.width <= HELP_THRESHOLD) {
					// help the player
					x = x + (r.width * (int) Math.signum(this.x - o.x));
					boundingBox.update(
							r.width * (int) Math.signum(this.x - o.x), 0);
					x = x + modX;
					y = y + modY;
					// TODO traslacion 3d
					super.d3Object.addTransformation(TransformacionesAfines.getXTraslation(modX));
					super.d3Object.addTransformation(TransformacionesAfines.getYTraslation(modY));
					return true;
				} else {
					// not helping the player (absolute collision)
					boundingBox.update(-modX, -modY);
					return false;
				}
			}
		} else {
			x = x + modX;
			y = y + modY;
			// TODO traslacion 3d
			super.d3Object.addTransformation(TransformacionesAfines.getXTraslation(modX));
			super.d3Object.addTransformation(TransformacionesAfines.getYTraslation(modY));
			return true;
		}
	}

	/**
	 * @param toCheck
	 *            objects to check collision with
	 * @param finalCollision
	 *            out parameter
	 * @return 0 if it has no collisions with nothing; 1 if it has only one
	 *         collision with a solid; 2 or more if it has multiple collisions
	 *         with solids. finalCollision contains those collisions remaining
	 */
	public int checkPlayerCollisions(List<Objeto> toCheck,
			List<Objeto> finalCollision) {
		if (toCheck != null) {
			List<Objeto> objsToCheck = new ArrayList<Objeto>();
			objsToCheck.addAll(toCheck);
			for (int i = 0; i < ownBombs.size(); i++) {
				// are we colliding still with the bomb?
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

			if (finalCollision == null) {
				finalCollision = new ArrayList<Objeto>();
			}
			finalCollision.addAll(toCheck);

			return toCheck.size();
		} else {
			ownBombs = new ArrayList<Objeto>();
			return 0;
		}
	}

	/**
	 * Get the power up from the item
	 * 
	 * @param item
	 *            item to get the power up from
	 */
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
