package ui.display.hud;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import engine.Settings;
import engine.states.Game;
import objects.GameObject;
import objects.entities.Player;
import ui.input.InputManager;

public class Sprintbar 
{
	private Player player;
	
	private float x;
	private float y;
	private float w;
	private float h;
	
	private Color color1; //high stamina
	private Color color2; //low stamina
	private Color sumColors;
	private float depletion;
	
	public Sprintbar(Player player)
	{
		this.player = player;
		
		w = 400;
		h = 20;
		x = Settings.Resolution_X * 0.5f - w * 0.5f;
		y = Settings.Resolution_Y * 0.8f - h * 0.5f;
		
		color1 = new Color(255, 150, 0);
		color2 = new Color(150, 0, 0);
		sumColors = new Color(color1);
		depletion = 1f;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(x - 2, y - 2, w + 4, h + 4);
		
		g.setColor(new Color(0f, 0f, 0f));
		g.fillRect(x, y, w, h);
		
		g.setColor(sumColors);
		g.fillRect(x, y, w * depletion, h);
	}
	
	public void update()
	{		
		if(player != null) 
		{
			depletion = player.getSprintStaminaPercent();
		}
		else
		{
			player = Game.Player;
		}
		
		color1.a = depletion;
		color2.a = 1 - depletion;
		sumColors.r = (color1.a * color1.r) + (color2.a * color2.r);
		sumColors.g = (color1.a * color1.g) + (color2.a * color2.g);
		sumColors.b = (color1.a * color1.b) + (color2.a * color2.b);
	}
}
