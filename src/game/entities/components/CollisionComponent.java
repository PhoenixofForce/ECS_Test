package game.entities.components;

import game.Game;
import game.entities.Entity;
import game.entities.components.movement.Collision;
import game.entities.components.movement.MovementComponent;
import game.entities.components.movement.KnockbackComponent;
import game.entities.components.listener.CollisionListener;
import game.entities.components.listener.CollisionLeaveListener;
import game.entities.hitbox.HitBox;
import game.entities.hitbox.HitBoxDirection;
import util.Vec2D;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CollisionComponent extends Component {

	private HitBox hitbox;
	public CollisionComponent(Entity e) {
		this(e, HitBox.HitBoxType.NOT_BLOCKING);
	}

	public CollisionComponent(Entity e, HitBox.HitBoxType type) {
		super(e, 1);
		if(entity.hasComponent(PositionComponent.class)) {
			PositionComponent pc = e.getComponent(PositionComponent.class);
			this.hitbox = new HitBox(pc.getPos().x, pc.getPos().y, pc.getSize().x, pc.getSize().y, type);
		}
	}

	public CollisionComponent(Entity e, HitBox hitbox) {
		super(e);
		this.hitbox = hitbox;
	}

	@Override
	public void update(Game game) {}

	public CollisionResponse collides(Game game) {
		if(entity.hasComponent(PositionComponent.class)) {
			return collides(game, new Vec2D(), false);
		}

		return new CollisionResponse();
	}

	public CollisionResponse collides(Game game, Vec2D velocityIn) {
		return collides(game, velocityIn, true);
	}

	public CollisionResponse collides(Game game, Vec2D velocityIn, boolean triggerEvents) {
		Vec2D v = velocityIn.clone();
		Vec2D pos = entity.getComponent(PositionComponent.class).getPos().clone().add(v);

		List<Entity> collider = game.getEntities().stream().filter(e -> e.hasComponent(CollisionComponent.class) && e.hasComponent(PositionComponent.class)).collect(Collectors.toList());

		List<Entity> collides = new ArrayList<>();
		List<HitBox> targetPositions = new ArrayList<>();
		List<HitBoxDirection> directions = new ArrayList<>();
		List<Double> velocities = new ArrayList<>();

		HitBox hitBox = getHitbox();
		HitBox targetLocation = new HitBox(pos.x, pos.y, this.hitbox.width, this.hitbox.height, this.hitbox.type);

		boolean isBlocking = false;
		for(Entity e: collider) {
			if(e == entity) continue;
			HitBox otherHitbox = e.getComponent(CollisionComponent.class).getHitbox();

			if (otherHitbox.collides(targetLocation)) {
				HitBoxDirection direction = hitBox.direction(otherHitbox);

				if(otherHitbox.type == HitBox.HitBoxType.BLOCKING) isBlocking = true;

				collides.add(e);
				directions.add(direction);
				targetPositions.add(targetLocation);

				if (direction == HitBoxDirection.COLLIDE || otherHitbox.type == HitBox.HitBoxType.NOT_BLOCKING) {
					velocities.add(0d);
					continue;
				}

				double ax = direction.getXDirection();
				double ay = direction.getYDirection();

				double distance = otherHitbox.collisionDepth(targetLocation, ax, ay);

				ax *= distance;
				ay *= distance;

				velocities.add(Math.sqrt(v.x * v.x + v.y * v.y));
				v.sub(ax, ay);

				targetLocation = hitBox.clone();
				targetLocation.move(v.x, v.y);
			}
		}

		MovementComponent mc = entity.getComponent(MovementComponent.class);
		KnockbackComponent kc = entity.getComponent(KnockbackComponent.class);
		for (int i = 0; i < directions.size(); i++) {
			HitBoxDirection direction = directions.get(i);
			if (velocities.get(i) == 0) continue;

			if (direction == HitBoxDirection.LEFT || direction == HitBoxDirection.RIGHT) {
				mc.setVX(0);
				if(kc != null) {
					kc.setKnockback(kc.getKX() * -0.5f, kc.getKY() * 0.75f);
				}
			} else if (direction == HitBoxDirection.UP || direction == HitBoxDirection.DOWN) {
				mc.setVY(0);
				if(kc != null) {
					kc.setKnockback(kc.getKX() * 0.75f, kc.getKY() * -0.5f);
				}
			}
		}

		CollisionListener occ = entity.getComponent(CollisionListener.class);
		for (int i = 0; i < collides.size(); i++) {
			Entity collisionObject = collides.get(i);
			HitBoxDirection direction = directions.get(i);
			double velocity = velocities.get(i);

			if(triggerEvents) {
				if(occ != null) occ.onCollide(new Collision(collisionObject, direction.invert(), velocity));
				if(collisionObject.hasComponent(CollisionListener.class)) {
					Vec2D tPos = new Vec2D(targetPositions.get(i).x, targetPositions.get(i).y);
					Collision c = new Collision(entity, entity.getComponent(PositionComponent.class).getPos().clone(), tPos, direction, velocity);

					collisionObject.getComponent(CollisionListener.class).onCollide(c);
				}
			}

			if(entity.hasComponent(CollisionLeaveListener.class)) entity.getComponent(CollisionLeaveListener.class).addCollidingEntity(collisionObject);
		}

		return new CollisionResponse(v, isBlocking, collides);
	}

	public HitBox getHitbox() {
		return hitbox;
	}

	public void setHitbox(HitBox box) {
		this.hitbox = box;
	}

	public static class CollisionResponse {

		private List<Entity> entities;
		private boolean isCollision, isBlocking;
		private Vec2D newVelocity;

		public CollisionResponse() {
			this.isCollision = false;
			this.isBlocking = false;
		}

		public CollisionResponse(Vec2D newVel, boolean isBlocking, List<Entity> colliders) {
			this.newVelocity = newVel;
			this.isCollision = true;
			this.isBlocking = isBlocking;

			this.entities = colliders;
		}

		public boolean isCollision() {
			return isCollision;
		}

		public Vec2D getNewVelocity() {
			return newVelocity;
		}

		public boolean isBlocking() {
			return isBlocking;
		}

		public List<Entity> getCollider() {
			return entities;
		}
	}
}
