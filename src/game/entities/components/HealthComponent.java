package game.entities.components;

import game.Game;
import game.entities.Entity;

public class HealthComponent extends Component {

	private CooldownComponent cooldown;

	private int maxHealth;
	private int currentHealth;

	private int invincibleTicks = 0;

	public HealthComponent(Entity e, int maxHealth) {
		super(e);
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;

		this.cooldown = new CooldownComponent(10);
	}

	public int getCurrentHealth() {
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
			this.currentHealth -= damage;
			cooldown.consume();
		}
	}

	public void heal(int toHeal) {
		this.currentHealth = Math.max(maxHealth, currentHealth + toHeal);
	}

	public void overheal(int toHeal) {
		this.currentHealth = currentHealth + toHeal;
	}

	public void heal() {
		this.currentHealth = maxHealth;
	}

	public void increaseMaxHealth(int toAdd) {
		this.maxHealth += toAdd;
	}

	@Override
	public void update(Game game) {
		cooldown.update(game);

		if(currentHealth <= 0) {
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
