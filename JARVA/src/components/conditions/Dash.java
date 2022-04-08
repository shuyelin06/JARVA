package components.conditions;

import engine.Settings;
import objects.entities.Unit;

public abstract class Dash extends Condition {
	private static final float Dash_Timer = 0.2f;
	
	
	private float cooldown;
	
	
	public Dash(Unit owner) {
		super(owner);
	}
	
	@Override
	public void addTimer(float time) {
		if( timer < 0 ) timer = time;
		
		if(timer < 0) timer = time;
		else timer += time;
	}
	public void applyEffect() {
		
		owner.setCollidable(false);
		
		owner.takeCondition(Type.Stun, 1 / Settings.Frames_Per_Second);
	}
	public void removeEffect() {
		
		owner.setCollidable(true);
		
		
		
	}
}