package engine;


public class Settings {	
	// Time Variables
	final public static int Frames_Per_Second = 60; // 60 FPS
	final public static float Ticks_Per_Frame = 1f; // 1 TPS
	
	// Video Settings
	final public static float BaseScale = 10f;
	public static float Scale = BaseScale;
	
	final public static int Resolution_X = 1920;
	final public static int Resolution_Y = 1080;
	
	// Sound Settings
	final public static float Volume = 0.5f;

	// Scale Settings
	final public static float Pixels_Per_Unit = 20f;
	final public static float Tile_Size = 10f;
	
	// GameState Markers
	public static int LastState = 0;
	
	public static void setScale(float i)	{	Scale = i;	}
}