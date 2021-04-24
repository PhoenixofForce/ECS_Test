package game.actions;

import game.Game;
import game.entities.Entity;
import game.entities.components.CooldownComponent;
import util.Vec2D;

public abstract class Action {

	private boolean activated;
	private CooldownComponent cp;

	public Action(int cooldown) {
		this(cooldown, 1, true);
	}

	public Action(int cooldown, boolean activated) {
		cp = new CooldownComponent(cooldown);
		this.activated = activated;
	}

	public Action(int cooldown, int charges) {
		this(cooldown, charges, true);
	}

	public Action(int cooldown, int charges, boolean activated) {
		cp = new CooldownComponent(cooldown, charges);
		this.activated = activated;
	}

	public void execute(Game game, Entity user, Vec2D direction) {
		if(isUseable() && activated) {
			executeAction(game, user, direction);
			cp.consume();
		}
	}

	protected abstract void executeAction(Game game, Entity user, Vec2D direction);

	public void update(Game game) {
		cp.update(game);
	}

	public void setActivated(boolean act) {
		this.activated = act;
	}

	public boolean isUseable() {
		return cp.canConsume();
	}
}
