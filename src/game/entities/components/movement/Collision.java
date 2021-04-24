package game.entities.components.movement;

import game.entities.Entity;
import game.entities.hitbox.HitBoxDirection;
import util.Vec2D;

public class Collision {
	private Entity collider;
	private Vec2D oldPosition, collisionPosition;
	private HitBoxDirection direction;
	private double velocity;
	private boolean source;

	public Collision(Entity collider, Vec2D oldPosition, Vec2D collisionPosition, HitBoxDirection direction, double velocity) {
		this.collider = collider;
		this.oldPosition = oldPosition;
		this.collisionPosition = collisionPosition;
		this.direction = direction;
		this.velocity = velocity;
		this.source = true;
	}

	public Collision(Entity collider, HitBoxDirection direction, double velocity) {
		this.collider = collider;
		this.direction = direction;
		this.velocity = velocity;
		source = false;
	}

	public Entity getCollider() {
		return collider;
	}

	public boolean isSource() {
		return source;
	}

	public double getVelocity() {
		return velocity;
	}

	public HitBoxDirection getDirection() {
		return direction;
	}

	public Vec2D getCollisionPosition() {
		return collisionPosition;
	}

	public Vec2D getOldPosition() {
		return oldPosition;
	}
}
