package maps;

import engine.Utility;
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
		arena = new Arena(1000f, 1000f);
	}
	
	public void update() {
		enforceCollisions();
	}
	
	public void enforceCollisions() {
		for(GameObject o : Game.GameObjects) {
			
			if(!arena.getBorder().intersects(o.getHitbox())) {
				// Object Border Control
				if( o instanceof Unit ) {
					( (Unit) o).takeKnockback( Utility.atan(o.getY(), o.getX()) + (float) Math.PI, 200f );
					
				}
				// Projectile Border Control
				else if(o instanceof Projectile) {
					o.remove();
				}
			}
		}
	}
	
}