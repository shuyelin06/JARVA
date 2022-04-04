package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public class Poison extends Condition {

	private float cooldown;
	//number of seconds until poison tick
	final private float TOTAL_COOLDOWN = 1.5f;
	
	public Poison(GameObject owner, Unit target, float timer) {
		super(owner, target, timer);
	}

	@Override
	public void applyEffect(Unit target) {

		cooldown -= Game.TicksPerFrame();
		
		if (cooldown < 0) {
			target.setHealth(target.getCurHealth() - target.getMaxHealth() * 0.05f);
			cooldown = TOTAL_COOLDOWN;
		}
		
	}

}
