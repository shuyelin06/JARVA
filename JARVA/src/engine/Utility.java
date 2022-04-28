package engine;

import objects.GameObject;
import objects.geometry.Vector;

public class Utility {
	// Float Variants of the Trig Functions
	public static float sin(float theta) { return (float) Math.sin(theta); }
	public static float cos(float theta) { return (float) Math.cos(theta); }
	
	public static float atan(float y, float x) { return (float) Math.atan2(y, x); }
	
	// Conversions
	public static float ConvertToDegrees(float radians) { return (float) (radians * 180 / Math.PI); }
	public static float ConvertToRadians(float degrees) { return (float) (degrees * Math.PI / 180); }
	
	// Linear Algebra Methods
	public static float crossK(Vector v1, Vector v2) { return v1.x * v2.y - v1.y * v2.x; }
	
	public static float dot(Vector v1, Vector v2) { return (v1.x * v2.x + v1.y * v2.y); }
	
	// Randomness
	public static float random() { return (float) Math.random(); }
	
	// Calculate Information
	public static float distance(float x1, float y1, float x2, float y2) { return (float) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2)); }
	public static float distance(GameObject o, float x, float y) { return distance(o.getX(), o.getY(), x, y); }
	public static float distance(GameObject o1, GameObject o2) { return distance(o1, o2.getX(), o2.getY()); }
	
	public static float angleBetween(float x1, float y1, float x2, float y2) { return ConvertToDegrees(atan(y2 - y1, x2 - x1)); }
	public static float angleBetween(GameObject o, float x, float y) { return angleBetween(o.getX(), o.getY(), x, y); }
	public static float angleBetween(GameObject o1, GameObject o2) { return angleBetween(o1, o2.getX(), o2.getY()); }
	
}