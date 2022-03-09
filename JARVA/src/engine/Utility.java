package engine;

import objects.geometry.Vector;

public class Utility {
	// Float Variants of the Trig Functions
	public static float sin(float theta) { return (float) Math.sin(theta); }
	public static float cos(float theta) { return (float) Math.cos(theta); }
	
	public static float atan(float y, float x) { return (float) Math.atan2(y, x); }
	
	// Linear Algebra Methods
	public static float crossK(Vector v1, Vector v2) { return v1.x * v2.y - v1.y * v2.x; }
	
	public static float dot(Vector v1, Vector v2) { return (v1.x * v2.x + v1.y + v2.y); }
	
}