package se.mejsla.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.mejsla.game.components.AnimationComponent;
import se.mejsla.game.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {
  private final ComponentMapper<TextureComponent> tm;
  private final ComponentMapper<AnimationComponent> am;

  public AnimationSystem() {
    super(Family.all(TextureComponent.class, AnimationComponent.class).get());

    tm = ComponentMapper.getFor(TextureComponent.class);
    am = ComponentMapper.getFor(AnimationComponent.class);
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
  }
}
