package objects.entities.units;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.GameObject.ObjectTeam;
import objects.entities.Unit;
import objects.entities.projectiles.Banana;
import objects.entities.projectiles.Thorn;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.sound.SoundManager;

public class Monkey extends Unit {
	public static float SpawnTimer;
	public static float SpawnCooldown;
	
	private boolean lastActive; // For sounds
	private boolean active;
	private int timer;
	final static float Base_Speed = 10;
	
	public Monkey() {
		super(Polygon.rectangle(6f, 6f));
		
		this.sprite = ImageManager.getImageCopy("monkey", 6, 6);
		
		this.score = 15;
		
		this.baseDamage = 5;
		
		this.maxVelocity = Game.Player.getMaxVelocity();
		
		this.damageBlock = 0.1f;
		
		timer = 0;
		active = false;
		this.team = ObjectTeam.Enemy;
	}
	
	@Override
	public void objectCollision(GameObject o) {
		super.objectCollision(o);
		
		if (o instanceof BananaTree) {
			active = true;
		}
	}
	protected void onDeath() {
		SoundManager.playSoundEffect("monkeydeath", Settings.EffectsVolume);
	}
	
	protected void unitUpdate() {
		BananaTree nearest = nearestBananaTree();
		if(nearest == null) return;
		
		moveTo(Base_Speed, nearest);
		if (active) {
			if( !lastActive ) {
				lastActive = true;
				SoundManager.playSoundEffect("monkeyattack", Settings.EffectsVolume);
			}
			
			timer++;
			if (timer % 20 == 0 && timer != 0) {
				new Banana(this, Game.Player)
					.setPierce(1)
					.setKnockback(0)
					.setDamageMultiplier(1)
					.build();
			}
		} else {
			lastActive = false;
		}
	}
	
	private BananaTree nearestBananaTree() {
		BananaTree nearest = null;
		for (BananaTree b: BananaTree.bananaTrees) {
			if (nearest == null) {
				nearest = b;
			}
			if (getDistance(b) < getDistance(nearest)) {
				nearest = b;
			}
		}
		return nearest;
	}
	
	public void moveTo(float baseSpeed, GameObject o) {
		final float Angle = Utility.atan(o.getY() - y, o.getX() - x);
		this.addXVelocity(baseSpeed * (float) Math.cos(Angle));
		this.addYVelocity(baseSpeed * (float) Math.sin(Angle));
	}
}
