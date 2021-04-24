package game.entities;

import game.entities.components.graphics.RectGraphicsComponent;
import game.entities.components.PositionComponent;
import game.entities.components.CollisionComponent;
import game.entities.hitbox.HitBox;

import java.awt.*;

public class Wall extends Entity {

	public Wall(double x, double y) {
		super(10);
		this.addComponent(new PositionComponent(this, x, y));
		this.addComponent(new CollisionComponent(this, HitBox.HitBoxType.BLOCKING));
		this.addComponent(new RectGraphicsComponent(this, Color.BLACK));
	}

	public Wall(double x, double y, double w, double h) {
		super(10);
		this.addComponent(new PositionComponent(this, x, y, w, h));
		this.addComponent(new CollisionComponent(this, HitBox.HitBoxType.BLOCKING));
		this.addComponent(new RectGraphicsComponent(this, Color.BLACK));
	}

}
