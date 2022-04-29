package ui.input;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import ui.display.DisplayManager;

public class InputManager {
	private Game game;	
	private static Input input;
	
	private static float mouseX;
	private static float mouseY;
	
	public InputManager(Game game, Input input) {
		this.game = game;
		this.input = input;
		
		mouseX = 0;
		mouseY = 0;
	}
	
	//doin a little static trollin
	public static float getScreenMouseX()	{		return mouseX;					} //on the screen
	public static float getScreenMouseY()	{		return mouseY;					}
	public static float getMapMouseX()		{		return (mouseX - (Settings.Resolution_X * 0.5f)) / Settings.Scale + Game.Player.getX();	} //relative to coordinate system (assumes player is always centered)
	public static float getMapMouseY()		{		return (mouseY - (Settings.Resolution_Y * 0.5f)) / Settings.Scale + Game.Player.getY();	}
	public static float getAngleToMouse(GameObject entity) 	{ 		return entity.getAngleTo(InputManager.getMapMouseX(), InputManager.getMapMouseY()); }
	
	public static boolean isLMBClicked() {		return input.isMousePressed(Input.MOUSE_LEFT_BUTTON); }
	public static boolean isLMBDown() {		return input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON); }
	
	// Check for Keys Down
	public void update() {
    mouseX = input.getMouseX();
		mouseY = input.getMouseY();
    
		if( !Game.Player.canMove() ) return;

		movement();
	}
	
	public void movement()
	{
		float movementVelocity = Game.Player.getMaxVelocity();
		//so it doesn't go faster on the diagonal
		boolean flip = false; //lol i give up
		float sumVelocityAngle = 0;
		ArrayList<Float> velocityAngle = new ArrayList<Float>();
		
		if(input.isKeyDown(Input.KEY_W)) { 
			velocityAngle.add(90f);
		}
		if(input.isKeyDown(Input.KEY_S)) {
			velocityAngle.add(270f);
			flip = true;
		}
		
		if(input.isKeyDown(Input.KEY_A)) {
			velocityAngle.add(180f);
		}
		if(input.isKeyDown(Input.KEY_D)) {
			if(flip)	{	velocityAngle.add(360f);	}
			else		{	velocityAngle.add(0f);	}
		}
		
//		// Sprinting
//		if( input.isKeyDown(Input.KEY_LSHIFT) ) { 
//			Game.Player.startSprinting();
//		} else {
//			Game.Player.stopSprinting();;
//		}
		
		//averages the angles
		if(velocityAngle.size() != 0)
		{
			for(Float f : velocityAngle)
			{
				sumVelocityAngle += f;
			}
			
			sumVelocityAngle /= velocityAngle.size();
			
			Game.Player.move(movementVelocity, sumVelocityAngle);
		}
		
		sumVelocityAngle = 0;
		velocityAngle.clear();
	}
	
	// Mouse Pressed
	public void mousePressed(int key, int x, int y) {
		switch(key) {
			case Input.MOUSE_RIGHT_BUTTON:
				Game.Player.dash();
				break;
			
			default:
				break;
		}
	}
	
	// Key Pressed
	public void keyPressed(int key) {
		switch(key) {
			case Input.KEY_1: Game.Player.getInventory().equipItem(0); break;
			case Input.KEY_2: Game.Player.getInventory().equipItem(1); break;
			case Input.KEY_3: Game.Player.getInventory().equipItem(2); break;
			case Input.KEY_4: Game.Player.getInventory().equipItem(3); break;
			case Input.KEY_5: Game.Player.getInventory().equipItem(4); break;
			case Input.KEY_6: Game.Player.getInventory().equipItem(5); break;
			case Input.KEY_7: Game.Player.getInventory().equipItem(6); break;
			case Input.KEY_8: Game.Player.getInventory().equipItem(7); break;
			case Input.KEY_9: Game.Player.getInventory().equipItem(8); break;
			
//			case Input.KEY_COMMA: Settings.Scale *= 0.8f; break; //dev mode options
//			case Input.KEY_PERIOD: Settings.Scale *= 1.25f; break; Temporary Disable for Export
			
			case Input.KEY_B: DisplayManager.Debug = !DisplayManager.Debug; break;
			
			default: 
				break;
		}
	}
}