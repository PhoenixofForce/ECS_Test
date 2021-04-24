package game.entities;

import game.Game;
import game.entities.components.*;
import game.entities.components.graphics.RectGraphicsComponent;
import game.entities.components.movement.Collision;
import game.entities.components.listener.CollisionListener;
import game.entities.components.movement.KnockbackComponent;
import game.entities.components.movement.MovementComponent;
import util.Vec2D;

import java.awt.*;

public class MeleeAttack extends Entity {

	private MeleeAttack instance;
	private CooldownComponent timeToDie;

	public MeleeAttack(Entity attacker, Vec2D dir) {
		super(1);
		instance = this;

		double x, y, w, h;
		if(dir.x != 0 && dir.y != 0) {
			w = 32;
			h = 32;

			x = (dir.x - 1) / 2.0 * 16;
			y = (dir.y - 1) / 2.0 * 16;
		} else {
			if(dir.x != 0) {
				h = 16 * 3;
				w = 16;
				x = dir.x * 16;
				y = -16;
			} else {
				w = 16 * 3;
				h = 16;
				y = dir.y * 16;
				x = -16;
			}
		}

		this.addComponent(new LinkPositionComponent(this, attacker, w, h, x, y));
		this.addComponent(new RectGraphicsComponent(this, Color.GRAY));
		this.addComponent(new CollisionComponent(this));
		this.addComponent(new CollisionListener(this) {
			@Override
			public void onCollide(Collision c) {
				Entity collider = c.getCollider();
				if(collider != instance && collider != attacker) {
					if(collider.hasComponent(HealthComponent.class))
						collider.getComponent(HealthComponent.class).damage(3);

					if(collider.hasComponent(KnockbackComponent.class)) {
						KnockbackComponent kc = collider.getComponent(KnockbackComponent.class);
						Vec2D positionDifference = collider.getComponent(PositionComponent.class).getPos().clone().sub(entity.getComponent(PositionComponent.class).getPos());

						MovementComponent mc = attacker.getComponent(MovementComponent.class);
						Vec2D movementSpeed = mc.getLastMovement().div(2);

						Vec2D knockback = positionDifference.normalize(3).add(movementSpeed);
						System.out.println(knockback);

						kc.addKnockback(knockback);
					}
				}
			}
		});

		timeToDie = new CooldownComponent(10);
	}

	@Override
	public void update(Game game) {
		super.update(game);
		timeToDie.update(game);
		if(timeToDie.canConsume()) {
			game.removeEntity(this);
		}
	}
}
