package objects.entities;

import engine.Utility;
import engine.states.Game;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Player extends Unit {
	private static float Player_Max_Velocity = 75f;
	
	// Dash Timer
	private static float Dash_Timer = 0.35f;
	private static float Dash_Boost = 2f;
	private static float Dash_Threshold = 0.5f;
	
	private float lastDashed;
	private boolean dashing;
	
	// Gun Inventory
	
	
	public Player() {
		super(Polygon.rectangle(5f, 8f));
		
		this.maxVelocity = Player_Max_Velocity;
		
		this.team = ObjectTeam.Ally;
		this.sprite = ImageManager.getImageCopy("Placeholder", 5, 8);
		
		this.contactDamage = 500f;
		
		this.build();
	}
	
	public boolean canMove() { return !dashing; }
	
	public void unitUpdate() {
		// Dash Determining
		if( dashing ) {
			if( Game.getTicks() - lastDashed > Dash_Timer ) {
				stopDashing();
			}
		}
		
	}
	
	public void dash() {
		if( dashing ) return;
		
		if( velocity.magnitude() > maxVelocity * Dash_Threshold ) {
			final float VelocityGain = Player_Max_Velocity * Dash_Boost;
			final float Direction = velocity.direction();
			
			this.setXVelocity( VelocityGain * Utility.cos(Direction) );
			this.setYVelocity( VelocityGain * Utility.sin(Direction) );
			
			beginDashing();
		}
	}
	
	private void beginDashing() {
		invulnerable(Dash_Timer);
		dashing = true;
		
		lastDashed = Game.getTicks();	
		maxVelocity = Player_Max_Velocity * Dash_Boost;
		friction = false;
	}
	private void stopDashing() {
		dashing = false;
		
		friction = true;
		maxVelocity = Player_Max_Velocity;
	}
}