package objects.entities.units;

import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.GameObject.ObjectTeam;
import objects.entities.Unit;
import objects.entities.Player;
import objects.entities.projectiles.Beam;
import objects.entities.projectiles.Rock;
import objects.geometry.Polygon;
import ui.display.animation.Animation;
import ui.display.images.ImageManager;

public class BighornSheep extends Unit {
	private Player player;
	
	final private static float LaserCooldown = 5f;
	
	private float attackCooldown;
	private float animationTimer;
	
	public BighornSheep() {
		super(Polygon.rectangle(10f, 10f));
		
		this.maxHealth = 100f;
		this.health = maxHealth;
		
		this.baseDamage = 10;
		
		this.width = 8;
		this.height = 8;
		this.animation = new Animation("ram", 32, 32);
		
		this.team = ObjectTeam.Enemy;
		this.player = Game.Player;
		
		this.attackCooldown = LaserCooldown;
		
		this.beam = new Beam( this, 10, 1 );
	}

	private Beam beam;
	float theta;
	
	@Override
	public GameObject build() {
		super.build();
		beam.build();
		return this;
	}
	
	private void shoot() {
		attackCooldown -= Game.TicksPerFrame();
		
		theta += 0.15f;
		
		final float TargetX = Utility.cos(theta) * 10f;
		final float TargetY = Utility.sin(theta) * 10f;
		
		beam.setSource(x, y);
		beam.setTarget(TargetX, TargetY);
		
		if( (int) (Game.getTicks()) % 2 == 0) {
			beam.changeSize(1, 0);
		} else {
			beam.changeSize(-1, 0);
		}
		
		
		if(attackCooldown < 0) {
//			attacking = true;
			
			
			
			attackCooldown = LaserCooldown;
		}
	}
		
	@Override
	protected void unitUpdate() {		
		if( attacking ) {
			animationTimer += Game.TicksPerFrame();
			if( animationTimer > 0.5f ) {
				animationTimer = 0f;
				attacking = false;
			}
		}
		
		this.shoot();
	}
		
}