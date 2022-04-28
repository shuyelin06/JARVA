package objects.entities.units;

import java.util.ArrayList;

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
		super(Polygon.rectangle(7f, 7f));
		
		this.sprite = ImageManager.getImageCopy("banana tree", 7, 7);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
		this.maxHealth = 300f;
		this.baseDamage = 0;
		
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
