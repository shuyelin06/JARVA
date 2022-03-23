package objects.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import engine.states.Game;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Player extends Unit {
	private float rectW;
	private float rectH;
	
	private int maxSprintStamina;
	private int sprintStamina;
	private int sprintCooldown;
	private boolean isSprinting;
	
	public Player() {
		super(Polygon.rectangle(150f, 50f));
		rectW = 150f;
		rectH = 50f;
		
		this.team = ObjectTeam.Ally;
		this.sprite = ImageManager.getImageCopy("Placeholder", 150, 50);
	
		this.contactDamage = 500f;
		
		maxSprintStamina = 150;
		sprintStamina = maxSprintStamina;
		sprintCooldown = 0;
		isSprinting = false;
		
	}
	
	public void unitUpdate() 
	{
		if(isSprinting)
		{
			sprintStamina--;
			sprintCooldown = 60;
		}
		else if(sprintStamina < maxSprintStamina)
		{
			sprintCooldown--;
			if(sprintCooldown < 0)
			{
				sprintStamina++;
			}
		}
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
		
		 //temp sprint bar
		g.drawRect(this.x - rectW * 0.5f - 1, this.y + (rectH * 0.5f) + 9, 
				rectW + 1f, 11);
		g.setColor(new Color( 1f - (sprintStamina * 2f / maxSprintStamina),
				0.1f,
				sprintStamina * 2f / maxSprintStamina));
		g.fillRect(this.x - rectW * 0.5f, this.y + (rectH * 0.5f) + 10, 
				rectW * sprintStamina / maxSprintStamina, 10);
		
	}
	
	public boolean hasSprintStamina() { return sprintStamina > 0; }
	public void isSprinting() { isSprinting = true; }
	public void isNotSprinting() { isSprinting = false; }
	public Player buildPlayer() { Game.GameObjects.add(this); return this; }
}