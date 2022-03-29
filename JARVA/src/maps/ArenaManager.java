package maps;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Projectile;
import objects.entities.Unit;

public class ArenaManager {
	private Game game;
	
	private Arena arena;
	
	public ArenaManager(Game game) {
		this.game = game;
		initialize();
	}
	
	public Arena getArena() { return arena; }
	
	// Initialize a random arena for the game
	private void initialize() {
		arena = new Arena();
	}
	
	public void update() {
		enforceCollisions();
	}
	
	public void enforceCollisions() {
		for(GameObject o : Game.GameObjects) {
			
			if(!arena.getBorder().intersects(o.getHitbox())) {
				
				if(o instanceof Unit ) {
					((Unit) o).takeKnockback(o.getAngleTo(0,0), o.getVelocity() * 1.1f);
					
				}
				else if(o instanceof Projectile) {
					o.remove();
				}
			}
		}
	}
	
}