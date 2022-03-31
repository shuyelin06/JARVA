package objects.entities;

import engine.Settings;
import engine.Utility;

import java.util.ArrayList;

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
	
	// Max Velocity Multipliers
	private ArrayList<Float> velocityMultipliers;
	
	// Dashing
	private static Float Dash_Boost = 2.5f;
	private static float Dash_Timer = 0.15f;
	private static float Dash_Threshold = 0.5f;
	
	private float lastDashed;
	private boolean dashing;
	
	// Sprinting
	private static Float Sprint_Boost = 1.5f;
	private static int Max_Sprint_Stamina = 150;
	
	private int maxSprintStamina;
	private int sprintStamina;
	private int sprintCooldown;
	private boolean isSprinting;
	
	// Width and Height
	private float rectW;
	private float rectH;
	
	// Gun Inventory
	private Revolver testWeapon;
	
	public Player() {
		super(Polygon.rectangle(5f, 10f));
	  
		// Team and Sprite
		this.team = ObjectTeam.Ally;
		this.sprite = ImageManager.getImageCopy("Placeholder", 5, 10);
		
		// Width and Height
		this.rectW = 5f;
		this.rectH = 10f;
		
		// Contact Damage
		this.contactDamage = 25f;
		
		// Velocity Determinants
		this.maxVelocity = Player_Max_Velocity;
		this.velocityMultipliers = new ArrayList<>();
		
		// Dashing
		this.lastDashed = Game.getTicks();
		this.dashing = false;
		
		// Sprinting
		this.sprintStamina = maxSprintStamina = Max_Sprint_Stamina;
		this.sprintCooldown = 0;
		this.isSprinting = false;
		
		// Test Weapon
		this.testWeapon = new Revolver(this);
		
		this.build();
	}
	
	public float getMaxVelocity() {
		float output = maxVelocity;
		for(Float f: velocityMultipliers) {
			output *= f;
		}
		return output;
	}
	public boolean canMove() { return !dashing; }
	public int getSprintStamina() { return sprintStamina; }
	public int getMaxSprintStamina() { return maxSprintStamina; }
	public float getSprintStaminaPercent() { return (float)sprintStamina / (float)maxSprintStamina; }
	
	/* --- Inherited Methods --- */
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
	
	public void unitUpdate() {
		System.out.println("Health: " + health);
		System.out.println("Percent Health: " + getPercentHealth());
		
		this.maxVelocity = Player_Max_Velocity;
		for(Float f: velocityMultipliers) {
			maxVelocity *= f;
		}
		
		// Dash Determining
		if( dashing ) {
			if ( Game.getTicks() - lastDashed > Dash_Timer ) stopDashing();
		}
		
		// Sprint Determining
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
		
		// Update Weapon
		testWeapon.update(); //ill move this somewhere else, just testing
	}
	
	/* --- Dash Behavior --- */
	public void dash() {
		if( dashing ) return;
		
		if( velocity.magnitude() > maxVelocity * Dash_Threshold ) {
			beginDashing();
			
			final float Direction = velocity.direction();
			final float VelocityBoost = this.getMaxVelocity();
			
			this.setXVelocity( VelocityBoost * Utility.cos(Direction) );
			this.setYVelocity( VelocityBoost * Utility.sin(Direction) );
		}
	}
	
	private void beginDashing() {
		invulnerable(Dash_Timer);
		dashing = true;
		
		lastDashed = Game.getTicks();
		friction = false;
		velocityMultipliers.add(Dash_Boost);
	}
	private void stopDashing() {
		dashing = false;
		
		friction = true;
		velocityMultipliers.remove(Dash_Boost);
	}
	
	/* --- Sprint Behavior --- */
	public void startSprinting() {
		if( sprintStamina > 0 ) {
			if(!isSprinting) velocityMultipliers.add(Sprint_Boost);
			isSprinting = true;
		} else stopSprinting();
	}
	
	public void stopSprinting() {
		isSprinting = false;
		velocityMultipliers.remove(Sprint_Boost);
	}
	
	
	private boolean hasSprintStamina() { return sprintStamina > 0; }
	public void isSprinting() { isSprinting = true; }
	public void isNotSprinting() { isSprinting = false; }
	
	/*
	 * private int maxSprintStamina;
	private int sprintStamina;
	private int sprintCooldown;
	private boolean isSprinting;
	
	 */

	
	public Player buildPlayer() { Game.GameObjects.add(this); return this; }
}