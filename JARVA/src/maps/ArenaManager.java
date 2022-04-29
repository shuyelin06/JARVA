package maps;

import components.conditions.Condition;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.entities.Projectile;
import objects.entities.Unit;

public class ArenaManager {
	final public static float ArenaWidth = 250f;
	final public static float ArenaHeight = 250f;
	
	private Game game;
	
	private Arena arena;
	
	public ArenaManager(Game game) {
		this.game = game;
		initialize();
	}
	
	public Arena getArena() { return arena; }
	
	// Initialize a random arena for the game
	private void initialize() {
		arena = new Arena(ArenaWidth, ArenaHeight);
	}
	
	public void update() {
		enforceCollisions();
	}
	
	public void enforceCollisions() {
		for(GameObject o : Game.GameObjects) {
			
			if(!arena.getBorder().intersects(o.getHitbox())) {
				// Object Border Control
				if( o instanceof Unit ) {
					Unit u = (Unit) o;
					if (u.conditionActive(Condition.Type.Confusion)) {
						u.takeKnockback( Utility.atan(o.getY(), o.getX()), 200f );
					} else {
						u.takeKnockback( Utility.atan(o.getY(), o.getX()) + (float) Math.PI, 200f );
					}
					
				}
				// Projectile Border Control
				else if(o instanceof Projectile) {
					if(!arena.getProjectileBorder().intersects(o.getHitbox())) {
						o.remove();
					}
				}
			}
		}
	}
	
}