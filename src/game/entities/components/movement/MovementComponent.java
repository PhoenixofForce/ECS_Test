package game.entities.components.movement;

import game.Game;
import game.entities.Entity;
import game.entities.components.Component;
import game.entities.components.PositionComponent;
import game.entities.components.CollisionComponent;
import game.entities.components.listener.MoveListener;
import util.Vec2D;

public class MovementComponent extends Component {

	protected double maxSpeed;
	protected double vx, vy;
	private double lastVX, lastVY;
	private Vec2D lastMovement;

	public MovementComponent(Entity e, double maxSpeed) {
		super(e);
		this.maxSpeed = maxSpeed;
	}

	@Override
	public void update(Game game) {
		KnockbackComponent kc = entity.getComponent(KnockbackComponent.class);
		Vec2D vel = new Vec2D(vx, vy);
		Vec2D normVel = vel.clone().normalize(maxSpeed);

		this.vx = normVel.x ;
		this.vy = normVel.y;

		double vx_ = vx;
		vx = 0;
		if(kc != null) vy += kc.getKY();
		move(game);
		vx += vx_;
		if(kc != null) vy -= kc.getKY();

		double vy_ = vy;
		vy = 0;
		if(kc != null) vx += kc.getKX();
		move(game);
		vy += vy_;
		if(kc != null) vx -= kc.getKX();

		this.vx = vel.x;
		this.vy = vel.y;

		if(vx != 0 || vy != 0) {
			lastVX = vx;
			lastVY = vy;
		}
	}

	public double getVX() {
		return vx;
	}

	public void setVX(double vx) {
		this.vx = vx;
	}

	public double getVY() {
		return vy;
	}

	public void setVY(double vy) {
		this.vy = vy;
	}

	public double getLastVX() {
		return lastVX;
	}

	public double getLastVY() {
		return lastVY;
	}

	private void move(Game game) {
		move(game, vx, vy);
	}

	public void move(Game game, double vx, double vy) {
		PositionComponent pc = entity.getComponent(PositionComponent.class);
		Vec2D vel = new Vec2D(vx, vy);

		if(pc != null) {
			if(entity.hasComponent(CollisionComponent.class)) {
				vel = entity.getComponent(CollisionComponent.class).collides(game, vel).getNewVelocity();
				entity.getComponent(CollisionComponent.class).getHitbox().move(vel.x, vel.y);
			}

			lastMovement = vel;
			pc.add(vel);
			if(entity.hasComponent(MoveListener.class)) entity.getComponent(MoveListener.class).onMove(game, vel);
		}
	}

	public Vec2D getLastMovement() {
		return lastMovement;
	}
}
