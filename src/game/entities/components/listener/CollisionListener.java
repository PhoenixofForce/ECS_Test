package game.entities.components.listener;

import game.Game;
import game.entities.Entity;
import game.entities.components.Component;
import game.entities.components.movement.Collision;

public abstract class CollisionListener extends Component {
	public CollisionListener(Entity e) {
		super(e);
	}

	public abstract void onCollide(Collision c);

	@Override
	public void update(Game game) {}
}
