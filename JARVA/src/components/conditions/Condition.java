package components.conditions;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;

public abstract class Condition {
	public enum Type {
		Invulnerable, Confusion,
		Burn, Poison, Stun
	}
	
	protected float timer;
	protected Unit owner;
	
	public Condition(Unit owner) {
		this.owner = owner;
		this.timer = 0f;
	}
	
	public boolean isActive() {
		if(timer > 0) return true;
		else return false;
	}
	public void addTimer(float time) {
		if(timer < 0) timer = time;
		else timer += time;
	}
	
	abstract public void applyEffect();
	abstract public void removeEffect();
	
	public void apply() {
		timer -= Game.TicksPerFrame();
		
		if(timer > 0) applyEffect(); 
		else removeEffect();
	}
	
}