package graphics.D2.rooms.game;

import java.util.Map;

import logic.Sprite;
import main.Initialization;
import main.Initialization.STAGE;
import main.Initialization.TYPE;

public class GameRepository {

	static Sprite tiles = null;
	static Sprite background = null;

	public static Sprite block = null;
	public static Sprite blueDoll = null;
	public static Sprite pinkDoll = null;

	public static Sprite destroyableBlock1 = null;
	public static Sprite destroyableBlock2 = null;

	public static Map<String, Sprite> player = null;
	
	public static void load(STAGE stage) {
		loadTiles(stage);
		loadBackground();

		loadBlock(stage);

		loadBlueDoll();
		loadPinkDoll();

		loadDestroyables(stage);

		loadPlayer();
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

}
