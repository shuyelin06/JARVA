package components.conditions;

import objects.GameObject;
import objects.entities.Unit;

public class Stun extends Condition {
	
	public Stun(GameObject owner, Unit target, float timer) {
		super(owner, target, timer);
	}

	@Override
	public void remove() {
		super.remove();
		
		target.stunned(false);
	}
	
	@Override
	public void applyEffect(Unit target) {
		target.stunned(true);
	}
	
}