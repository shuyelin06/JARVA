package objects.entities.units;

import engine.Settings;
import engine.states.Game;
import objects.entities.Unit;
import objects.entities.projectiles.Dagger;
import objects.geometry.Polygon;
import ui.display.animation.Animation;
import ui.display.images.ImageManager;
import ui.sound.SoundManager;

public class Eagle extends Unit{
	public static float SpawnTimer;
	public static float SpawnCooldown;
	
	private float timer;
	private float firingTimer;
	
	private float cooldown;

	private float rapidFireSpacing;
	private float spiralAngle;
	
	
	private boolean firing;
	private int attackMode;
	
	private static int TotalShots = 15;
	int shotCount;
	
	private float prevAddY;
	private float prevAddX;
	
	public Eagle() {
		super(Polygon.rectangle(12f, 12f));
		
		this.width = 12;
		this.height = 12;
		this.animation = new Animation("eagle", 32, 32);
		
		this.score = 15;
		
		this.baseDamage = 5;
		
		firing = false;
		attackMode = 1;
		spiralAngle = 0;
		
		cooldown = 3;
		timer = cooldown;
		
		rapidFireSpacing = 1/10f;
		firingTimer = rapidFireSpacing;
		
		this.team = ObjectTeam.Enemy;
	}
	
	protected void onDeath() {
		SoundManager.playSoundEffect("eagledeath", Settings.EffectsVolume);
	}
	
	protected void unitUpdate() {
		attacking = firing;
		
		if( firing ) {
			
			switch(attackMode) {
			
			case 1:
				rapidFire();
				break;
				
			case 2:
				volleyFire();
				break;
				
			case 3:
				spiralFire();
				break;
				
			}
			
		} else {
			// Shooting Cooldown
			timer -= Game.TicksPerFrame();
			if(timer < 0 && Math.random() < 0.01) {
				SoundManager.playSoundEffect("eagleattack", Settings.EffectsVolume);
				
				firing = true;
				shotCount = TotalShots;
				firingTimer = rapidFireSpacing;
				
				spiralAngle = 0;
					
			}
		}
		
		move();
		
	}
	
	public void move() {
//		if(Math.random() < 0.3) {
		
			float addY = 40 * (float) ( Math.random() - 0.5);
			float addX = 40 * (float) (Math.random() - 0.5);
			if(Math.abs(addY-prevAddY) > 10) {
				addY = 0;
			}else {
				prevAddY = addY;
			}
			if(Math.abs(addX-prevAddX) > 10) {
				addX = 0;
			}else {
				prevAddX = addX;
			}
			
			this.addYVelocity(addY);
			this.addXVelocity(addX);
//		}
	}
	
	public void rapidFire() {
		firingTimer -= Game.TicksPerFrame();
		if(firingTimer < 0) {
			spawnDagger(0, false);
			
			shotCount--;
			firingTimer = rapidFireSpacing;
		}
		
		if(shotCount <= 0) {
			reset();
		}
	}
	
	public void volleyFire() {
		spawnDagger(0, false);
		for(int i = 1; i < 7; i++) {
			spawnDagger(i * (float)Math.PI/24, false);
			spawnDagger(-i * (float) Math.PI/24, false);
		}
		reset();
	}
	
	public void spiralFire() {
//		//temporary
//		spawnDagger(0, false);
		firingTimer -= Game.TicksPerFrame();
		if(firingTimer < 0) {
			spawnDagger(spiralAngle, true);
			shotCount--;
			firingTimer = rapidFireSpacing;
			spiralAngle += Math.PI/20;
		}
		
		if(shotCount <= 0) {
			reset();
		}
	}
	
	
	public void spawnDagger(float offset, boolean absoluteAngle) {
		new Dagger(this, Game.Player, offset, absoluteAngle)
		.setPierce(1)
		.setKnockback(0)
		.setDamageMultiplier(1)
		.build();
	}
	
	public void reset() {
		timer = cooldown;
		firing = false;
		
		if(attackMode >= 3) {
			attackMode = 1;
		}else {
			attackMode++;
		}
	}
}
