package maps;

import org.newdawn.slick.Graphics;

import engine.Settings;
import objects.entities.Unit;
import objects.geometry.Polygon;

public class Arena {

	protected int centerX;
	protected int centerY;
	
	//protected Border border;
	protected Border border;
		
	protected int sizeX;
	protected int sizeY;
	
	public Arena() {
		border = new Border();

	}
	
	// Return Centers
	public int getCenterX() { return centerX; }
	public int getCenterY() { return centerY; }
	
	public Polygon getBorder() { return border; }
	
	public void draw(Graphics g) {
		border.draw(g, 0, 0);
	}


	
}