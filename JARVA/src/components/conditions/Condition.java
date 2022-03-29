package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public abstract class Condition {
	protected boolean remove;
	protected float timer;
	
	protected GameObject owner;
	protected Unit target;
	
	public Condition(GameObject owner, Unit target) {
		this.owner = owner;
		this.target = target;
		
		this.timer = 1f;
	}
	
	abstract public void applyEffect(GameObject target);
	public void remove() {
		this.remove = true;
	}
	public void apply() {
		timer -= Game.TicksPerFrame();
		if(timer < 0) {
			remove();
			return;
		}
		
		applyEffect(target);
	}
	
}