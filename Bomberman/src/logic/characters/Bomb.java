package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;
import logic.Objeto;
import logic.collisions.BoundingBox;
import logic.collisions.Point2D;
import main.Game;
import main.Initialization;

public class Bomb extends Objeto {

	private static final int SECONDS = 3;
	private int radius = 1;
	
	private Player player;
	
	public Bomb(int x, int y, Room r, int radius, Player player) {
		super(x, y, r);
		this.radius = radius;
		this.player = player;
		sprite_index = GameRepository.bomb;
		image_speed = 0.2;
		
		boundingBox = new BoundingBox(new Point2D(x
				- Initialization.TILE_HEIGHT / 4, y - Initialization.TILE_WIDTH
				/ 4), new Point2D(x + Initialization.TILE_HEIGHT / 4, y
				+ Initialization.TILE_WIDTH / 4));
	}

	@Override
	public void create() {
		System.out.println(SECONDS * (int) Game.FPS);
		setAlarm(0, SECONDS * (int) Game.FPS);
	}

	@Override
	public void alarm(int alarmNo) {
		switch (alarmNo) {
		case 0:
			System.out.println("Alarm");
			destroyBomb();
			break;
		default:
			break;
		}
	}
	
	private void destroyBomb(){
		ExplosionManager em = new ExplosionManager(x, y, myRoom, radius);
		myRoom.addObjeto(em);
		player.bombs--;
		destroy();
	}
}
