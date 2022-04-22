package objects.entities;

import engine.Settings;
import engine.Utility;
import engine.Values;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import engine.states.Game;
import objects.GameObject;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;
import components.Inventory;
import components.conditions.Condition;
import components.conditions.Invulnerable;
import components.weapons.guns.HeavySniper;
import components.weapons.guns.RealRailgun;
import components.weapons.guns.Revolver;
import components.weapons.guns.Shotgun;
import components.weapons.guns.SubmachineGun;

public class Player extends Unit {
	private static float Player_Max_Velocity = 75f;
	
	// Max Velocity Multipliers
	private ArrayList<Float> velocityMultipliers;
	
	// Dashing
	private static Float Dash_Boost = 2.5f;
	private static float Dash_Timer = 0.15f;
	private static float Dash_Threshold = 0.35f;
	private static float Dash_Cooldown = 0.5f;
	
	private float lastDashed;
	private boolean dashing;
	
	// Sprinting
	private static Float Sprint_Boost = 1.5f;
	private static int Max_Sprint_Stamina = 150;
	
	private int maxSprintStamina;
	private int sprintStamina;
	private int sprintCooldown;
	private boolean isSprinting;
	
	// Gun Inventory
	private Inventory inventory;
	
	public Player() {
		super(Polygon.rectangle(6f, 6f));
	  
		this.collidable = false;
		
		// Team and Sprite
		this.team = ObjectTeam.Ally;
		this.sprite = ImageManager.getImageCopy("jarvis", 6, 6);
		
		// Contact Damage
		this.baseDamage = 1f;
		this.contactDamage = 1f;
		
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
		
		// Test Weapons
		this.inventory = new Inventory();
		inventory.addItem(new Revolver(this));
		inventory.addItem(new HeavySniper(this));
		inventory.addItem(new Shotgun(this));
		inventory.addItem(new SubmachineGun(this));
		inventory.addItem(new RealRailgun(this));
		inventory.equipItem(0);
		
		this.build();
	}
	
	public float getMaxVelocity() {
		float output = maxVelocity;
		for(Float f: velocityMultipliers) {
			output *= f;
		}
		return output;
	}
	
	public Inventory getInventory()	{	return inventory;	}
	
	public boolean canMove() { return !dashing; }
	public int getSprintStamina() { return sprintStamina; }
	public int getMaxSprintStamina() { return maxSprintStamina; }
	public float getSprintStaminaPercent() { return (float)sprintStamina / (float)maxSprintStamina; }
	
	/* --- Inherited Methods --- */
	public void unitDraw(Graphics g) {
		inventory.draw(g); //ill move this to the managers
	}
	
	public void unitUpdate() {
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
		inventory.update();
		
		if(inventory.getWeapon() != null)
		{
			inventory.getEquippedItem().rotateTo(InputManager.getMapMouseX(), InputManager.getMapMouseY());
			
			if(InputManager.isLMBClicked())
			{
				inventory.getWeapon().use();
			}
			
			if(InputManager.isLMBDown() && inventory.getWeapon().isHeldUse())
			{
				inventory.getWeapon().use();
			}
		}
	}
	
	/* --- Overwritten Methods --- */
	@Override

	protected void mirroredCheck() {
		if(InputManager.getScreenMouseX() < Settings.Resolution_X * 0.5f) // idk how to get mouse relative to the player
		{ mirrored = true; }
		
		else { mirrored = false; }
	}
	
	@Override
	public void takeDamage(float damage) {
		super.takeDamage(damage);
		if (invulnerable == false) {
			takeCondition(Condition.Type.Invulnerable, Values.Default_Invulnerability_Timer);
		}
	}
	
	/* --- Helper Methods --- */
	public void move(float movementVelocity, float sumVelocityAngle) 
	{
		if(!stunned) {
			Game.Player.addYVelocity(movementVelocity * (float) -Math.sin(Math.toRadians(sumVelocityAngle)));
			Game.Player.addXVelocity(movementVelocity * (float) Math.cos(Math.toRadians(sumVelocityAngle)));
		}
	}
	
	/* --- Dash Behavior --- */
	public void dash() 
	{
		if( dashing ) return;
		if( Game.getTicks() - lastDashed < Dash_Cooldown ) return;
		
		if( sprintStamina < 15) return;
		
		if( velocity.magnitude() > maxVelocity * Dash_Threshold ) {
			beginDashing();
			
			sprintStamina -= 15;
			sprintCooldown = 120;
			
			final float Direction = velocity.direction();
			final float VelocityBoost = this.getMaxVelocity();
			
			this.setXVelocity( VelocityBoost * Utility.cos(Direction) );
			this.setYVelocity( VelocityBoost * Utility.sin(Direction) );
		}
	}
	
	private void beginDashing() {
		takeCondition(Condition.Type.Invulnerable, Dash_Timer);
		dashing = true;
		
		collidable = false;
		lastDashed = Game.getTicks();
		friction = false;
		velocityMultipliers.add(Dash_Boost);
	}
	private void stopDashing() 
	{
		dashing = false;
		
		collidable = true;
		friction = true;
		velocityMultipliers.remove(Dash_Boost);
	}
	
	/* --- Sprint Behavior --- */
	public void startSprinting() 
	{
		if( sprintStamina > 0 ) {
			if(!isSprinting) velocityMultipliers.add(Sprint_Boost);
			isSprinting = true;
		} else stopSprinting();
	}
	
	public void stopSprinting() 
	{
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