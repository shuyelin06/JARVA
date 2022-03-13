package ui.input;

import org.newdawn.slick.Input;

import engine.states.Game;

public class InputManager {
	private Game game;
	private Input input;
	
	public InputManager(Game game, Input input) {
		this.game = game;
		this.input = input;
	}
	
	// Check for Keys Down
	public void update() {
		if(input.isKeyDown(Input.KEY_W)) {
			
		}
		
//		if(input.isKeyDown(0))
//		if(input.isKeyDown(Key.W))
	}
	
}