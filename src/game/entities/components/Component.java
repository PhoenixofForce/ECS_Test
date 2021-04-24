package game.entities.components;

import game.Game;
import game.entities.Entity;

public abstract class Component {

	private boolean activated;
	protected Entity entity;
	private float priority;

	public Component(Entity e) {
		this(e, 0);
	}
	public Component(Entity e, float priority) {
		this.entity = e;
		this.priority = priority;
		activated = true;
	}

	public abstract void update(Game game);
	public void onRemove() {}

	public float getPriority() {
		return priority;
	}

	public void enable() {
		this.activated = true;
	}

	public void disable() {
		this.activated = false;
	}

	public boolean isActivated() {
		return activated;
	}
}
