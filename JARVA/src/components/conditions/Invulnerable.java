package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public class Invulnerable extends Condition {
	
	public Invulnerable(Unit target, float timer) {
		super(null, target, timer);
	}

	@Override
	public void remove() {
		super.remove();
		
		target.invulnerable(false);
	}
	
	@Override
	public void applyEffect(Unit target) {
		target.invulnerable(true);
	}
	
}
