import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class MovementSystem extends EntitySystem {
  static final class PositionComponent implements Component {
    public float x = 0.0f;
    public float y = 0.0f;
  }

  static final class VelocityComponent implements Component {
    public float x = 0.0f;
    public float y = 0.0f;
  }

  private ImmutableArray<Entity> entities;

  private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
  private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

  public void addedToEngine(Engine engine) {
    entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
  }

  public void update(float deltaTime) {
    StreamSupport.stream(entities.spliterator(), false).forEach((Entity entity) -> {
      final PositionComponent position = pm.get(entity);
      final VelocityComponent velocity = vm.get(entity);

      position.x += velocity.x * deltaTime;
      position.y += velocity.y * deltaTime;
    });
  }
}
