package objects.entities.units;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.entities.Player;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.entities.projectiles.Dagger;
import objects.entities.projectiles.Thorn;
import objects.entities.projectiles.ThornBall;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.sound.SoundManager;

public class Tumbleweed extends Unit {
	public static float SpawnTimer;
	public static float SpawnCooldown;
	
	private float theta;
	
	private int timer;
	
	public Tumbleweed() {
		super( Polygon.regularPolygon(2.5f, 8) );
		
		this.sprite = ImageManager.getImageCopy("tumbleweed", 7, 7);
		
		this.score = 1;
		
		this.maxHealth = 25f;
		this.health = maxHealth;
		
		this.baseDamage = 5;
		this.maxVelocity = Player.Player_Max_Velocity * 0.5f;
		
		timer = 0;
//		timer = (int) (1000 * Math.random());
		
		this.team = ObjectTeam.Enemy;
	}
	
	protected void onDeath() {
		SoundManager.playSoundEffect("weeddeath", Settings.EffectsVolume);
	}
	
	protected void unitUpdate() {
		this.addXVelocity(5f * Utility.cos(theta));
		this.addYVelocity(5f * Utility.sin(theta));
		
		timer++;
		if (timer % 200 == 0) {
			theta = (float) (Math.random() * 2 * Math.PI);
			
			SoundManager.playSoundEffect("weedattack", Settings.EffectsVolume);
			new ThornBall(this, Game.Player)
				.setMaxTimer(200)
				.build();
		}
		
	}
}