package game.entities.components.graphics;

import game.entities.Entity;
import game.entities.components.HealthComponent;
import game.entities.components.LinkPositionComponent;
import game.entities.components.PositionComponent;
import util.MathUtil;

import java.awt.*;

public class HealthGraphicsComponent extends GraphicsComponent {

	private HealthComponent hc;
	private PositionComponent pos;

	public HealthGraphicsComponent(Entity e, Entity follow, double x, double y, double w, double h) {
		super(e);
		this.hc = follow.getComponent(HealthComponent.class);

		pos = new PositionComponent(null, x, y, w, h);
	}

	public HealthGraphicsComponent(Entity e, Entity follow) {
		super(e);
		this.hc = follow.getComponent(HealthComponent.class);

		pos = new LinkPositionComponent(null, follow, 32, 8, -8, -10);
	}

	public HealthGraphicsComponent(Entity e) {
		super(e);
		this.hc = e.getComponent(HealthComponent.class);

		pos = new LinkPositionComponent(null, e, 32, 8, -8, -10);
	}

	@Override
	public void draw(Graphics g) {
		Color c = hc.isInvincible()? Color.YELLOW:  Color.RED;
		g.setColor(c.darker().darker());
		g.fillRect(pos.getX(), pos.getY(), pos.getW(), pos.getH());

		double healthWidth = MathUtil.map(hc.getCurrentHealth().getValue(), 0, hc.getMaxHealth(), 0, pos.getSize().x);
		g.setColor(c.darker());
		g.fillRect(pos.getX(), pos.getY(), (int) healthWidth, pos.getH());

		healthWidth = MathUtil.map(hc.getCurrentHealth().getActualValue(), 0, hc.getMaxHealth(), 0, pos.getSize().x);
		g.setColor(c);
		g.fillRect(pos.getX(), pos.getY(), (int) healthWidth, pos.getH());
	}
}
