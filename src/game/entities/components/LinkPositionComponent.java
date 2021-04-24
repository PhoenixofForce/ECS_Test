package game.entities.components;

import game.entities.Entity;
import util.Vec2D;

public class LinkPositionComponent extends PositionComponent {

	private Entity following;
	private Vec2D off;
	public LinkPositionComponent(Entity e, Entity follow, double w, double h, double xOff, double yOff) {
		super(e, 0, 0, w, h);
		this.following = follow;
		this.off = new Vec2D(xOff, yOff);
	}

	@Override
	public Vec2D getPos() {
		return following.getComponent(PositionComponent.class).getPos().clone().add(off);
	}
}
