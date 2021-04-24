package game.entities;

import game.entities.components.graphics.HealthGraphicsComponent;

public class UI extends Entity {

	public UI(Player player) {
		super(-10);
		this.addComponent(new HealthGraphicsComponent(this, player, 16, 16, 64, 16));

	}


}
