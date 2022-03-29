package objects.entities;

import engine.Settings;
import engine.Utility;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import engine.states.Game;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;
import components.weapons.guns.Revolver;

public class Player extends Unit {
	private static float Player_Max_Velocity = 75f;
	
	// Dashing
	private static float Dash_Timer = 0.35f;
	private static float Dash_Boost = 2f;
	private static float Dash_Threshold = 0.5f;
	
	private float lastDashed;
	private boolean dashing;
	
	// Sprinting
	private static int Max_Sprint_Stamina = 150;
	
	private int maxSprintStamina;
	private int sprintStamina;
	private int sprintCooldown;
	private boolean isSprinting;
	
	private float rectW;
	private float rectH;
	
	// Gun Inventory
	private Revolver testWeapon;
	
	public Player() {
		super(Polygon.rectangle(5f, 10f));
		
		this.rectW = 5f;
		this.rectH = 10f;
		
		this.maxVelocity = Player_Max_Velocity;
		
		this.team = ObjectTeam.Ally;
		this.sprite = ImageManager.getImageCopy("Placeholder", 5, 10);
		
		this.contactDamage = 25f;
		
		// Sprinting
		maxSprintStamina = Max_Sprint_Stamina;
		this.sprintStamina = Max_Sprint_Stamina;
		this.sprintCooldown = 0;
		this.isSprinting = false;
		
		// Test Weapon
		this.testWeapon = new Revolver(this);
		
		this.build();
	}
	
	public boolean canMove() { return !dashing; }
	public int getSprintStamina() { return sprintStamina; }
	public int getMaxSprintStamina() { return maxSprintStamina; }
	public float getSprintStaminaPercent() { return (float)sprintStamina / (float)maxSprintStamina; }
	
	public void unitUpdate() {
		// Dash Determining
		if( dashing ) {
			if( Game.getTicks() - lastDashed > Dash_Timer ) {
				stopDashing();
			}
		}
		
		if( isSprinting ) {
			sprintStamina--;
			sprintCooldown = 120;
		}
		else if( sprintStamina < maxSprintStamina ) {
			sprintCooldown--;
			if(sprintCooldown < 0)
			{
				sprintStamina += 2;
			}
			else if(sprintCooldown < 60)
			{
				sprintStamina += 1;
			}
		}
		
		testWeapon.update(); //ill move this somewhere else, just testing
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
	
	public void draw(Graphics g)
	{
		if(InputManager.getScreenMouseX() < Settings.Resolution_X * 0.5f) //idk how to get mouse relative to the player
		{
			mirroredSprite = true;
		}
		else
		{
			mirroredSprite = false;
		}
		
		super.draw(g);
		
		testWeapon.draw(g); //ill move this to the managers
	}
	
	public boolean hasSprintStamina() { return sprintStamina > 0; }
	public void isSprinting() { isSprinting = true; }
	public void isNotSprinting() { isSprinting = false; }
	public Player buildPlayer() { Game.GameObjects.add(this); return this; }
}