package se.mejsla.game.systems;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import se.mejsla.game.components.CameraComponent;
import se.mejsla.game.components.TransformComponent;

public class CameraSystem extends IteratingSystem {

	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<CameraComponent> cm;

	public CameraSystem() {
		super(Family.all(CameraComponent.class).get());

		tm = ComponentMapper.getFor(TransformComponent.class);
		cm = ComponentMapper.getFor(CameraComponent.class);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		CameraComponent cam = cm.get(entity);

		if (cam.target == null) {
			return;
		}

		TransformComponent target = tm.get(cam.target);

		if (target == null) {
			return;
		}

		cam.camera.position.y = Math.max(cam.camera.position.y, target.pos.y);
	}
}
