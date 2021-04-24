package game.entities;

import game.entities.components.*;
import game.entities.components.graphics.RectGraphicsComponent;
import game.entities.components.movement.*;
import game.entities.components.listener.CollisionListener;

import java.awt.*;

public class Enemy extends Entity {

	public Enemy() {
		super(3);
		this.addComponent(new PositionComponent(this, 200, 200));
		this.addComponent(new RectGraphicsComponent(this, Color.RED));
		this.addComponent(new TopDownMovementComponent(this, 3));
		this.addComponent(new HealthComponent(this, 5));
		this.addComponent(new CollisionComponent(this));
		this.addComponent(new KnockbackComponent(this));
		this.addComponent(new CollisionListener(this) {
			@Override
			public void onCollide(Collision c) {
				Entity collider = c.getCollider();

				if(collider instanceof Player) {
					if(collider.hasComponent(HealthComponent.class)) {
						collider.getComponent(HealthComponent.class).damage(4);
					}
				}
			}
		});
	}
}
