package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public class Invulnerable extends Condition {
	
	public Invulnerable(Unit owner) {
		super(owner);
	}

	@Override
	public void addTimer(float time) {
		if(timer < 0) {
			timer = time;
		}
	}
	public void removeEffect() {
		owner.invulnerable(false);
	}
	public void applyEffect() {
		owner.invulnerable(true);
	}
	
}
