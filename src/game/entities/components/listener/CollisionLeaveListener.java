package game.entities.components.listener;

import game.Game;
import game.entities.Entity;
import game.entities.components.Component;
import game.entities.components.CollisionComponent;

import java.util.List;
import java.util.ArrayList;

public abstract class CollisionLeaveListener extends Component {

	private List<Entity> collidingEntities;
	public CollisionLeaveListener(Entity e) {
		super(e);
		collidingEntities = new ArrayList<>();
	}

	@Override
	public void update(Game game) {
		List<Entity> toRemove = new ArrayList<>();
		for (Entity collidingEntity : collidingEntities) {
			if (!entity.getComponent(CollisionComponent.class).getHitbox().collides(collidingEntity.getComponent(CollisionComponent.class).getHitbox())) {
				toRemove.add(collidingEntity);
			}
		}

		for(Entity e: toRemove) {
			onCollisionLeave(e);
			collidingEntities.remove(e);
		}
	}

	public void addCollidingEntity(Entity e) {
		if(e == entity || collidingEntities.contains(e)) return;
		collidingEntities.add(e);
	}

	public abstract void onCollisionLeave(Entity e);

	@Override
	public float getPriority() {
		return 0;
	}
}
