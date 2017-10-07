package se.mejsla.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import se.mejsla.game.components.MovementComponent;
import se.mejsla.game.components.TransformComponent;

public class MovementSystem extends IteratingSystem {
  private Vector2 tmp = new Vector2();

  private final ComponentMapper<TransformComponent> tm;
  private final ComponentMapper<MovementComponent> mm;

  public MovementSystem() {
    super(Family.all(TransformComponent.class, MovementComponent.class).get());

    tm = ComponentMapper.getFor(TransformComponent.class);
    mm = ComponentMapper.getFor(MovementComponent.class);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    final TransformComponent pos = tm.get(entity);
    final MovementComponent mov = mm.get(entity);

    tmp.set(mov.accel).scl(deltaTime);
    mov.velocity.add(tmp);

    tmp.set(mov.velocity).scl(deltaTime);
    pos.pos.add(tmp.x, tmp.y, 0.0f);
    System.out.println("x: " + tmp.x + ", y: " + tmp.y);
  }
}
