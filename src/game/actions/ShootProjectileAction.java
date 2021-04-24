package game.actions;

import game.Game;
import game.entities.Entity;
import game.entities.Projectile;
import game.entities.components.PositionComponent;
import game.entities.components.movement.MovementComponent;
import util.Vec2D;

public class ShootProjectileAction extends Action {

	public ShootProjectileAction() {
		super(30);
	}

	@Override
	protected void executeAction(Game game, Entity user, Vec2D dir) {
		MovementComponent mc = user.getComponent(MovementComponent.class);
		PositionComponent pc = user.getComponent(PositionComponent.class);

		double dirX = (dir.x == 0 && dir.y == 0)? mc.getLastVX(): dir.x;
		double dirY = (dir.x == 0 && dir.y == 0)? mc.getLastVY(): dir.y;

		Projectile p = new Projectile(game, user, pc.getPos(), dirX, dirY);
		game.addEntity(p);
	}
}
