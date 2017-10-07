package se.mejsla.game.systems;

import java.util.Comparator;
import java.util.stream.Stream;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import se.mejsla.game.components.TextureComponent;
import se.mejsla.game.components.TransformComponent;

public final class RenderingSystem extends IteratingSystem {
  static final float FRUSTUM_WIDTH = 10;
  static final float FRUSTUM_HEIGHT = 15;
  static final float PIXELS_TO_METRES = 1.0f / 32.0f;

  private final SpriteBatch batch;
  private final OrthographicCamera cam;

  private final ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
  private final ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);

  private final Array<Entity> renderQueue = new Array<>();

  private final Comparator<Entity> comparator = (Entity entityA,
      Entity entityB) -> (int) Math.signum(transformMapper.get(entityB).pos.z - transformMapper.get(entityA).pos.z);

  public RenderingSystem(final SpriteBatch batch) {
    super(Family.all(TransformComponent.class, TextureComponent.class).get());

    this.batch = batch;
    cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
    cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);
    renderQueue.sort(comparator);

    cam.update();
    batch.setProjectionMatrix(cam.combined);
    batch.begin();

    renderQueue.forEach((Entity entity) -> {
      final TextureComponent tex = textureMapper.get(entity);

      if (tex.region == null) {
        return;
      }

      final TransformComponent t = transformMapper.get(entity);
      final float width = tex.region.getRegionWidth();
      final float height = tex.region.getRegionHeight();
      final float originX = width * 0.5f;
      final float originY = height * 0.5f;

      batch.draw(tex.region, t.pos.x - originX, t.pos.y - originY, originX, originY, width, height,
          t.scale.x * PIXELS_TO_METRES, t.scale.y * PIXELS_TO_METRES, MathUtils.radiansToDegrees * t.rotation);
    });

    batch.end();
    renderQueue.clear();
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    renderQueue.add(entity);
  }

  public OrthographicCamera getCamera() {
    return cam;
  }
}
