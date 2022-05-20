package objects.entities;

import engine.Settings;
import engine.Utility;
import engine.Values;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import engine.states.Game;
import objects.GameObject;
import objects.collisions.BoundMonitor;
import objects.geometry.Polygon;
import ui.display.animation.Animation;
import ui.display.images.ImageManager;
import ui.input.InputManager;
import ui.sound.SoundManager;
import components.Inventory;
import components.conditions.Condition;
import components.conditions.Invulnerable;
import components.weapons.guns.HeavySniper;
import components.weapons.guns.RealRailgun;
import components.weapons.guns.Revolver;
import components.weapons.guns.Shotgun;
import components.weapons.guns.SubmachineGun;

public class Player extends Unit {
	public static float Player_Max_Velocity = 70f;
	
	// Max Velocity Multipliers
	private ArrayList<Float> velocityMultipliers;
	
	// Dashing
	private static Float Dash_Boost = 2.5f;
	private static float Dash_Timer = 0.15f;
	private static float Dash_Threshold = 0.5f;
	private static float Dash_Cooldown = 0.5f;
	
	private float lastDashed;
	private boolean dashing;
	
	// Sprint Meter
	private static int Max_Sprint_Stamina = 150;
	private Animation animation;
	private Image cowboyHat;
	private int animationTick;
	private int animationFrame;
	private boolean animating;
	
	private int maxSprintStamina;
	private int sprintStamina;
	private int sprintCooldown;
	private boolean isSprinting;
	
	// Gun Inventory
	private Inventory inventory;
	
	public Player() {
		super(Polygon.rectangle(4f, 8f));
		
		// Team and Sprite
		this.team = ObjectTeam.Ally;
		
		this.sprite = ImageManager.getImageCopy("ikeaMan", 8, 8); //6, 6
		this.cowboyHat = ImageManager.getImageCopy("cowboyHat", 7, 3);
		
		this.cowboyHat.setFilter(Image.FILTER_NEAREST);
		
		this.maxHealth = 150f;
		this.health = maxHealth;
		
		// Contact Damage
		this.baseDamage = 1f;
		this.contactDamage = 1f;
		
		// Velocity Determinants
		this.maxVelocity = Player_Max_Velocity;
		this.velocityMultipliers = new ArrayList<>();
		
		animation = new Animation("ikeaSheet", 32, 32);
		animationTick = 0;
		animationFrame = 0;
		animating = false;
		
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
//		inventory.addItem(new RealRailgun(this)); No.
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
	public void addVelocityMultiplier(Float f) { velocityMultipliers.add(f); }
	public void removeVelocityMultiplier(Float f) { velocityMultipliers.remove(f); }
	
	public Inventory getInventory()	{	return inventory;	}
	
	public boolean canMove() { return !dashing; }
	public int getSprintStamina() { return sprintStamina; }
	public int getMaxSprintStamina() { return maxSprintStamina; }
	public float getSprintStaminaPercent() { return (float)sprintStamina / (float)maxSprintStamina; }
	
	/* --- Inherited Methods --- */
	protected void onDeath() {}
	
	public void unitDraw(Graphics g) {
		if(animation != null)
		{
			if(animating && animationTick % 2 == 0) animationFrame++; animationTick++;
			if(animationFrame > animation.animationSize() - 1)
			{
				 animationFrame %= animation.animationSize(); 
			}
		}
		
		sprite = animation.getFrame(animationFrame).getScaledCopy(8, 8);
	
		Image tempHat = cowboyHat;
		tempHat.setRotation(0);
		float offsetX = 0f;
		float offsetY = 0f;
		
		
		if(this.isMirrored())
		{
			 offsetX -= 1.2f; 
			 tempHat = tempHat.getFlippedCopy(true, false);
		}
		
		if(dashing) 
		{
			sprite = ImageManager.getImageCopy("ikeaDash", 8, 8);
			offsetY = 0.2f;
			
			if(this.isMirrored()) //i am so bad at coding
			{
				tempHat.setRotation(30);
				offsetX += 3f;
			}
			else
			{
				tempHat.setRotation(-30);
				offsetX -= 3f;
			}
			
			
		}
		
		tempHat.drawCentered(this.x + offsetX, this.y - 5.2f + offsetY);
	}
	
	public void unitUpdate() {
		this.maxVelocity = Player_Max_Velocity;
		for(Float f: velocityMultipliers) {
			maxVelocity *= f;
		}
		
		if(velocity.magnitude() > 1.5f)
		{
			animating = true;
		}
		else
		{
			animating = false;
			animationFrame = 0;
		}
		
		// Dash Determining
		if( dashing ) {
			this.velocity.scalarMultiply( maxVelocity / velocity.magnitude() );
			if ( Game.getTicks() - lastDashed > Dash_Timer ) stopDashing();
		}
		
		// Sprint Determining
		if( isSprinting ) {
			sprintStamina--;
			sprintCooldown = 120;
		} else if( sprintStamina < maxSprintStamina ) {
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
		
		SoundManager.playSoundEffect("dash", Settings.EffectsVolume);
	}
	private void stopDashing() 
	{
		dashing = false;
		
		collidable = true;
		friction = true;
		velocityMultipliers.remove(Dash_Boost);
	}
	
	
	private boolean hasSprintStamina() { return sprintStamina > 0; }
	public void isSprinting() { isSprinting = true; }
	public void isNotSprinting() { isSprinting = false; }

	public Player buildPlayer() { Game.GameObjects.add(this); return this; }
}