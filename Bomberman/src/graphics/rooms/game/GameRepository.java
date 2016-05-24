/**
 * Class containing the resources for the game
 */
package graphics.rooms.game;

import java.util.Map;

import logic.Sprite;
import main.Initialization;
import main.Initialization.STAGE;
import main.Initialization.TYPE;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class GameRepository {

	static Sprite hud = null;
	static Sprite victory = null;

	static Sprite tiles = null;
	static Sprite background = null;

	public static Sprite block = null;

	public static Sprite destroyableBlock1 = null;
	public static Sprite destroyableBlock2 = null;

	public static Map<String, Sprite> player = null;
	public static Map<String, Sprite> enemy = null;

	public static Sprite bomb = null;
	public static Sprite stairs = null;

	public static Sprite coreExplosion = null;
	public static Sprite edgeDownExplosion = null;
	public static Sprite edgeLeftExplosion = null;
	public static Sprite edgeRightExplosion = null;
	public static Sprite edgeUpExplosion = null;
	public static Sprite midHorExplosion = null;
	public static Sprite midVerExplosion = null;

	public static Sprite itemBomb = null;
	public static Sprite itemPower = null;
	public static Sprite itemSpeed = null;

	public static Sprite pickupSpeed = null;
	public static Sprite pickupPower = null;
	public static Sprite pickupBomb = null;

	public static Sprite destroyItem = null;

	/**
	 * Load resources
	 * 
	 * @param stage
	 *            theme
	 */
	public static void load(STAGE stage) {
		loadHUD();
		loadVictory();

		loadTiles(stage);
		loadBackground();

		loadBlock(stage);

		loadDestroyables(stage);

		loadPlayer();
		loadEnemy(stage);

		loadBomb();
		loadStairs();

		loadExplosions();

		loadItems();
	}

	/**
	 * Load HUD
	 */
	private static void loadHUD() {
		if (hud == null) {
			hud = Initialization.getSpriteFromMenu(Initialization.MENUS.HUD
					.toString());
		}
	}

	/**
	 * Load victory
	 */
	private static void loadVictory() {
		if (victory == null) {
			victory = Initialization
					.getSpriteFromMenu(Initialization.MENUS.VICTORY.toString());
		}
	}

	/**
	 * Load tiles
	 * 
	 * @param stage
	 *            theme
	 */
	private static void loadTiles(STAGE stage) {
		if(stage == null){
			return ;
		}
		tiles = Initialization.getSpriteFromMap(stage.toString() + "_"
				+ Initialization.TYPE.TILE.toString());
	}

	/**
	 * Load background
	 */
	private static void loadBackground() {
		if (background == null) {
			background = Initialization
					.getSpriteFromMenu(Initialization.MENUS.BACKGROUND
							.toString());
		}
	}

	/**
	 * Load block
	 * 
	 * @param stage
	 *            theme
	 */
	private static void loadBlock(STAGE stage) {
		if(stage == null){
			return ;
		}
		block = Initialization.getSpriteFromMap(stage.toString() + "_"
				+ TYPE.BLOCK.toString());
	}

	/**
	 * Load destroyable blocl
	 * 
	 * @param stage
	 *            theme
	 */
	private static void loadDestroyables(STAGE stage) {
		if(stage == null){
			return ;
		}
		destroyableBlock1 = Initialization.getSpriteFromMap(stage.toString()
				+ "_" + TYPE.DESTROYABLE_BLOCK.toString());
		destroyableBlock2 = Initialization.getSpriteFromMap(stage.toString()
				+ "_" + TYPE.DESTROY_BLOCK.toString());
	}

	/**
	 * Load player
	 */
	private static void loadPlayer() {
		if (player == null) {
			player = Initialization.getSpritesFromTableSprites(
					Initialization.SPRITES.WHITE_BOMBER.toString(),
					Initialization.BOMBERMAN_SPRS);
		}
	}

	/**
	 * Load enemy
	 * 
	 * @param stage
	 *            theme
	 */
	private static void loadEnemy(STAGE stage) {
		if(stage == null){
			return ;
		}
		enemy = Initialization.getSpritesFromTableSprites(
				Initialization.SPRITES.ENEMY.toString() + "_"
						+ stage.toString(), Initialization.ENEMIES_SPRS);
	}

	/**
	 * Load bomb
	 */
	private static void loadBomb() {
		if (bomb == null) {
			bomb = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.BOMB
							.toString());
		}
	}

	/**
	 * Load stairs
	 */
	private static void loadStairs() {
		if (stairs == null) {
			stairs = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.STAIRS
							.toString());
		}
	}

	/**
	 * Load explosions
	 */
	private static void loadExplosions() {
		if (coreExplosion == null) {
			coreExplosion = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_CORE
							.toString());
		}
		if (edgeDownExplosion == null) {
			edgeDownExplosion = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_EDGE_DOWN
							.toString());
		}
		if (edgeLeftExplosion == null) {
			edgeLeftExplosion = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_EDGE_LEFT
							.toString());
		}
		if (edgeRightExplosion == null) {
			edgeRightExplosion = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_EDGE_RIGHT
							.toString());
		}
		if (edgeUpExplosion == null) {
			edgeUpExplosion = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_EDGE_UP
							.toString());
		}
		if (midHorExplosion == null) {
			midHorExplosion = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_MID_HOR
							.toString());
		}
		if (midVerExplosion == null) {
			midVerExplosion = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.EXPLOSION_MID_VER
							.toString());
		}
	}

	/**
	 * Load items
	 */
	private static void loadItems() {
		if (itemBomb == null) {
			itemBomb = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.ITEM_BOMB
							.toString());
		}
		if (itemPower == null) {
			itemPower = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.ITEM_POWER
							.toString());
		}
		if (itemSpeed == null) {
			itemSpeed = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.ITEM_SPEED
							.toString());
		}
		if (pickupSpeed == null) {
			pickupSpeed = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.SPEED_PICKUP
							.toString());
			pickupSpeed.setCenterX(Initialization.getCenterXFromSpriteName(Initialization.SPRITES.SPEED_PICKUP.toString()));
			pickupSpeed.setCenterX(Initialization.getCenterYFromSpriteName(Initialization.SPRITES.SPEED_PICKUP.toString()));
		}
		if (pickupPower == null) {
			pickupPower = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.POWER_PICKUP
							.toString());
			pickupPower.setCenterX(Initialization.getCenterXFromSpriteName(Initialization.SPRITES.POWER_PICKUP.toString()));
			pickupPower.setCenterY(Initialization.getCenterYFromSpriteName(Initialization.SPRITES.POWER_PICKUP.toString()));
		}
		if (pickupBomb == null) {
			pickupBomb = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.BOMB_PICKUP
							.toString());
			pickupBomb.setCenterX(Initialization.getCenterXFromSpriteName(Initialization.SPRITES.BOMB_PICKUP.toString()));
			pickupBomb.setCenterY(Initialization.getCenterYFromSpriteName(Initialization.SPRITES.BOMB_PICKUP.toString()));
		}
		if (destroyItem == null) {
			destroyItem = Initialization
					.getSpriteFromSprites(Initialization.SPRITES.ITEM_DESTROY
							.toString());
		}
	}
}
