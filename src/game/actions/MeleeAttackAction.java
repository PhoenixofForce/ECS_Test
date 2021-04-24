package game.actions;

import game.Game;
import game.entities.Entity;
import game.entities.MeleeAttack;
import game.entities.components.movement.MovementComponent;
import util.Vec2D;

public class MeleeAttackAction extends Action {

	public MeleeAttackAction() {
		super(30);
	}

	@Override
	protected void executeAction(Game game, Entity user, Vec2D dir) {
		if(dir.length() == 0) {
			dir = new Vec2D(user.getComponent(MovementComponent.class).getLastVX(), user.getComponent(MovementComponent.class).getLastVX());
		}
		game.addEntity(new MeleeAttack(user, dir));
	}
}
