package se.mejsla.game;

import java.util.Random;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

import se.mejsla.game.components.AnimationComponent;
import se.mejsla.game.components.CameraComponent;
import se.mejsla.game.components.DotComponent;
import se.mejsla.game.components.MovementComponent;
import se.mejsla.game.components.TextureComponent;
import se.mejsla.game.components.TransformComponent;
import se.mejsla.game.systems.RenderingSystem;

public class World {
  public static final float WORLD_WIDTH = 10;
  public static final float WORLD_HEIGHT = 15 * 20;
  public static final int WORLD_STATE_RUNNING = 0;
  public static final int WORLD_STATE_NEXT_LEVEL = 1;
  public static final int WORLD_STATE_GAME_OVER = 2;

  private final PooledEngine engine;
  private final Random rand;

  public World(PooledEngine engine) {
    this.engine = engine;
    this.rand = new Random();
  }

  public void create() {
    final Entity dot = createDot();
    createCamera(dot);
    // createBackground();
  }

  private Entity createDot() {
    final Entity entity = engine.createEntity();
    final AnimationComponent animation = engine.createComponent(AnimationComponent.class);
    final DotComponent dot = engine.createComponent(DotComponent.class);
		// final BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
		// final GravityComponent gravity = engine.createComponent(GravityComponent.class);
		final MovementComponent movement = engine.createComponent(MovementComponent.class);
		final TransformComponent position = engine.createComponent(TransformComponent.class);
		final TextureComponent texture = engine.createComponent(TextureComponent.class);

    position.pos.set(5.0f, 1.0f, 0.0f);

    entity.add(animation);
		entity.add(dot);
		// entity.add(bounds);
		// entity.add(gravity);
		entity.add(movement);
		entity.add(position);
    entity.add(texture);

    engine.addEntity(entity);

    return entity;
  }

  private void createCamera(Entity target) {
    final Entity entity = engine.createEntity();
    final CameraComponent camera = new CameraComponent();

    camera.camera = engine.getSystem(RenderingSystem.class).getCamera();
    camera.target = target;

    entity.add(camera);
    engine.addEntity(entity);
  }
}
