package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public class Poison extends Condition {
	//number of seconds until poison tick
	final private float TOTAL_COOLDOWN = 0.5f;
	private float cooldown;
	
	public Poison(Unit owner) {
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
			owner.setHealth(owner.getCurHealth() * 0.96f);
			cooldown = TOTAL_COOLDOWN;
		}
		
	}

}
