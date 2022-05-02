package objects.entities.units;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.states.Game;
import objects.GameObject;
import objects.GameObject.ObjectTeam;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class BananaTree extends Unit {
	public static float SpawnTimer;
	public static float SpawnCooldown;
	
	public static ArrayList<BananaTree> bananaTrees = new ArrayList<BananaTree>();
	
	public BananaTree() {
		super(Polygon.rectangle(24, 24));
		
		this.sprite = ImageManager.getImageCopy("banana tree", 24, 24);
		
		this.maxHealth = 350f;
		this.health = maxHealth;
		
		this.baseDamage = 0;
		this.contactDamage = 0;
		
		this.knockbackBlock = 1;
		
		this.team = ObjectTeam.Enemy;
	}
	
	@Override
	protected void unitUpdate() {}
	
	@Override
	public void remove() {
		super.remove();
		bananaTrees.remove(this);
	}
	
	@Override
	public GameObject build() {
		bananaTrees.add(this);
		return super.build();
	}
}
