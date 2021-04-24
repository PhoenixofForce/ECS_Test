package game.entities.components.movement;

import game.Game;
import game.entities.Entity;
import game.entities.components.Component;
import util.Vec2D;

public class KnockbackComponent extends Component {

	private double kx, ky;
	public KnockbackComponent(Entity e) {
		super(e, 1);
	}

	@Override
	public void update(Game game) {
		kx *= 0.95f;
		ky *= 0.95f;
	}

	public double getKX() {
		return kx;
	}

	public double getKY() {
		return ky;
	}

	public void addKnockback(Vec2D toAdd) {
		this.kx += toAdd.x;
		this.ky += toAdd.y;
	}

	public void setKnockback(double kx, double ky) {
		this.kx = kx;
		this.ky = ky;
	}
}
