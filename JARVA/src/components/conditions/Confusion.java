package components.conditions;

import objects.GameObject;
import objects.entities.Unit;

public class Confusion extends Condition {

	public Confusion(GameObject owner, Unit target, float timer) {
		super(owner, target, timer);
		
	}

	@Override
	public void remove() {
		super.remove();
		
		target.confused(false);
	}
	
	@Override
	public void applyEffect(Unit target) {
		target.confused(true);
	}
	
}
