package graphics.d3;

import logic.Input;
import logic.StatesMachine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class SuperBomberman3D extends ApplicationAdapter implements
		ApplicationListener {

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		new LwjglApplication(new SuperBomberman3D(), config);
	}

	public PerspectiveCamera cam;
	public CameraInputController camController;

	public Model bombermanModel;
	public Environment env;

	public ModelInstance bomberman;
	public ModelBatch modelBatch;

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
		bombermanModel = mb.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.RED)),
				Usage.Position | Usage.Normal);
		bomberman = new ModelInstance(bombermanModel);
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
		modelBatch.end();
	}

	private void step() {
		float x = 0;
		float y = 0;
		
		Input.KEY direction = StatesMachine.input.getDirection();
		
		if(direction == Input.KEY.UP){
			y = 0.2f;
		}
		else if(direction == Input.KEY.DOWN){
			y = -0.2f;
		}
		else if(direction == Input.KEY.LEFT){
			x = -0.2f;
		}
		else if(direction == Input.KEY.RIGHT){
			x = 0.2f;
		}
		
		bomberman.transform.translate(new Vector3(x, y, 0));
	}

	@Override
	public void dispose() {
		bombermanModel.dispose();
		modelBatch.dispose();
	}
}
