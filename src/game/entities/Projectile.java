package game.entities;

import game.Game;
import game.entities.components.*;
import game.entities.components.graphics.OvalGraphicsComponent;
import game.entities.components.listener.CollisionListener;
import game.entities.components.movement.Collision;
import game.entities.components.movement.DirectionMovementComponent;
import util.Vec2D;

import java.awt.*;

public class Projectile extends Entity {

	private Entity shooter;
	private Entity instance;

	public Projectile(Game game, Entity shooter, Vec2D pos, double vx, double vy) {
		instance = this;
		this.shooter = shooter;

		this.addComponent(new PositionComponent(this, pos.x, pos.y, 8));
		this.addComponent(new DirectionMovementComponent(this, vx, vy, 10));
		this.addComponent(new CollisionComponent(this));
		this.addComponent(new OvalGraphicsComponent(this, Color.GREEN));
		this.addComponent(new CollisionListener(this) {
			@Override
			public void onCollide(Collision c) {
				Entity collider = c.getCollider();

				if(collider instanceof Wall) game.removeEntity(instance);
				else if((collider instanceof Enemy || collider instanceof Player) && !(collider == shooter)) {
					collider.getComponent(HealthComponent.class).damage(2);
					game.removeEntity(instance);
				}
			}
		});
	}
}
