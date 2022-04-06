package components;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import components.weapons.Weapon;

public class Inventory 
{
	private int slots;
	private ArrayList<Item> items;
	private Item equippedItem;
	
	public Inventory()
	{
		this.slots = 10;
		
		this.items = new ArrayList<Item>();
		this.equippedItem = null;
	}
	
	public Weapon getWeapon() //cheese temporary method
	{
		if(equippedItem != null && equippedItem.isWeapon()) 
		{
			return((Weapon)equippedItem);
		}
		else
		{
			return null;
		}
	}
	
	public ArrayList<Item> getItems()	{	return items;	}
	
	public void equipItem(int index)
	{
		if( index < 0 || index >= items.size()) return;
		
		boolean test = equippedItem != items.get(index);
		if(items.get(index) != null && equippedItem != null && test)
		{
			equippedItem.unequip();
		}
			
		equippedItem = items.get(index);
		
		if(items.get(index) != null && test)
		{
			equippedItem.equip();
		}
		
		
	}
	
	public void addItem(Item item)
	{
		if(items.size() < slots) items.add(item);
	}
	
	public Item getEquippedItem()	{		return equippedItem;	}
	
	public void draw(Graphics g)
	{
		drawEquipped(g);
	}
	
	public void drawEquipped(Graphics g)
	{
		if(equippedItem != null)
		{
			equippedItem.draw(g);
		}
	}
	
	public void update()
	{
		updateEquipped();
	}
	
	public void updateEquipped()
	{
		if(equippedItem != null)
		{
			equippedItem.update();
		}
	}
}
