package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import engine.states.Game;
import objects.GameObject;
import objects.entities.Player;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Thorn extends Projectile {
	
	private float baseSpeed = 10f;
	private double theta;
	
	private GameObject origin;
	private GameObject target;
	
	private int timer;
	private int maxTimer;
	
	public Thorn(GameObject origin, GameObject target) {
		super(Polygon.rectangle(3f, 3f), origin);
		
		this.origin = origin;
		this.target = target;
		
		this.setX(origin.getX());
		this.setY(origin.getY());
		
		this.setTeam(origin.getTeam());
		
		this.sprite = ImageManager.getImageCopy("tumbleweed", 3, 3);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
		theta = Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX());
		this.setXVelocity((float) Math.cos(theta) * baseSpeed);
		this.setYVelocity((float) Math.sin(theta) * baseSpeed);
		
	}

	

	@Override
	public void projectileUpdate() {
		timer++;
		if (timer < maxTimer) {
			theta = Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX());
			this.setXVelocity((float) Math.cos(theta) * baseSpeed);
			this.setYVelocity((float) Math.sin(theta) * baseSpeed);
//		} else if (timer == maxTimer && !(origin instanceof Thorn)) {
		} else if (timer % maxTimer == 0) {
			new Thorn(this, Game.Player)
				.setMaxTimer(500)
				.setPierce(1)
				.setKnockback(0)
				.setDamageMultiplier(1)
				.build();
		}
	}

	@Override
	public void objectDraw(Graphics g) {
		
	}
	
	public Thorn setMaxTimer(int maxTimer) { this.maxTimer = maxTimer; return this; }
	
}