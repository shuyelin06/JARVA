package objects.entities.units;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.entities.Player;
import objects.entities.Unit;
import objects.entities.projectiles.Beam;
import objects.entities.projectiles.Rock;
import objects.geometry.Polygon;
import ui.display.animation.Animation;
import ui.display.images.ImageManager;
import ui.sound.SoundManager;

public class AngryBoulder extends Unit {
	public static float SpawnTimer;
	public static float SpawnCooldown;
	
	private Player player;
	
	private static float ShotCooldown = 2.5f;
	private static int NumberOfShots = 5;
	private static float ShotSpread = 90; // In Degrees
	
	private float lastShot;
	
	private float timer;
	
	public AngryBoulder() {
		super(Polygon.rectangle(8f, 8f));
		
		this.maxVelocity = Player.Player_Max_Velocity * 0.3f;
		
		this.score = 10;
		
		this.maxHealth = 50f;
		this.health = maxHealth;
		this.damageBlock = 0.5f;
		
		this.baseDamage = 10;
		this.lastShot = ShotCooldown;
		
		this.width = 8;
		this.height = 8;
		this.animation = new Animation("rock", 32, 32);
		
		this.team = ObjectTeam.Enemy;
		this.player = Game.Player;
	}

	private void shoot() {
		lastShot -= Game.TicksPerFrame();
		
		if(lastShot < 0) {
			attacking = true;
			SoundManager.playSoundEffect("rockattack", Settings.EffectsVolume);
			
			float angle = -ShotSpread / 2f;
			for( int i = 0; i < NumberOfShots; i++ ) {
				new Rock(this, player, angle, (float) Math.random() * 0.25f + 1f)
					.build();
				angle += ShotSpread / NumberOfShots;
			}
			
			lastShot = ShotCooldown;
		}
	}
	
	protected void onDeath() {
		SoundManager.playSoundEffect("rockdeath", Settings.EffectsVolume);
	}
	
	@Override
	protected void unitUpdate() {
		final float Angle = Utility.atan(player.getY() - y, player.getX() - x);
		this.addXVelocity(5f * Utility.cos(Angle));
		this.addYVelocity(5f * Utility.sin(Angle));
		
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