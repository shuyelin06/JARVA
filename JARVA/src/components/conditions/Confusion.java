package components.conditions;

import objects.GameObject;
import objects.entities.Unit;

public class Confusion extends Condition {

	public Confusion(Unit owner) {
		super(owner);
	}

	public void removeEffect() {
		owner.confused(false);
	}
	public void applyEffect() {
		owner.confused(true);
	}
}
