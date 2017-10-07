package se.mejsla.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public final class MovementComponent implements Component {
  public final Vector2 velocity = new Vector2();
	public final Vector2 accel = new Vector2();
}
