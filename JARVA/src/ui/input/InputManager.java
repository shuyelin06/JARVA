package ui.input;

import org.newdawn.slick.Input;

import engine.Settings;
import engine.Utility;
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
		final float Velocity = Game.Player.getMaxVelocity();
		
		if( !Game.Player.canMove() ) return;
		
		if(input.isKeyDown(Input.KEY_W)) { 
			Game.Player.addYVelocity(-Velocity);
		}
		if(input.isKeyDown(Input.KEY_S)) {
			Game.Player.addYVelocity(Velocity);
		}
		
		if(input.isKeyDown(Input.KEY_A)) {
			Game.Player.addXVelocity(-Velocity);
		}
		if(input.isKeyDown(Input.KEY_D)) {
			Game.Player.addXVelocity(Velocity);
		}
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