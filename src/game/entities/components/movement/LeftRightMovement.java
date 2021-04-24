package game.entities.components.movement;

import game.Game;
import game.entities.Entity;
import game.entities.Pit;
import game.entities.components.CollisionComponent;
import util.Vec2D;

public class LeftRightMovement extends MovementComponent {

	private int mx = 1;

	public LeftRightMovement(Entity e, double maxSpeed) {
		super(e, maxSpeed);
	}

	@Override
	public void update(Game game) {
		setVX(mx);
		super.update(game);

		if(entity.hasComponent(CollisionComponent.class)) {
			CollisionComponent cc = entity.getComponent(CollisionComponent.class);

			CollisionComponent.CollisionResponse c = cc.collides(game, new Vec2D(mx, 0));
			if(c.isCollision() && (c.isBlocking() || (!entity.hasTag("onPlatform") && c.getCollider().stream().anyMatch(e -> e instanceof Pit)))) mx *= -1;
		}
	}
}
