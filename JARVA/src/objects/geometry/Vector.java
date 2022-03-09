package objects.geometry;

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
	
	public Vector offset(float x2, float y2) { return new Vector(x + x2, y + y2); }
	public Vector flip() { return new Vector(-x, -y); }
}