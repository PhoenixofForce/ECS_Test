package game.actions;

import game.Game;
import game.entities.Entity;
import game.entities.components.HealthComponent;
import game.entities.components.movement.KnockbackComponent;
import game.entities.components.movement.MovementComponent;
import util.Vec2D;

public class DashAction extends Action {

	public DashAction() {
		this(true);
	}

	public DashAction(boolean activated) {
		super(120, 3, activated);
	}

	@Override
	protected void executeAction(Game game, Entity user, Vec2D dir) {
		if(user.hasComponent(KnockbackComponent.class) && user.hasComponent(MovementComponent.class)) {
			KnockbackComponent kc = user.getComponent(KnockbackComponent.class);
			MovementComponent mc = user.getComponent(MovementComponent.class);
			user.getComponent(HealthComponent.class).addInvinvibility(20);

			Vec2D knockback = dir;
			if(dir.length() == 0) knockback = new Vec2D(mc.getLastVX(), mc.getLastVY());
			knockback.normalize(20);

			kc.addKnockback(knockback);
		}
	}
}
