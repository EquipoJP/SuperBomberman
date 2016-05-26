package graphics.d3;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import graphics.rooms.game.Game.STATE;
import graphics.rooms.game.GameRepository;
import logic.Input.KEY;
import logic.Objeto;
import logic.characters.Block;
import logic.characters.Bomb;
import logic.characters.DestroyableBlock;
import logic.characters.Enemy;
import logic.characters.ExplosionPart;
import logic.characters.Item;
import logic.characters.Player;
import logic.collisions.Point2D;
import logic.misc.Level;
import main.Game;

public class SuperBomberman3D extends ApplicationAdapter implements ApplicationListener {

	public SuperBomberman3D(graphics.rooms.game.Game game) {
		room = game;
	}

	public static LwjglApplication main(graphics.rooms.game.Game game) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// config.forceExit = false;
		config.height = Game.HEIGHT;
		config.width = Game.WIDTH;
		config.title = "Super Bomberman 3D";
		return new LwjglApplication(new SuperBomberman3D(game), config);
	}

	public PerspectiveCamera cam;
	public CameraInputController camController;
	public Thread calcs;

	public Model bombermanModel;
	public Model enemyModel;
	public Model boxModel;
	public Model destroyableModel;
	public Model itemModel;
	public Model bombModel;
	public Model explosionModel;
	public Model planeModel;

	public Environment env;
	public ModelBatch modelBatch;

	private int FIELD_OF_VIEW = 67;

	private Vector3 initialPosition = new Vector3(200f, 500f, 200f);
	private Vector3 origin = new Vector3(0, 0, 0);
	private float near = 1f;
	private float far = 5000f;

	private int width, height;

	/* Game */
	public graphics.rooms.game.Game room;
	private int xInitPlane, zInitPlane, xEndPlane, zEndPlane;
	public Map<Objeto, ModelInstance> objetos;
	public ModelInstance plane;

	/* End Game */

	@Override
	public void create() {
		calcs = null;
		init();
		camera();
		models();
		environment();
		System.out.println("Finished creating things");
	}

	private void init() {
		Level lvl = room.level;
		xInitPlane = lvl.mapInitX;
		xEndPlane = lvl.mapInitX + lvl.mapWidth;
		zInitPlane = lvl.mapInitY;
		zEndPlane = lvl.mapInitY + lvl.mapHeight;

		float xMid = xInitPlane + (xEndPlane - xInitPlane) / 2;
		float zMid = zInitPlane + (zEndPlane - zInitPlane) / 2;

		origin = new Vector3(xMid, 0, zMid);
		initialPosition = new Vector3(xMid, 600, zMid);
	}

	private void camera() {
		cam = new PerspectiveCamera(FIELD_OF_VIEW, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(initialPosition);
		cam.lookAt(origin);
		cam.near = near;
		cam.far = far;
		cam.update();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);
	}

	private void models() {
		modelBatch = new ModelBatch();
		ModelBuilder mb = new ModelBuilder();

		width = GameRepository.block.getWidth();
		height = GameRepository.block.getHeight();

		/* bomberman */
		bombermanModel = mb.createBox(width - 10, height + height / 2, width - 10,
				new Material(ColorAttribute.createDiffuse(Color.BLUE)), Usage.Position | Usage.Normal);

		/* enemy */
		enemyModel = mb.createBox(width - 10, height + height / 2, width - 10,
				new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Position | Usage.Normal);

		/* box */
		boxModel = mb.createBox(width, height, width, new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
				Usage.Position | Usage.Normal);
		destroyableModel = mb.createBox(width, height, width, new Material(ColorAttribute.createDiffuse(Color.GRAY)),
				Usage.Position | Usage.Normal);
		itemModel = mb.createBox(width, height, width, new Material(ColorAttribute.createDiffuse(Color.OLIVE)),
				Usage.Position | Usage.Normal);
		bombModel = mb.createBox(width, height, width, new Material(ColorAttribute.createDiffuse(Color.BROWN)),
				Usage.Position | Usage.Normal);
		explosionModel = mb.createBox(width, height, width, new Material(ColorAttribute.createDiffuse(Color.CORAL)),
				Usage.Position | Usage.Normal);

		/* plane model */
		planeModel = mb.createRect(xInitPlane, 0, zEndPlane, xEndPlane, 0, zEndPlane, xEndPlane, 0, zInitPlane,
				xInitPlane, 0, zInitPlane, 0, 1, 0, GL20.GL_TRIANGLES,
				new Material(new ColorAttribute(ColorAttribute.createDiffuse(Color.GREEN)),
						new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);
		plane = new ModelInstance(planeModel);

		objetos = new HashMap<>();
		for (Objeto obj : room.objetos) {
			ModelInstance model = null;
			boolean insert = false;

			if (obj instanceof Player) {
				insert = true;
				model = new ModelInstance(bombermanModel);
				model.transform.translate(0, height / 2, 0);
				model.transform.translate(obj.x, 0, obj.y + logic.misc.Map.fixpos);
			}
			if (obj instanceof Block) {
				insert = true;
				model = new ModelInstance(boxModel);
				model.transform.translate(0, height / 2, 0);
				model.transform.translate(obj.x, 0, obj.y);
			}
			if (obj instanceof DestroyableBlock) {
				insert = true;
				model = new ModelInstance(destroyableModel);
				model.transform.translate(0, height / 2, 0);
				model.transform.translate(obj.x, 0, obj.y);
			}
			if (obj instanceof Enemy) {
				insert = true;
				model = new ModelInstance(enemyModel);
				model.transform.translate(0, height / 2, 0);
				model.transform.translate(obj.x, 0, obj.y + logic.misc.Map.fixpos);
			}

			if (insert) {
				objetos.put(obj, model);
			}
		}
	}

	private void environment() {
		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 1f));
		env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	@Override
	public void render() {
		step();
		Collection<ModelInstance> objetosLista = objetos.values();

		camController.update();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(objetosLista, env);
		modelBatch.render(plane, env);
		modelBatch.end();
	}

	public void step() {
		/* move objects */
		Map<Objeto, Point2D> lastPos = new HashMap<>();
		for (Objeto obj : objetos.keySet()) {
			lastPos.put(obj, new Point2D(obj.x, obj.y));
		}

		KEY dir = KEY.NO_KEY;
		KEY keyPressed = KEY.NO_KEY;
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			dir = KEY.UP;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			dir = KEY.DOWN;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			dir = KEY.LEFT;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			dir = KEY.RIGHT;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			keyPressed = KEY.BOMB;
		}

		room.step(keyPressed, dir);

		List<Objeto> destroy = new LinkedList<Objeto>();
		for (Objeto obj : objetos.keySet()) {
			if (!room.objetos.contains(obj)) {
				destroy.add(obj);
			}
		}
		for (Objeto obj : destroy) {
			objetos.remove(obj);
		}

		for (Objeto obj : room.objetos) {
			obj.render(null);
			if (objetos.containsKey(obj)) {
				;
			} else {
				if (obj instanceof Bomb) {
					ModelInstance model = new ModelInstance(bombModel);
					model.transform.translate(0, height / 2, 0);
					model.transform.translate(obj.x, 0, obj.y);
					objetos.put(obj, model);
				}
				if (obj instanceof ExplosionPart) {
					ModelInstance model = new ModelInstance(explosionModel);
					model.transform.translate(0, height / 2, 0);
					model.transform.translate(obj.x + width / 2, 0, obj.y + height / 2);
					objetos.put(obj, model);
				}
				if (obj instanceof Item) {
					ModelInstance model = new ModelInstance(itemModel);
					model.transform.translate(0, height / 2, 0);
					model.transform.translate(obj.x, 0, obj.y);
					objetos.put(obj, model);
				}
			}
		}

		for (Map.Entry<Objeto, ModelInstance> entry : objetos.entrySet()) {
			Objeto key = entry.getKey();
			ModelInstance value = entry.getValue();

			Point2D last = lastPos.get(key);
			if (last != null) {
				value.transform.translate(key.x - last.getX(), 0, key.y - last.getY());
			}
		}
		/* end move objects */

		if (room.state == STATE.DESTRUCTION || room.state == STATE.VICTORY) {
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		bombermanModel.dispose();
		planeModel.dispose();
		boxModel.dispose();
		bombModel.dispose();
		explosionModel.dispose();
		enemyModel.dispose();
		room.destroy();
		modelBatch.dispose();
	}
}
