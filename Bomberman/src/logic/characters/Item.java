package logic.characters;

import graphics.D2.rooms.Room;
import graphics.D2.rooms.game.GameRepository;
import kuusisto.tinysound.Sound;
import logic.Objeto;
import logic.collisions.NoPerspectiveBoundingBox;
import sound.MusicRepository;

public class Item extends Objeto{

	public enum TYPE {BOMB, POWER, SPEED};
	
	private TYPE type;
	
	public Item(int x, int y, Room r, TYPE t) {
		super(x, y, r);
		
		switch(t){
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
	}
	
	@Override
	public void customDestroy(){
		Sound powerup = MusicRepository.powerup;
		powerup.play();
	}
	
	public TYPE getType(){
		return type;
	}
}
