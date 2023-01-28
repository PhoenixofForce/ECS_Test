package game.entities.components;

import data.Ease;
import data.SmoothFloat;
import game.Game;
import game.entities.Entity;

public class HealthComponent extends Component {

	private CooldownComponent cooldown;

	private int maxHealth;
	private SmoothFloat currentHealth;

	private int invincibleTicks = 0;

	public HealthComponent(Entity e, int maxHealth) {
		super(e);
		this.maxHealth = maxHealth;
		this.currentHealth = new SmoothFloat(Ease::easeInBounce, maxHealth);

		this.cooldown = new CooldownComponent(10);
	}

	public SmoothFloat getCurrentHealth() {
		return currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void damage(int damage) {
		damage(damage, false);
	}

	public void damage(int damage, boolean ignoreInvincible) {
		if(cooldown.canConsume() && (!isInvincible() || ignoreInvincible)) {
			this.currentHealth.subSmooth(damage);
			cooldown.consume();
		}
	}

	public void heal(int toHeal) {
		this.currentHealth.addSmooth(Math.min(toHeal, maxHealth - currentHealth.getActualValue()));

	}

	public void overheal(int toHeal) {
		currentHealth.addSmooth(toHeal);
	}

	public void heal() {
		this.currentHealth.setSmooth(maxHealth);
	}

	public void increaseMaxHealth(int toAdd) {
		this.maxHealth += toAdd;
	}

	@Override
	public void update(Game game) {
		cooldown.update(game);
		currentHealth.update();

		if(currentHealth.getValue() <= 0) {
			game.removeEntity(entity);
		}

		invincibleTicks = Math.max(0, invincibleTicks - 1);
	}

	public void addInvinvibility(int ticks) {
		this.invincibleTicks += ticks;
	}

	public boolean isInvincible() {
		return invincibleTicks > 0;
	}
}
