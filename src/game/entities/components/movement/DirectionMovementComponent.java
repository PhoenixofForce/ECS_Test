package game.entities.components.movement;

import game.entities.Entity;

public class DirectionMovementComponent extends MovementComponent {

	public DirectionMovementComponent(Entity e, double vx, double vy, double maxSpeed) {
		super(e, maxSpeed);
		this.setVX(vx);
		this.setVY(vy);
	}
}
