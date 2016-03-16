package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;
import logic.Objeto;
import logic.Sprite;
import logic.Input.KEY;
import main.Game;

public class Bomb extends Objeto {

	private static final int SECONDS = 3;
	private int radius = 1;
	private boolean destruction;
	
	private Player player;
	
	private Sprite bomb;
	private Sprite destroyedBomb;

	public Bomb(int x, int y, Room r, int radius, Player player) {
		super(x, y, r);
		this.radius = radius;
		this.player = player;
		destruction = false;
		
		bomb = GameRepository.bomb;
		destroyedBomb = GameRepository.destroyedBomb;
		
		sprite_index = bomb;
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
			callForDestruction();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void customStep(KEY key, KEY direction) {
		if(destruction){
			if(sprite_index != destroyedBomb){
				sprite_index = destroyedBomb;
				image_index = 0;
				resetAnimationEnd();
			}
			else{
				if(animation_end){
					destroyBomb();
				}
			}
		}
		
	}

	public void callForDestruction(){
		destruction = true;
	}
	
	private void destroyBomb(){
		ExplosionManager em = new ExplosionManager(x, y, myRoom, radius);
		myRoom.addObjeto(em);
		player.bombs--;
		destroy();
	}
}
