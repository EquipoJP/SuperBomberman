package graphics.d3;

import graphics.rooms.game.GameRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import logic.Input.KEY;
import logic.Objeto;
import logic.StatesMachine;
import logic.StatesMachine.STATE;
import logic.characters.Block;
import logic.characters.DestroyableBlock;
import logic.characters.Enemy;
import logic.characters.Player;
import logic.collisions.Point2D;
import logic.misc.Level;
import main.Game;
import main.Initialization;

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
import com.badlogic.gdx.math.collision.BoundingBox;

public class SuperBomberman3D extends ApplicationAdapter implements
		ApplicationListener {
	
	private final float MOV = 0.1f;
	private final float MOD_MOV = 0.01f;

	public SuperBomberman3D(graphics.rooms.game.Game game) {
		room = game;
	}

	public static LwjglApplication main(graphics.rooms.game.Game game) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.forceExit = false;
		config.height = Game.HEIGHT;
		config.width = Game.WIDTH;
		config.title = "Super Bomberman 3D";
		return new LwjglApplication(new SuperBomberman3D(game), config);
	}

	public PerspectiveCamera cam;
	public CameraInputController camController;

	public Model bombermanModel;
	public Model enemyModel;
	public Model boxModel;
	public Model planeModel;
	
	public Environment env;
	public ModelBatch modelBatch;

	private int FIELD_OF_VIEW = 67;

	private Vector3 initialPosition = new Vector3(10f, 10f, 10f);
	private Vector3 origin = new Vector3(0, 0, 0);
	private float near = 1f;
	private float far = 300f;
	
	/* Game */
	private graphics.rooms.game.Game room;
	private int xInitPlane, zInitPlane, xEndPlane, zEndPlane;
	Map<Objeto, ModelInstance> objetos;
	ModelInstance plane;
	/* End Game */

	@Override
	public void create() {
		init();
		camera();
		models();
		environment();
		System.out.println("Finished creating things");
	}
	
	private void init(){
		Level lvl = room.level;
		xInitPlane = lvl.mapInitX;
		xEndPlane = lvl.mapInitX + lvl.mapWidth;
		zInitPlane = lvl.mapInitY;
		xEndPlane = lvl.mapInitY + lvl.mapHeight;
	}

	private void camera() {
		cam = new PerspectiveCamera(FIELD_OF_VIEW, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
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
		
		/* bomberman */
		int playerWidth, playerHeight;
		playerWidth = GameRepository.player.get(Initialization.BOMBERMAN_SPRS[0]).getWidth();
		playerHeight = GameRepository.player.get(Initialization.BOMBERMAN_SPRS[0]).getHeight();
		
		bombermanModel = mb.createBox(playerWidth, playerHeight, playerWidth,
				new Material(ColorAttribute.createDiffuse(Color.BLUE)),
				Usage.Position | Usage.Normal);
		
		/* enemy */
		int enemyWidth, enemyHeight;
		enemyWidth = GameRepository.enemy.get(Initialization.ENEMIES_SPRS[2]).getWidth();
		enemyHeight = GameRepository.enemy.get(Initialization.ENEMIES_SPRS[2]).getHeight();
		
		enemyModel = mb.createBox(enemyWidth, enemyHeight, enemyWidth,
				new Material(ColorAttribute.createDiffuse(Color.RED)),
				Usage.Position | Usage.Normal);
		
		/* box */
		int boxWidth, boxHeight;
		boxWidth = GameRepository.block.getWidth();
		boxHeight = GameRepository.block.getHeight();
		
		boxModel = mb.createBox(boxWidth, boxHeight, boxWidth,
				new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
				Usage.Position | Usage.Normal);


		/* plane model */
		planeModel = mb.createRect(xInitPlane, 0, zEndPlane,
				xEndPlane, 0, zEndPlane,
				xEndPlane, 0, zInitPlane,
				xInitPlane, 0, zInitPlane,
                0, 1, 0,
                GL20.GL_TRIANGLES,
                new Material(
                    new ColorAttribute(
                        ColorAttribute.createDiffuse(Color.GREEN)),
                    new BlendingAttribute(
                        GL20.GL_SRC_ALPHA,
                        GL20.GL_ONE_MINUS_SRC_ALPHA)),
                VertexAttributes.Usage.Position |
                VertexAttributes.Usage.TextureCoordinates);
		plane = new ModelInstance(planeModel);
		
		
		objetos = new HashMap<>();
		for(Objeto obj : room.objetos){
			ModelInstance model = null;
			boolean insert = false;
			
			if(obj instanceof Player){
				insert = true;
				model = new ModelInstance(bombermanModel);
				model.transform.translate(0, playerHeight/2, 0);
				model.transform.translate(obj.x, 0, obj.y);
			}
			if(obj instanceof Block || obj instanceof DestroyableBlock){
				insert = true;
				model = new ModelInstance(boxModel);
				model.transform.translate(0, boxHeight/2, 0);
				model.transform.translate(obj.x, 0, obj.y);
			}
			if(obj instanceof Enemy){
				insert = true;
				model = new ModelInstance(enemyModel);
				model.transform.translate(0, enemyHeight/2, 0);
				model.transform.translate(obj.x, 0, obj.y);
			}
			
			if(insert){
				objetos.put(obj, model);
			}
		}
	}

	private void environment() {
		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f,
				0.2f, 1f));
		env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	@Override
	public void render() {
		step();
		
		Collection<ModelInstance> objetosLista = objetos.values();
		
		camController.update();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(objetosLista, env);
		modelBatch.render(plane, env);
		modelBatch.end();
	}

	public void step() {
		/* move objects */
		Map<Objeto, Point2D> lastPos = new HashMap<>();
		for(Objeto obj : objetos.keySet()){
			lastPos.put(obj, new Point2D(obj.x, obj.y));
		}
		
		room.step(KEY.NO_KEY, KEY.NO_KEY);
		
		float lastX = 0, lastY = 0;
		for (Map.Entry<Objeto, ModelInstance> entry : objetos.entrySet()) {
		    Objeto key = entry.getKey();
		    ModelInstance value = entry.getValue();
		    
		    Point2D last = lastPos.get(key);
		    if (! (key instanceof Player)){
		    	value.transform.translate(key.x - last.getX(), 0, key.y - last.getY());
		    }
		    else{
		    	lastX = last.getX();
		    	lastY = last.getY();
		    }
		}
		/* end move objects */
		
		/* move bomberman */
		moveBomberman(lastX, lastY);
		/* end move bomberman */
		
		if(Gdx.input.isKeyPressed(Input.Keys.F1)){
			StatesMachine.goToRoom(STATE.MAIN_MENU, false);
		}
	}
	
	private void moveBomberman(float lastX, float lastY){
		ModelInstance bomberman = getBomberman();
		Player bombermanObjeto = getBombermanObject();
		
		float modX = 0;
		float modY = 0;
		float modZ = 0;
		
		float x = 0;
		float y = 0;
		float z = 0;
		
		float finalX = 0;
		float finalZ = 0;
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			x = -MOV;
			modX = MOD_MOV;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			x = MOV;
			modX = -MOD_MOV;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			z = MOV;
			modZ = -MOD_MOV;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			z = -MOV;
			modZ = MOD_MOV;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)){
			y = MOV;
			modY = -MOD_MOV;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)){
			y = -MOV;
			modY = MOD_MOV;
		}

		/* collisions and things */
		bomberman.transform.translate(new Vector3(x, y, z));
		finalX = lastX + x;
		finalZ = lastY + z;
		
		BoundingBox bb = new BoundingBox();
		bomberman.calculateBoundingBox(bb);
		bb = bb.mul(bomberman.transform);
		
		BoundingBox pp = new BoundingBox();
		plane.calculateBoundingBox(pp);
		pp = pp.mul(plane.transform);
		
		while(bb.intersects(pp)){
			bomberman.transform.translate(new Vector3(modX, modY, modZ));
			finalX = finalX + modX;
			finalZ = finalZ + modZ;
			
			bb = new BoundingBox();
			bomberman.calculateBoundingBox(bb);
			bb = bb.mul(bomberman.transform);
			
			pp = new BoundingBox();
			plane.calculateBoundingBox(pp);
			pp = pp.mul(plane.transform);
		}
		
		/* collision with stuff */
		for(ModelInstance model : objetos.values()){
			if(model.equals(bomberman)){
				;
			}
			else{
				bb = new BoundingBox();
				bomberman.calculateBoundingBox(bb);
				bb = bb.mul(bomberman.transform);
				
				BoundingBox mm = new BoundingBox();
				model.calculateBoundingBox(mm);
				mm = mm.mul(model.transform);
				
				while(bb.intersects(mm)){
					bomberman.transform.translate(new Vector3(modX, modY, modZ));
					finalX = finalX + modX;
					finalZ = finalZ + modZ;
					
					bb = new BoundingBox();
					bomberman.calculateBoundingBox(bb);
					bb = bb.mul(bomberman.transform);
					
					mm = new BoundingBox();
					model.calculateBoundingBox(mm);
					mm = mm.mul(model.transform);
				}
			}
		}
		/* end collision with stuff */
		
		bombermanObjeto.x = (int) finalX;
		bombermanObjeto.y = (int) finalZ;
		
		/* end of collisions and things */
	}
	
	private ModelInstance getBomberman(){
		for (Map.Entry<Objeto, ModelInstance> entry : objetos.entrySet()) {
		    Objeto key = entry.getKey();
		    ModelInstance value = entry.getValue();
		    
		    if (key instanceof Player){
		    	return value;
		    }
		}
		return null;
	}
	
	private Player getBombermanObject(){
		for (Map.Entry<Objeto, ModelInstance> entry : objetos.entrySet()) {
		    Objeto key = entry.getKey();
		    
		    if (key instanceof Player){
		    	return (Player) key;
		    }
		}
		return null;
	}

	@Override
	public void dispose() {
		bombermanModel.dispose();
		planeModel.dispose();
		boxModel.dispose();
		enemyModel.dispose();
		room.destroy();
		modelBatch.dispose();
	}
}
