package game.entities;

import game.entities.components.graphics.RectGraphicsComponent;
import game.entities.components.PositionComponent;
import game.entities.components.CollisionComponent;
import game.entities.components.listener.CollisionListener;
import game.entities.components.listener.CollisionLeaveListener;
import game.entities.components.movement.Collision;
import game.entities.components.movement.LeftRightMovement;
import game.entities.components.movement.MovementComponent;
import game.entities.components.movement.LinkMovementComponent;

import java.util.Map;
import java.util.HashMap;

import java.awt.*;

public class MovingPlatform extends Entity {

	private Map<Entity, LinkMovementComponent> movementComponents;

	public MovingPlatform() {
		super(9);
		movementComponents = new HashMap<>();

		this.addComponent(new PositionComponent(this, 50, 400, 100, 100));
		this.addComponent(new RectGraphicsComponent(this, Color.YELLOW));
		this.addComponent(new LeftRightMovement(this, 3));
		this.addComponent(new CollisionComponent(this));
		this.addComponent(new CollisionListener(this) {
			@Override
			public void onCollide(Collision c) {
				Entity collider = c.getCollider();
				if(collider.hasComponent(MovementComponent.class) && !movementComponents.containsKey(collider)) onCollision(collider);

			}
		});
		this.addComponent(new CollisionLeaveListener(this) {
			@Override
			public void onCollisionLeave(Entity e) {
				if(movementComponents.containsKey(e)) onNoCollision(e);
			}
		});

		this.addTag("onPlatform");
	}

	private void onCollision(Entity e) {
		e.addTag("onPlatform");
		LinkMovementComponent lmc = new LinkMovementComponent(e, this);
		e.addComponent(lmc);
		movementComponents.put(e, lmc);
	}

	private void onNoCollision(Entity e) {
		LinkMovementComponent lmc = movementComponents.remove(e);
		e.removeComponent(lmc);
		e.removeTag("onPlatform");
	}

}
