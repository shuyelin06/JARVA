package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public class Burn extends Condition {

	private float cooldown;
	//number of seconds until burn tick
	final private float TOTAL_COOLDOWN = 1;
	
	public Burn(Unit owner) {
		super(owner);
	}

	public void removeEffect() {}
	public void applyEffect() {
		cooldown -= Game.TicksPerFrame();
		
		if (cooldown < 0) {
			owner.setHealth(owner.getCurHealth() * 0.99f);
			cooldown = TOTAL_COOLDOWN;
		}
	}
	
	
}
