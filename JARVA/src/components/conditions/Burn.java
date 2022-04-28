package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public class Burn extends Condition {

	private float cooldown;
	//number of seconds until burn tick
	final private float TOTAL_COOLDOWN = 0.7f;
	
	public Burn(Unit owner) {
		super(owner);
	}

	@Override
	public void addTimer(float time) {
		//reset the timer instead of adding
		this.timer = time;
	}
	
	public void removeEffect() {}
	public void applyEffect() {
		cooldown -= Game.TicksPerFrame();
		
		if (cooldown < 0) {
			owner.setHealth(owner.getCurHealth() - owner.getMaxHealth() * 0.025f);
			cooldown = TOTAL_COOLDOWN;
		}
		
		
	}
	
	
}
