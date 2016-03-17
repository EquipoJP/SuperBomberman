package graphics.D2.rooms.game;

import java.util.Map;

import logic.Sprite;
import main.Initialization;
import main.Initialization.STAGE;
import main.Initialization.TYPE;

public class GameRepository {
	
	static Sprite hud = null;

	static Sprite tiles = null;
	static Sprite background = null;

	public static Sprite block = null;
	public static Sprite blueDoll = null;
	public static Sprite pinkDoll = null;

	public static Sprite destroyableBlock1 = null;
	public static Sprite destroyableBlock2 = null;

	public static Map<String, Sprite> player = null;
	
	public static Sprite bomb = null;
	
	public static Sprite coreExplosion = null;
	public static Sprite edgeDownExplosion = null;
	public static Sprite edgeLeftExplosion = null;
	public static Sprite edgeRightExplosion = null;
	public static Sprite edgeUpExplosion = null;
	public static Sprite midHorExplosion = null;
	public static Sprite midVerExplosion = null;
	
	public static void load(STAGE stage) {
		loadHUD();
		loadTiles(stage);
		loadBackground();

		loadBlock(stage);

		loadBlueDoll();
		loadPinkDoll();

		loadDestroyables(stage);

		loadPlayer();
		
		loadBomb();
		
		loadExplosions();
	}

	private static void loadHUD() {
		if(hud == null){
			hud = Initialization.getSpriteFromMenu(Initialization.BUTTONS.HUD.toString());
		}
	}

	private static void loadTiles(STAGE stage) {
		if (tiles == null) {
			tiles = Initialization.getSpriteFromMap(stage.toString() + "_" + Initialization.TYPE.TILE.toString());
		}
	}

	private static void loadBackground() {
		// TODO Auto-generated method stub

	}

	private static void loadBlock(STAGE stage) {
		if (block == null) {
			block = Initialization.getSpriteFromMap(stage.toString() + "_" + TYPE.BLOCK.toString());
		}
	}

	private static void loadBlueDoll() {
		if (blueDoll == null) {
			blueDoll = Initialization.getSpriteFromSprites(Initialization.SPRITES.BLUE_DOLL.toString());
		}
	}

	private static void loadPinkDoll() {
		if (pinkDoll == null) {
			pinkDoll = Initialization.getSpriteFromSprites(Initialization.SPRITES.PINK_DOLL.toString());
		}
	}

	private static void loadDestroyables(STAGE stage) {
		if (destroyableBlock1 == null) {
			destroyableBlock1 = Initialization
					.getSpriteFromMap(stage.toString() + "_" + TYPE.DESTROYABLE_BLOCK.toString());
		}
		if (destroyableBlock2 == null) {
			destroyableBlock2 = Initialization.getSpriteFromMap(stage.toString() + "_" + TYPE.DESTROY_BLOCK.toString());
		}
	}

	private static void loadPlayer() {
		if (player == null) {
			player = Initialization.getSprites(Initialization.SPRITES.WHITE_BOMBER.toString());
		}
	}
	
	private static void loadBomb() {
		if (bomb == null) {
			bomb = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.BOMB.toString());
		}
	}
	
	private static void loadExplosions() {
		if (coreExplosion == null) {
			coreExplosion = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_CORE.toString());
		}
		if (edgeDownExplosion == null) {
			edgeDownExplosion = Initialization.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_EDGE_DOWN.toString());
		}
		if (edgeLeftExplosion == null) {
			edgeLeftExplosion = Initialization.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_EDGE_LEFT.toString());
		}
		if (edgeRightExplosion == null) {
			edgeRightExplosion = Initialization.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_EDGE_RIGHT.toString());
		}
		if (edgeUpExplosion == null) {
			edgeUpExplosion = Initialization.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_EDGE_UP.toString());
		}
		if (midHorExplosion == null) {
			midHorExplosion = Initialization.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_MID_HOR.toString());
		}
		if (midVerExplosion == null) {
			midVerExplosion = Initialization.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_MID_VER.toString());
		}
	}

}
