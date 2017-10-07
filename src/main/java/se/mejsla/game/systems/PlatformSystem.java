package se.mejsla.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.mejsla.game.World;
import se.mejsla.game.components.MovementComponent;
import se.mejsla.game.components.PlatformComponent;
import se.mejsla.game.components.TransformComponent;

public class PlatformSystem extends IteratingSystem {
  private static final Family family = Family
      .all(PlatformComponent.class, TransformComponent.class, MovementComponent.class).get();
  private Engine engine;

  private ComponentMapper<TransformComponent> tm;
  private ComponentMapper<MovementComponent> mm;
  private ComponentMapper<PlatformComponent> pm;

  public PlatformSystem() {
    super(family);

    tm = ComponentMapper.getFor(TransformComponent.class);
    mm = ComponentMapper.getFor(MovementComponent.class);
    pm = ComponentMapper.getFor(PlatformComponent.class);
  }

  @Override
  public void addedToEngine(Engine engine) {
    super.addedToEngine(engine);
    this.engine = engine;
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    final PlatformComponent platform = pm.get(entity);

    if (platform.type == PlatformComponent.TYPE_MOVING) {
      final TransformComponent pos = tm.get(entity);
      final MovementComponent mov = mm.get(entity);

      if (pos.pos.x < PlatformComponent.WIDTH / 2) {
        mov.velocity.x = -mov.velocity.x;
        pos.pos.x = PlatformComponent.WIDTH / 2;
      }
      if (pos.pos.x > World.WORLD_WIDTH - PlatformComponent.WIDTH / 2) {
        mov.velocity.x = -mov.velocity.x;
        pos.pos.x = World.WORLD_WIDTH - PlatformComponent.WIDTH / 2;
      }
    }
  }

  public void pulverize(Entity entity) {
    if (family.matches(entity)) {
      final MovementComponent mov = mm.get(entity);
      mov.velocity.x = 0;
    }
  }
}
