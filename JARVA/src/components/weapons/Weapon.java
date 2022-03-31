package components.weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import components.Item;
import engine.Settings;
import objects.GameObject;
import ui.input.InputManager;

public abstract class Weapon extends Item
{
	private float baseDamage;
	
	// Weapon Usage
	private float lastUsed;
	private float useTimer;
	
	public Weapon(GameObject owner)
	{
		super(owner);
		
		// Default Variables
		this.baseDamage = 1f;
	}
	
	abstract public void equip();
	abstract public void unequip();
	
	abstract public void use();
}
