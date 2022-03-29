package components.weapons.guns;

import objects.GameObject;
import ui.display.images.ImageManager;
import components.weapons.Weapon;

public class Revolver extends Weapon
{
	public Revolver(GameObject owner) 
	{
		super(owner);
		
		this.w = 8;
		this.h = 5;
		
		this.sprite = ImageManager.getImageCopy("revolver");
	}
	
	
}
