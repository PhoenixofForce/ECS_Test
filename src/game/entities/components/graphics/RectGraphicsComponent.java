package game.entities.components.graphics;

import game.entities.Entity;
import game.entities.components.PositionComponent;

import java.awt.*;

public class RectGraphicsComponent extends GraphicsComponent {

	private Color color;
	public RectGraphicsComponent(Entity e, Color c) {
		super(e);
		this.color = c;
	}

	@Override
	public void draw(Graphics g) {
		if(entity.hasComponent(PositionComponent.class)) {
			PositionComponent pc = entity.getComponent(PositionComponent.class);

			g.setColor(this.color);
			g.fillRect(pc.getX(), pc.getY(), pc.getW(), pc.getH());
		}
	}
}
