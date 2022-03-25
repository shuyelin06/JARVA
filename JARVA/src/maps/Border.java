package maps;

import org.newdawn.slick.Graphics;

import objects.entities.Unit;
import objects.geometry.Polygon;

public class Border extends Unit {
	public Border (Polygon p) {
		super(p);
		this.setDamageBlock(1f).setKnockbackBlock(1f).setContactDamage(0f).setBaseDamage(0f);
		
	}

	@Override
	protected void unitUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Graphics g) {
		drawHitbox(g);
	}
}
