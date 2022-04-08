package ui.display.hud;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import components.conditions.Burn;
import components.conditions.Condition;
import components.conditions.Poison;
import engine.Settings;
import engine.states.Game;
import objects.GameObject;
import objects.entities.Player;
import objects.entities.Unit;
import ui.input.InputManager;

public class Healthbar
{
	private Unit unit;
	
	private float x;
	private float y;
	private float w;
	private float h;
	
	private Color color1; //high health
	private Color color2; //low health
	private Color sumColors;
	private float depletion;
	
	public Healthbar(Unit unit)
	{
		this.unit = unit;
		
		w = 400;
		h = 20;
		x = Settings.Resolution_X * 0.5f - w * 0.5f;
		y = Settings.Resolution_Y * 0.8f + h * 0.8f;
		
		color1 = new Color(46, 139, 87);
		color2 = new Color(196, 30, 58);
		sumColors = new Color(color1);
		depletion = 1f;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(x - 2, y - 2, w + 4, h + 4);
		
		g.setColor(new Color(0f, 0f, 0f));
		g.fillRect(x, y, w, h);
		
		
		
		if (unit.conditionActive(Condition.Type.Burn)) {
			g.setColor(Color.red);
		} else if (unit.conditionActive(Condition.Type.Poison)) {
			g.setColor(Color.green);
		} else {
			g.setColor(sumColors);
		}
		
		//prevent health bar from going negative
		if (depletion >= 0) {
			g.fillRect(x, y, w * depletion, h);
		}
		
	}
	
	public void update()
	{		
		
		if(unit != null) 
		{
			depletion = unit.getPercentHealth();
		}
		
		color1.a = depletion;
		color2.a = 1 - depletion;
		sumColors.r = (color1.a * color1.r) + (color2.a * color2.r);
		sumColors.g = (color1.a * color1.g) + (color2.a * color2.g);
		sumColors.b = (color1.a * color1.b) + (color2.a * color2.b);
	}
	
	public void setUnit(Unit unit) { this.unit = unit; }
}
