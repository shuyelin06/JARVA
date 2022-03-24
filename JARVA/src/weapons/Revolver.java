package weapons;

import objects.GameObject;

public class Revolver extends Weapon
{
	public Revolver(GameObject owner) 
	{
		super(owner);
		
		this.w = 80;
		this.h = 30;
	}

}
