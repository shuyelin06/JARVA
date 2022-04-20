package objects.entities.units;

import engine.states.Game;
import objects.entities.Unit;
import objects.entities.projectiles.Dagger;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Eagle extends Unit{
	
	private float timer;
	private float firingTimer;
	
	private float cooldown;

	private float rapidFireSpacing;
	private float spiralAngle;
	
	
	private boolean firing;
	private int attackMode;
	
	private static int TotalShots = 20;
	int shotCount;
	
	public Eagle() {
		super(Polygon.rectangle(12f, 12f));
		
		this.sprite = ImageManager.getImageCopy("eagle", 12, 12);
		this.sprite.setImageColor(1f, 1f, 1f);
		
		this.baseDamage = 10;
		
		firing = false;
		attackMode = 1;
		spiralAngle = 0;
		
		cooldown = 3;
		timer = cooldown;
		
		rapidFireSpacing = 1/10f;
		firingTimer = rapidFireSpacing;
		
		this.team = ObjectTeam.Enemy;
	}
	
	
	protected void unitUpdate() {
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
				firing = true;
				shotCount = TotalShots;
				firingTimer = rapidFireSpacing;
				
				spiralAngle = 0;
					
			}
		}
		
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
