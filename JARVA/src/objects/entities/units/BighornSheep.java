package objects.entities.units;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.GameObject.ObjectTeam;
import objects.entities.Unit;
import objects.entities.Player;
import objects.entities.projectiles.Beam;
import objects.entities.projectiles.Rock;
import objects.geometry.Polygon;
import ui.display.animation.Animation;
import ui.display.images.ImageManager;

public class BighornSheep extends Unit {
	private Player player;
	
	final private static float LaserCooldown = 0.75f;
	final private static int LaserSpin = 5 * Settings.Frames_Per_Second;
	
	final private static int LaserLength = 50;
	final private static int LaserHeight = 1;
	
	final private static float AttackRadius = LaserLength * 0.8f;
	
	private float AttackSpeed = Player.Player_Max_Velocity * 0.8f;
	private float BaseSpeed = AttackSpeed * 2;
	
	private Beam beam;
	
	private float attackCooldown;
	private float attackTheta;
	
	private float animationTimer;
	
	public BighornSheep() {
		super(Polygon.rectangle(4, 4));
				
		this.maxHealth = 3f;
		this.health = maxHealth;
		
		this.baseDamage = 10;
		
		this.maxVelocity = BaseSpeed;
		
		this.width = 4;
		this.height = 4;
		this.animation = new Animation("ram", 32, 32);
		
		this.team = ObjectTeam.Enemy;
		this.player = Game.Player;
		
		this.attackCooldown = LaserCooldown;
		
		this.beam = null;
	}

	private float distanceToPlayer() {
		return (float) Math.sqrt( 
				(y - player.getY()) * (y - player.getY()) + 
				(x - player.getX()) * (x - player.getX())
				);
	}
	@Override
	protected void unitUpdate() {
		this.mirrored = false;
		
		if( attacking ) {
			animationTimer += Game.TicksPerFrame();
			if( animationTimer > 0.5f ) {
				animationTimer = 0f;
				attacking = false;
			}
		}
		
		final float Angle = Utility.atan(player.getY() - y, player.getX() - x);
		if( distanceToPlayer()  < AttackRadius ) {
			// Move Slowly
			this.maxVelocity = AttackSpeed;
			this.addXVelocity(AttackSpeed * Utility.cos(Angle));
			this.addYVelocity(AttackSpeed * Utility.sin(Angle));
			
			omega = Utility.ConvertToRadians(LaserSpin);
			
			attackCooldown -= Game.TicksPerFrame();
			if( attackCooldown < 0 ) {
				if( beam == null ) {
					beam = new Beam( this, LaserLength, LaserHeight );
					beam.build();
				}
				
				final float TargetX = x +  Utility.cos(angle + (float) Math.PI / 2f ) * LaserLength;
				final float TargetY = y + Utility.sin(angle + (float) Math.PI / 2f) * LaserLength;
				
				beam.setSource(x, y);
				beam.setTarget(TargetX, TargetY);
			}
		} else {
			if( beam != null ) {
				beam.remove();
				beam = null;
			}
			
			omega = 0;
			
			// Reset Attack Cooldown
			this.attackCooldown = LaserCooldown;
			
			// Run to Player
			this.maxVelocity = BaseSpeed;
			this.addXVelocity(BaseSpeed * Utility.cos(Angle));
			this.addYVelocity(BaseSpeed * Utility.sin(Angle));
		}
	}
		
}