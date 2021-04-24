package game.entities.components.movement;

import game.Game;
import game.entities.Entity;
import game.entities.Pit;
import game.entities.components.CollisionComponent;
import util.Noise;
import util.Vec2D;

public class TopDownMovementComponent extends MovementComponent {

	private int my = 1;

	public TopDownMovementComponent(Entity e, double maxSpeed) {
		super(e, maxSpeed);
	}

	@Override
	public void update(Game game) {
		setVY(my);
		super.update(game);

		if (entity.hasComponent(CollisionComponent.class)) {
			CollisionComponent cc = entity.getComponent(CollisionComponent.class);

			CollisionComponent.CollisionResponse c = cc.collides(game, new Vec2D(0, my).normalize(maxSpeed),  false);
			if(c.isCollision() && (c.isBlocking() || (!entity.hasTag("onPlatform") && c.getCollider().stream().anyMatch(e -> e instanceof Pit)))) my *= -1;
		}
	}
}
