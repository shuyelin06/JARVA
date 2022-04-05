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
	
	private boolean firing;
	
	public Eagle() {
		super(Polygon.rectangle(25f, 25f));
		
		this.sprite = ImageManager.getImageCopy("placeholder", 25, 25);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
		this.baseDamage = 10;
		
		firing = false;
		
		cooldown = 4;
		timer = cooldown;
		
		rapidFireSpacing = 1/10f;
		firingTimer = rapidFireSpacing;
		
		this.team = ObjectTeam.Enemy;
	}
	
	private static int TotalShots = 20;
	int shotCount;
	
	protected void unitUpdate() {
		if( firing ) {
			firingTimer -= Game.TicksPerFrame();
			if(firingTimer < 0) {
				new Dagger(this, Game.Player)
					.setPierce(1)
					.setKnockback(0)
					.setDamageMultiplier(1)
					.build();
				
				shotCount--;
				firingTimer = rapidFireSpacing;
			}
			
			if(shotCount <= 0) {
				timer = cooldown;
				firing = false;
			}
			
		} else {
			// Shooting Cooldown
			timer -= Game.TicksPerFrame();
			if(timer < 0) {
				firing = true;
				shotCount = TotalShots;
				firingTimer = rapidFireSpacing;
			}
		}
		
	}
}
