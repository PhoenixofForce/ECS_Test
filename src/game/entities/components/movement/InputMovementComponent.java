package game.entities.components.movement;

import game.Game;
import game.actions.DashAction;
import game.actions.MeleeAttackAction;
import game.actions.ShootProjectileAction;
import game.entities.Entity;
import game.entities.components.ActionComponent;
import util.Vec2D;

import java.util.HashMap;
import java.util.Map;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputMovementComponent extends MovementComponent implements KeyListener {

	private Game game;
	private Map<Integer, Boolean> pressedKeys;
	public InputMovementComponent(Game g, Entity e, double maxSpeed) {
		super(e, maxSpeed);
		this.pressedKeys = new HashMap<>();

		this.game = g;
		g.getWindow().addKeyListener(this);
	}

	@Override
	public void update(Game game) {
		int vx = 0, vy = 0;

		if(isKeyPressed(KeyEvent.VK_A)) vx -= 1;
		if(isKeyPressed(KeyEvent.VK_D)) vx += 1;

		if(isKeyPressed(KeyEvent.VK_S)) vy += 1;
		if(isKeyPressed(KeyEvent.VK_W)) vy -= 1;

		this.setVX(vx);
		this.setVY(vy);
		super.update(game);

		if(entity.hasComponent(ActionComponent.class)) {
			ActionComponent ac = entity.getComponent(ActionComponent.class);

			if(isKeyPressed(KeyEvent.VK_SPACE)) {
				if(ac.hasAction(DashAction.class) )ac.getAction(DashAction.class).execute(game, entity, new Vec2D());
			}

			if(ac.hasAction(ShootProjectileAction.class)) {
				int pvx = 0, pvy = 0;

				if(isKeyPressed(KeyEvent.VK_LEFT)) pvx -= 1;
				if(isKeyPressed(KeyEvent.VK_RIGHT)) pvx += 1;
				if(isKeyPressed(KeyEvent.VK_UP)) pvy -= 1;
				if(isKeyPressed(KeyEvent.VK_DOWN)) pvy += 1;

				if(pvx != 0 || pvy != 0) ac.getAction(ShootProjectileAction.class).execute(game, entity, new Vec2D(pvx, pvy));
			}

			if(ac.hasAction(MeleeAttackAction.class)) {
				int pvx = 0, pvy = 0;

				if(isKeyPressed(KeyEvent.VK_LEFT)) pvx -= 1;
				if(isKeyPressed(KeyEvent.VK_RIGHT)) pvx += 1;
				if(isKeyPressed(KeyEvent.VK_UP)) pvy -= 1;
				if(isKeyPressed(KeyEvent.VK_DOWN)) pvy += 1;

				if(pvx != 0 || pvy != 0) ac.getAction(MeleeAttackAction.class).execute(game, entity, new Vec2D(pvx, pvy));
			}
		}
	}

	@Override
	public void onRemove() {
		game.getWindow().removeKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.put(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.put(e.getKeyCode(), false);
	}

	private boolean isKeyPressed(int key) {
		return pressedKeys.getOrDefault(key, false);
	}
}
