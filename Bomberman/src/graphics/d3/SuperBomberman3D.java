package graphics.d3;

import logic.StatesMachine;
import logic.StatesMachine.STATE;
import main.Game;

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

	public static LwjglApplication main() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.height = Game.HEIGHT;
		config.width = Game.WIDTH;
		config.title = "Super Bomberman 3D";
		return new LwjglApplication(new SuperBomberman3D(), config);
	}

	public PerspectiveCamera cam;
	public CameraInputController camController;

	public Model bombermanModel;
	public Model planeModel;
	public Environment env;
	public ModelBatch modelBatch;

	public ModelInstance bomberman;
	public ModelInstance plane;

	private int FIELD_OF_VIEW = 67;

	private Vector3 initialPosition = new Vector3(10f, 10f, 10f);
	private Vector3 origin = new Vector3(0, 0, 0);
	private float near = 1f;
	private float far = 300f;

	@Override
	public void create() {
		camera();
		models();
		environment();
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
		bombermanModel = mb.createBox(3f, 3f, 3f,
				new Material(ColorAttribute.createDiffuse(Color.RED)),
				Usage.Position | Usage.Normal);
		bomberman = new ModelInstance(bombermanModel);
		bomberman.transform.translate(new Vector3(0, 2, 0));

		planeModel = mb.createRect(-10, 0, 10,
				10, 0, 10,
				10, 0, -10,
				-10, 0, -10,
                0, 1, 0,
                GL20.GL_TRIANGLES,
                new Material(
                    new ColorAttribute(
                        ColorAttribute.createDiffuse(Color.BLUE)),
                    new BlendingAttribute(
                        GL20.GL_SRC_ALPHA,
                        GL20.GL_ONE_MINUS_SRC_ALPHA)),
                VertexAttributes.Usage.Position |
                VertexAttributes.Usage.TextureCoordinates);
		plane = new ModelInstance(planeModel);
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
		
		camController.update();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(bomberman, env);
		modelBatch.render(plane, env);
		modelBatch.end();
	}

	public void step() {
		float modX = 0;
		float modY = 0;
		float modZ = 0;
		
		float x = 0;
		float y = 0;
		float z = 0;
		
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
		
		BoundingBox bb = new BoundingBox();
		bomberman.calculateBoundingBox(bb);
		bb = bb.mul(bomberman.transform);
		
		BoundingBox pp = new BoundingBox();
		plane.calculateBoundingBox(pp);
		pp = pp.mul(plane.transform);
		
		while(bb.intersects(pp)){
			bomberman.transform.translate(new Vector3(modX, modY, modZ));
			
			bb = new BoundingBox();
			bomberman.calculateBoundingBox(bb);
			bb = bb.mul(bomberman.transform);
			
			pp = new BoundingBox();
			plane.calculateBoundingBox(pp);
			pp = pp.mul(plane.transform);
		}
		/* end of collisions and things */
		
		if(Gdx.input.isKeyPressed(Input.Keys.F1)){
			StatesMachine.goToRoom(STATE.MAIN_MENU, false);
		}
	}

	@Override
	public void dispose() {
		bombermanModel.dispose();
		planeModel.dispose();
		modelBatch.dispose();
	}
}
