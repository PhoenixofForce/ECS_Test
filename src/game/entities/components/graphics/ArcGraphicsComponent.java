package game.entities.components.graphics;

import game.entities.Entity;
import game.entities.components.PositionComponent;

import java.awt.*;

public class ArcGraphicsComponent extends GraphicsComponent {

	private Color c;
	private double angle, startingAngle;

	public ArcGraphicsComponent(Entity e, Color c) {
		super(e);
		this.c = c;
		this.angle = 0;
		this.startingAngle = 0;
	}

	@Override
	public void draw(Graphics g) {
		PositionComponent pc = entity.getComponent(PositionComponent.class);
		int x = pc.getX(),
				y = pc.getY(),
				w = pc.getW(),
				h = pc.getH();

		g.setColor(c);
		g.fillArc(x, y, w, h, (int) startingAngle, (int) angle);
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setStartingAngle(double angle) {
		this.startingAngle = angle;
	}
}
