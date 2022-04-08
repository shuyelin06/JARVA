package components.weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import components.Item;
import engine.Settings;
import objects.GameObject;
import objects.entities.Unit;
import ui.input.InputManager;

public abstract class Weapon extends Item
{
	protected float baseDamage;
	
	// Weapon Usage
	protected float lastUsed;
	protected float useTimer;
	
	public Weapon(Unit owner)
	{
		super(owner);
		
		// Default Variables
		this.baseDamage = 1f;
		
		this.isWeapon = true;
	}
}
