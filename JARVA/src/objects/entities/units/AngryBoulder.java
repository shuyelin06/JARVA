package objects.entities.units;

import engine.states.Game;
import objects.entities.Player;
import objects.entities.Unit;
import objects.entities.projectiles.Rock;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class AngryBoulder extends Unit {
	private Player player;
	
	private static float ShotCooldown = 2.5f;
	private static int NumberOfShots = 15;
	private static float ShotSpread = 90; // In Degrees
	
	private float lastShot;
	
	public AngryBoulder() {
		super(Polygon.rectangle(8f, 8f));
		
		this.maxHealth = 500f;
		this.health = maxHealth;
		
		this.baseDamage = 20;
		this.lastShot = ShotCooldown;
		
		this.sprite = ImageManager.getImageCopy("rock", 8, 8);
		this.team = ObjectTeam.Enemy;
		
		this.player = Game.Player;
	}

	private void shoot() {
		lastShot -= Game.TicksPerFrame();
		
		if(lastShot < 0) {
			float angle = -ShotSpread / 2f;
			for( int i = 0; i < NumberOfShots; i++ ) {
				new Rock(this, player, angle, (float) Math.random() * 0.25f + 1f)
					.build();
				angle += ShotSpread / NumberOfShots;
			}
			
			lastShot = ShotCooldown;
		}
	}
	
	@Override
	protected void unitUpdate() {
		
		this.shoot();
	}
	
	
	
}