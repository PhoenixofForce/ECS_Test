package game.entities.components.movement;

import game.Game;
import game.entities.Entity;
import util.Noise;

public class NoiseMovement extends MovementComponent {

	private int counter = 0;
	public NoiseMovement(Entity e, double maxSpeed) {
		super(e, maxSpeed);
	}

	@Override
	public void update(Game game) {
		setVX(Noise.noise(83212, counter, 32) * 2 - 1);
		setVY(Noise.noise(49275, counter, 32) * 2 - 1);
		counter++;

		super.update(game);
	}
}
