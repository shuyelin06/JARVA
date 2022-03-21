package objects.entities;

import objects.geometry.Polygon;

public class Player extends Unit {

	public Player() {
		super(Polygon.rectangle(50f, 50f));
	}
	
	public void unitUpdate() {};
}