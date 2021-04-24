package game.entities.components;

import game.Game;
import game.entities.Entity;

public class CooldownComponent extends Component {

	private int ticksSinceLastUse = 0;
	private int cooldown;

	private int currentCharges = 0;
	private int maxCharges;

	private int chargeCooldown = 10;
	private int ticksSinceLastCharge = 0;

	public CooldownComponent( int cooldown) {
		this(cooldown, 1);
	}

	public CooldownComponent( int cooldown, int maxCharges) {
		super(null);
		this. cooldown = cooldown;
		this.maxCharges = maxCharges;
	}

	public CooldownComponent(Entity e, int cooldown) {
		super(e);
		this. cooldown = cooldown;
		this.maxCharges = 1;
	}

	public void consume() {
		if(canConsume()) {
			if(currentCharges == maxCharges) this.ticksSinceLastUse = 0;
			ticksSinceLastCharge = 0;
			currentCharges--;
		}
	}

	public boolean canConsume() {
		return currentCharges > 0 && chargeCooldown == ticksSinceLastCharge;
	}

	@Override
	public void update(Game game) {
		ticksSinceLastUse = Math.min(ticksSinceLastUse + 1, cooldown);
		ticksSinceLastCharge = Math.min(ticksSinceLastCharge + 1, chargeCooldown);

		if(ticksSinceLastUse == cooldown) {
			currentCharges = Math.min(currentCharges + 1, maxCharges);
			this.ticksSinceLastUse = 0;
		}
	}

	@Override
	public String toString() {
		return "cooldown(" + ticksSinceLastUse + "/" + cooldown + " | " + currentCharges + "/" + maxCharges + ")";
	}
}
