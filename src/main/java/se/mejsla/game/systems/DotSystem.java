package se.mejsla.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import se.mejsla.game.World;
import se.mejsla.game.components.DotComponent;
import se.mejsla.game.components.MovementComponent;
import se.mejsla.game.components.TransformComponent;

public final class DotSystem extends IteratingSystem {
  private static final Family family = Family.all(DotComponent.class, TransformComponent.class, MovementComponent.class)
      .get();

  private float accelX = 0.0f;

  private final ComponentMapper<DotComponent> dm;
  private final ComponentMapper<TransformComponent> tm;
  private final ComponentMapper<MovementComponent> mm;

  private final World world;

  public DotSystem(final World world) {
    super(family);
    this.world = world;

    dm = ComponentMapper.getFor(DotComponent.class);
    tm = ComponentMapper.getFor(TransformComponent.class);
    mm = ComponentMapper.getFor(MovementComponent.class);
  }

  public void setAccelX(float accelX) {
    this.accelX = accelX;
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);

    accelX = 0.0f;
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    final TransformComponent t = tm.get(entity);
    final MovementComponent mov = mm.get(entity);
    final DotComponent dot = dm.get(entity);

    if (t.pos.x < 0) {
      t.pos.x = World.WORLD_WIDTH;
    }

    if (t.pos.x > World.WORLD_WIDTH) {
      t.pos.x = 0;
    }

    t.scale.x = mov.velocity.x < 0.0f ? Math.abs(t.scale.x) * -1.0f : Math.abs(t.scale.x);

  }

}
