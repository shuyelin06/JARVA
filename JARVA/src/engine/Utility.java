package engine;

public class Utility {
	// Float variants of the trig functions
	public static float sin(float theta) { return (float) Math.sin(theta); }
	public static float cos(float theta) { return (float) Math.cos(theta); }
	
	public static float atan(float y, float x) { return (float) Math.atan2(y, x); }
}