package ui.input;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import engine.states.Game;
import objects.GameObject;

public class InputManager {
	private Game game;	
	private Input input;
	
	public InputManager(Game game, Input input) {
		this.game = game;
		this.input = input;
	}
	
	// Check for Keys Down
	public void update() {
		final float Velocity = 100f;
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
		
		//averages the angles of the
		
		if(velocityAngle.size() != 0)
		{
			for(Float f : velocityAngle)
			{
				sumVelocityAngle += f;
			}
			
			sumVelocityAngle /= velocityAngle.size();
			System.out.println(sumVelocityAngle);
			
			Game.Player.addYVelocity(Velocity * (float) -Math.sin(Math.toRadians(sumVelocityAngle)));
			Game.Player.addXVelocity(Velocity * (float) Math.cos(Math.toRadians(sumVelocityAngle)));
		}
		
		sumVelocityAngle = 0;
		velocityAngle.clear();
	}

	// Mouse Pressed
	public void mousePressed(int key) {
		switch(key) {
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