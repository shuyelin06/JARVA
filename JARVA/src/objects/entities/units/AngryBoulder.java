package objects.entities.units;

import engine.Utility;
import engine.states.Game;
import objects.entities.Player;
import objects.entities.Unit;
import objects.entities.projectiles.Beam;
import objects.entities.projectiles.Rock;
import objects.geometry.Polygon;
import ui.display.animation.Animation;
import ui.display.images.ImageManager;

public class AngryBoulder extends Unit {
	private Player player;
	
	private static float ShotCooldown = 2.5f;
	private static int NumberOfShots = 10;
	private static float ShotSpread = 90; // In Degrees
	
	private float lastShot;
	
	private float timer;
	
	public AngryBoulder() {
		super(Polygon.rectangle(8f, 8f));
		
		this.maxHealth = 100f;
		this.health = maxHealth;
		this.damageBlock = 0.5f;
		
		this.baseDamage = 10;
		this.lastShot = ShotCooldown;
		
		this.width = 8;
		this.height = 8;
		this.animation = new Animation("rock", 32, 32);
		
		this.team = ObjectTeam.Enemy;
		this.player = Game.Player;
		
		this.beam = new Beam( this, Game.Player.getX(), Game.Player.getY() );
	}

	private void shoot() {
		lastShot -= Game.TicksPerFrame();
		
		if(lastShot < 0) {
			attacking = true;
			
			float angle = -ShotSpread / 2f;
			for( int i = 0; i < NumberOfShots; i++ ) {
				new Rock(this, player, angle, (float) Math.random() * 0.25f + 1f)
					.build();
				angle += ShotSpread / NumberOfShots;
			}
			
			lastShot = ShotCooldown;
		}
	}
	
	Beam beam;
	float playerLastX;
	float playerLastY;
	
	float omega;
	float theta;
	
	@Override
	protected void unitUpdate() {
//		Player player = Game.Player;
//		

//		this.setXVelocity(2.5f);

		theta += 0.015f;
		final float TargetX = Utility.cos(theta) * 10f;
		final float TargetY = Utility.sin(theta) * 10f;
		
//		this.beam.changeLength(0.05f);
		this.beam.changeTarget(TargetX, TargetY);
		
//		beam.changeTarget(player.getX(), player.getY());

		
		if( attacking ) {
			timer += Game.TicksPerFrame();
			if( timer > 0.5f ) {
				timer = 0f;
				attacking = false;
			}
		}
		this.shoot();
	}
	
	
	
}