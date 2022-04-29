package maps;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Settings;
import objects.entities.Unit;
import objects.geometry.Polygon;
import objects.geometry.Vector;

public class Arena {	
	// Width and Height of Arena
	protected float width;
	protected float height;
	
	// Center of the Arena 
	protected int centerX;
	protected int centerY;
	
	// Friction in the Arena
	protected float friction;
	
	// Protected Border border;
	protected Border border;
	
	protected Border projectileBorder;
	
	public Arena(float width, float height) {
		this.friction = 0.35f;
		
		this.width = width;
		this.height = height;
		
		Vector[] vertices = Polygon.rectangleEdges(width, height);
		border = new Border( this, vertices );
		
		Vector[] projectileVerticies = Polygon.rectangleEdges(width+150, height+150);
		projectileBorder = new Border (this, projectileVerticies);

	}
	
	// Return Centers
	public float getFriction() { return friction; }
	
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	
	public int getCenterX() { return centerX; }
	public int getCenterY() { return centerY; }
	
	public Polygon getBorder() { return border; }
	public Polygon getProjectileBorder() { return projectileBorder; }
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.setLineWidth(10f);
		border.draw(g, 0, 0);
	}


	
}