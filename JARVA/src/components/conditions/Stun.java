package components.conditions;

import objects.GameObject;
import objects.entities.Unit;

public class Stun extends Condition {
	
	public Stun(Unit owner) {
		super(owner);
	}
	
	public void removeEffect() {
		owner.stunned(false);
	}
	public void applyEffect() {
		owner.stunned(true);
	}
	
}