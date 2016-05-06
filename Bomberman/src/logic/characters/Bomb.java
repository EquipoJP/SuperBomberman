/**
 * Class representing a bomb
 */
package logic.characters;

import graphics.rooms.Room;
import graphics.rooms.game.GameRepository;
import kuusisto.tinysound.Sound;
import logic.Objeto;
import logic.collisions.BoundingBox;
import logic.collisions.Point2D;
import main.Game;
import main.Initialization;
import sound.MusicRepository;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Bomb extends Objeto {

	private static final int SECONDS = 3;
	private int radius = 1;

	private Player player;

	/**
	 * Creates a bomb
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param r
	 *            room
	 * @param radius
	 *            radius of explosion
	 * @param player
	 *            owner of the bomb
	 */
	public Bomb(int x, int y, int z, Room r, int radius, Player player, graphics.d3.objetos.Objeto d3Object) {
		super(x, y, z, r);

		Sound put = MusicRepository.putBomb;
		put.play();

		this.radius = radius;
		this.player = player;

		sprite_index = GameRepository.bomb;
		image_speed = 0.15;

		boundingBox = new BoundingBox(new Point2D(x
				- Initialization.TILE_HEIGHT / 2, y - Initialization.TILE_WIDTH
				/ 2), new Point2D(x + Initialization.TILE_HEIGHT / 2, y
				+ Initialization.TILE_WIDTH / 2));

		depth = Initialization.getDepth("Bomb");
		
		super.d3Object = d3Object.clone();
		// TODO traslacion 3d
	}

	@Override
	public void create() {
		// System.out.println(SECONDS * (int) Game.FPS);
		setAlarm(0, SECONDS * (int) Game.FPS);
	}

	@Override
	public void alarm(int alarmNo) {
		switch (alarmNo) {
		case 0:
			Sound expl = MusicRepository.explosion;
			expl.play();
			destroyBomb();
			break;
		default:
			break;
		}
	}

	/**
	 * Begins the explosion of the bomb
	 */
	private void destroyBomb() {
		ExplosionManager em = new ExplosionManager(x, y, z, myRoom, radius);
		myRoom.addObjeto(em);
		player.bombs--;
		destroy();
	}

	/**
	 * Call for the destruction of the bomb
	 */
	public void callForDestruction() {
		if (alarm[0] > 0) {
			unsetAlarm(0);
			destroyBomb();
		}
	}
}
