package game.entities.components.graphics;

import game.Game;
import game.entities.Entity;
import game.entities.components.Component;
import game.entities.components.PositionComponent;

import java.awt.*;

public abstract class GraphicsComponent extends Component {

	public GraphicsComponent(Entity e) {
		super(e, 10);
	}

	@Override
	public void update(Game game) {
		draw(game.getGraphics());
	}

	public abstract void draw(Graphics g);
}
