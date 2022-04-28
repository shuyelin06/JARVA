package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import components.conditions.Confusion;
import components.conditions.Stun;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.entities.Player;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Rock extends Projectile {
	private static float BaseSpeed = 35f;
	
	public Rock(GameObject origin, GameObject target, float angleOffset, float speedMultiplier) {
		super(Polygon.rectangle(4, 4), origin);
		
		this.omega = (float) Math.PI;
		this.knockback = 50f;
		
		this.sprite = ImageManager.getImageCopy("rockProjectile", 4, 4);
		this.setTeam(origin.getTeam()).setX(origin.getX()).setY(origin.getY());
		
		final float Angle = Utility.ConvertToRadians( origin.getAngleTo(target) + angleOffset );
		final float Speed = BaseSpeed * speedMultiplier;
		this.setXVelocity(Speed * Utility.cos(Angle));
		this.setYVelocity(Speed * Utility.sin(Angle));
	}

	

	@Override
	public void projectileUpdate() {}

	@Override
	public void objectDraw(Graphics g) {}
	
}