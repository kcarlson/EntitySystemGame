package se.mejsla.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import se.mejsla.game.systems.AnimationSystem;
import se.mejsla.game.systems.BackgroundSystem;
import se.mejsla.game.systems.CameraSystem;
import se.mejsla.game.systems.DotSystem;
import se.mejsla.game.systems.PlatformSystem;
import se.mejsla.game.systems.RenderingSystem;

public final class GameScreen extends ScreenAdapter {
  private static final int WIDTH = 500;
  private static final int HEIGHT = 500;
  private final MyGame game;
  private final OrthographicCamera guiCam;
  private final PooledEngine engine;
  private final World world;
  private final Vector3 touchPoint = new Vector3();

  public GameScreen(final MyGame game) {
    this.game = game;
    this.guiCam = new OrthographicCamera(WIDTH, HEIGHT);
    guiCam.position.set(WIDTH / 2, HEIGHT / 2, 0);
    engine = new PooledEngine();
    world = new World(engine);

    engine.addSystem(new DotSystem(world));
    engine.addSystem(new PlatformSystem());
    engine.addSystem(new CameraSystem());
    engine.addSystem(new BackgroundSystem());
    // engine.addSystem(new GravitySystem());
    engine.addSystem(new MovementSystem());
    // engine.addSystem(new BoundsSystem());
    // engine.addSystem(new StateSystem());
    engine.addSystem(new AnimationSystem());
    // engine.addSystem(new CollisionSystem(world, collisionListener));
    engine.addSystem(new RenderingSystem(game.batcher));

    engine.getSystem(BackgroundSystem.class).setCamera(engine.getSystem(RenderingSystem.class).getCamera());

    world.create();

    resumeSystems();
  }

  public void update(float deltaTime) {
    if (deltaTime > 0.1f) {
      deltaTime = 0.1f;
    }
    engine.update(deltaTime);
    updateRunning(deltaTime);
  }

  public void draw() {
    final GL20 gl = Gdx.gl;
    gl.glClearColor(255, 255, 255, 1);
    gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    guiCam.update();
    game.batcher.setProjectionMatrix(guiCam.combined);
  }

  private void updateRunning(float deltaTime) {
    if (Gdx.input.justTouched()) {
      guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
      return;
    }
    float accelX = 0.0f;

    if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT))
      accelX = 5f;
    if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT))
      accelX = -5f;

    System.out.println("-> accelX" + accelX + ", " + Gdx.input.getX() + "," + Gdx.input.getY());
    engine.getSystem(DotSystem.class).setAccelX(accelX);
  }

  public void drawUI() {
    guiCam.update();
    game.batcher.setProjectionMatrix(guiCam.combined);
  }


	private void pauseSystems() {
		engine.getSystem(DotSystem.class).setProcessing(false);
		// engine.getSystem(PlatformSystem.class).setProcessing(false);
		// engine.getSystem(GravitySystem.class).setProcessing(false);
		engine.getSystem(MovementSystem.class).setProcessing(false);
		// engine.getSystem(BoundsSystem.class).setProcessing(false);
		engine.getSystem(AnimationSystem.class).setProcessing(false);
	}

	private void resumeSystems() {
		engine.getSystem(DotSystem.class).setProcessing(true);
		// engine.getSystem(PlatformSystem.class).setProcessing(true);
		// engine.getSystem(GravitySystem.class).setProcessing(true);
		engine.getSystem(MovementSystem.class).setProcessing(true);
		// engine.getSystem(BoundsSystem.class).setProcessing(true);
		engine.getSystem(AnimationSystem.class).setProcessing(true);
	}


  @Override
  public void render(float delta) {
    update(delta);
    drawUI();
  }
}
