package ui.input;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;

public class InputManager {
	private Game game;	
	private Input input;
	
	private static float mouseX;
	private static float mouseY;
	
	public InputManager(Game game, Input input) {
		this.game = game;
		this.input = input;
		
		mouseX = 0;
		mouseY = 0;
	}
	
	public static float getActualMouseX()	{		return mouseX;					}
	public static float getActualMouseY()	{		return mouseY;					}
	public static float getScaledMouseX()	{		return mouseX / Settings.Scale;	}
	public static float getScaledMouseY()	{		return mouseY / Settings.Scale;	} //doin a little static trollin
	
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
		
		if(input.isKeyDown(Input.KEY_LSHIFT) && Game.Player.hasSprintStamina()) 
		{ 
			Game.Player.isSprinting();
			movementVelocity *= 1.8f;
		}
		else
		{
			Game.Player.isNotSprinting();
		}
		
		//averages the angles
		if(velocityAngle.size() != 0)
		{
			for(Float f : velocityAngle)
			{
				sumVelocityAngle += f;
			}
			
			sumVelocityAngle /= velocityAngle.size();
			
			Game.Player.addYVelocity(movementVelocity * (float) -Math.sin(Math.toRadians(sumVelocityAngle)));
			Game.Player.addXVelocity(movementVelocity * (float) Math.cos(Math.toRadians(sumVelocityAngle)));
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
			default: 
				break;
		}
	}
}