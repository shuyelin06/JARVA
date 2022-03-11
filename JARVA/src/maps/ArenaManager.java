package maps;

import engine.states.Game;

public class ArenaManager {
	private Game game;
	
	private Arena arena;
	
	public ArenaManager(Game game) {
		this.game = game;
	}
	
	public Arena getArena() { return arena; }
	
	// Initialize a random arena for the game
	private void initialize() {
		
	}
}