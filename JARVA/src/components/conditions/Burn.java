package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public class Burn extends Condition {

	private float cooldown;
	//number of seconds until burn tick
	final private float TOTAL_COOLDOWN = 1;
	
	public Burn(GameObject owner, Unit target, float timer) {
		super(owner, target, timer);
	}

	@Override
	public void applyEffect(Unit target) {
		
		cooldown -= Game.TicksPerFrame();
		
		if (cooldown < 0) {
			target.setHealth(target.getCurHealth() * 0.99f);
			cooldown = TOTAL_COOLDOWN;
		}
		
	}
	
}
