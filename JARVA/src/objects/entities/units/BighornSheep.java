package objects.entities.units;

import engine.states.Game;
import objects.entities.Unit;
import objects.entities.projectiles.Beam;
import objects.geometry.Polygon;

public class BighornSheep extends Unit {

	
	public BighornSheep(Polygon polygon) {
		super(polygon);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void unitUpdate() {
		
//		new Beam( this, Game.Player.getX(), Game.Player.getY() );
		
	}
	
}