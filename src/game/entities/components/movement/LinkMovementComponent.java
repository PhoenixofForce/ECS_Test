package game.entities.components.movement;

import game.Game;
import game.entities.Entity;
import game.entities.components.Component;
import game.entities.components.listener.MoveListener;
import util.Vec2D;

public class LinkMovementComponent extends Component {

	private Entity following;
	private MoveListener ml;

	public LinkMovementComponent(Entity e, Entity following) {
		super(e);
		this.following = following;

		ml = new MoveListener(following) {
			@Override
			public void onMove(Game game, Vec2D dir) {
				e.getComponent(MovementComponent.class).move(game, dir.x, dir.y);
			}
		};

		following.addComponent(ml);
	}

	@Override
	public void update(Game game) {}

	@Override
	public void onRemove() {
		following.removeComponent(ml);
	}

	@Override
	public float getPriority() {
		return 0;
	}

}
