package game.entities;

import game.Game;
import game.actions.DashAction;
import game.actions.MeleeAttackAction;
import game.entities.components.*;
import game.entities.components.graphics.RectGraphicsComponent;
import game.entities.components.listener.CollisionListener;
import game.entities.components.movement.Collision;
import game.entities.components.movement.KnockbackComponent;
import game.entities.components.movement.InputMovementComponent;
import game.entities.hitbox.HitBox;
import util.Vec2D;

import java.awt.*;

public class Player extends Entity {

	public Player(Game game) {
		this.addComponent(new PositionComponent(this, 100, 100));
		this.addComponent(new RectGraphicsComponent(this, Color.BLUE));
		this.addComponent(new HealthComponent(this, 50));
		this.addComponent(new InputMovementComponent(game, this, 5));
		this.addComponent(new CollisionComponent(this));
		this.addComponent(new KnockbackComponent(this));
		this.addComponent(new ActionComponent(this, new MeleeAttackAction(), new DashAction()));
		this.addComponent(new CollisionListener(this) {
			@Override
			public void onCollide(Collision c) {
				Entity collider = c.getCollider();

				if(collider instanceof Enemy) {
					HitBox own = getComponent(CollisionComponent.class).getHitbox();
					HitBox other = collider.getComponent(CollisionComponent.class).getHitbox();

					double dx = (own.getCenterX() - other.getCenterX());
					double dy = (own.getCenterY() - other.getCenterY());
					double l = Math.sqrt(dx * dx + dy * dy);
					dx /= l;
					dy /= l;

					getComponent(KnockbackComponent.class).addKnockback(new Vec2D(0.4 * dx, 0.4 * dy));
				}
			}
		});
	}
}