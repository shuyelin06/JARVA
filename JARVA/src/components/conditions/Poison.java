package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public class Poison extends Condition {
	//number of seconds until poison tick
	final private float TOTAL_COOLDOWN = 1.5f;
	private float cooldown;
	
	public Poison(Unit owner) {
		super(owner);
	}

	public void removeEffect() {}
	public void applyEffect() {

		cooldown -= Game.TicksPerFrame();
		
		if (cooldown < 0) {
			owner.setHealth(owner.getCurHealth() - owner.getMaxHealth() * 0.05f);
			cooldown = TOTAL_COOLDOWN;
		}
		
	}

}
