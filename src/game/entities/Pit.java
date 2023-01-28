package game.entities;

import game.Game;
import game.entities.components.*;
import game.entities.components.graphics.GraphicsComponent;
import game.entities.components.graphics.RectGraphicsComponent;
import game.entities.components.listener.CollisionListener;
import game.entities.components.movement.Collision;
import game.entities.components.movement.KnockbackComponent;
import game.entities.components.movement.MovementComponent;
import util.Vec2D;

import java.awt.*;

import java.util.*;
import java.util.List;

public class Pit extends Entity {

	private Game game;
	private Map<Entity, CooldownComponent> cooldowns;

	private List<Vec2D> safeSpots;

	public Pit(Game game) {
		super(9);

		this.game = game;
		cooldowns = new HashMap<>();
		safeSpots =  new ArrayList<>();
		safeSpots.add(new Vec2D(400, 380));

		this.addComponent(new PositionComponent(this, 16, 420, 800 - 32, 60));
		this.addComponent(new RectGraphicsComponent(this, Color.GRAY));
		this.addComponent(new CollisionComponent(this));
		this.addComponent(new CollisionListener(this) {
			@Override
			public void onCollide(Collision c) {
				if(c.isSource()) {
					Entity collider = c.getCollider();

					if(collider.hasComponent(HealthComponent.class) && !collider.hasTag("onPlatform") && !collider.hasTag("inPit")) {
						HealthComponent hc = collider.getComponent(HealthComponent.class);
						hc.damage((int) (hc.getCurrentHealth().getValue() / 2), true);

						Vec2D dir = c.getOldPosition().clone().sub(c.getCollisionPosition());
						dropIntoPit(collider, dir.normalize(32));
					}
				}
			}
		});
	}

	@Override
	public void update(Game game) {
		super.update(game);

		List<Entity> toRemove = new ArrayList();
		for(Entity e: cooldowns.keySet()) {
			CooldownComponent c = cooldowns.get(e);
			c.update(game);

			if(c.canConsume()) {
				toRemove.add(e);
				removeFromPit(e);
			}
		}

		for(Entity e: toRemove) cooldowns.remove(e);
	}

	private void dropIntoPit(Entity e, Vec2D dir) {
		e.addTag("inPit");
		e.getComponent(MovementComponent.class).move(game, dir.x, 0);
		e.getComponent(MovementComponent.class).move(game, 0, dir.y);

		e.getComponent(GraphicsComponent.class).disable();
		e.getComponent(MovementComponent.class).disable();
		e.getComponent(CollisionComponent.class).disable();
		//e.getComponent(HealthComponent.class).disable();
		if(e.hasComponent(KnockbackComponent.class)) {
			KnockbackComponent kc = e.getComponent(KnockbackComponent.class);
			kc.setKnockback(0, 0);
		}

		cooldowns.put(e, new CooldownComponent(60));
	}

	private void removeFromPit(Entity e) {
		Vec2D ePos = e.getComponent(PositionComponent.class).getPos();
		if(e.getComponent(CollisionComponent.class).getHitbox().collides(getComponent(CollisionComponent.class).getHitbox())) {
			Vec2D safeSpot = safeSpots.stream().min(Comparator.comparingDouble(v -> v.distanceTo(ePos.clone()))).get().clone();
			e.getComponent(PositionComponent.class).setPos(safeSpot);
		} else {
			if(safeSpots.size() < 10) safeSpots.add(ePos.clone());
		}
		Vec2D newPos = e.getComponent(PositionComponent.class).getPos();
		e.getComponent(CollisionComponent.class).getHitbox().x = newPos.x;		//somehow they got desynced
		e.getComponent(CollisionComponent.class).getHitbox().y = newPos.y;

		e.getComponent(GraphicsComponent.class).enable();
		e.getComponent(CollisionComponent.class).enable();
		e.getComponent(MovementComponent.class).enable();
		e.getComponent(HealthComponent.class).enable();
		e.removeTag("inPit");
	}
}
