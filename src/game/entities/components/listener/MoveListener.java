package game.entities.components.listener;

import game.Game;
import game.entities.Entity;
import game.entities.components.Component;
import util.Vec2D;

public abstract class MoveListener extends Component {

	public MoveListener(Entity e) {
		super(e);
	}

	public abstract void onMove(Game game, Vec2D dir);

	@Override
	public void update(Game game) {}
}
