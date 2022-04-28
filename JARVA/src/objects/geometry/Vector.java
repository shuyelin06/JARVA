package objects.geometry;

import engine.Utility;

public class Vector {
	public float x;
	public float y;
	
	public Vector() {
		this.x = 0f;
		this.y = 0f;
	}
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	// Object Helper Methods
	public float direction() { return Utility.atan(y, x); }
	public float magnitude() { return (float) Math.sqrt(x * x + y * y); }
	
	// Mutator Methods
	public void setXY(float x2, float y2) { x = x2; y = y2; }
	
	public void setX(float x2) { x = x2; }
	public void setY(float y2) { y = y2; }
	
	public void addX(float x2) { x += x2; }
	public void addY(float y2) { y += y2; }
	
	public void reduce(float reduction) {
		x -= x * reduction;
		y -= y * reduction;
	}
	public void scalarMultiply(float scalar) {
		x *= scalar;
		y *= scalar;
	}
	public void rotate(float angle) {
		 float rotatedX = x * Utility.cos(angle) - y * Utility.sin(angle);
		 float rotatedY = x * Utility.sin(angle) + y * Utility.cos(angle);
		 
		 x = rotatedX;
		 y = rotatedY;
	}
	
	// Helper Methods
	public Vector offset(Vector v) { return new Vector(x + v.x, y + v.y); }
	public Vector offset(float x2, float y2) { return new Vector(x + x2, y + y2); }
	public Vector flip() { return new Vector(-x, -y); }
}