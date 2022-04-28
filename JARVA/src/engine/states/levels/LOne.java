package engine.states.levels;

import engine.states.Game;
import objects.entities.units.AngryBoulder;
import objects.entities.units.BighornSheep;
import objects.entities.units.Eagle;
import objects.entities.units.Tumbleweed;

public class LOne extends Level
{
	public LOne(Game game) 
	{
		super(game);
		
		lastSpawnTime = 100;
	}
	
	public void update()
	{
		super.update();
		
		if(spawningTimer == 20)
		{
			for( int i = 0; i < 10; i++ ) {
				new BighornSheep()
					.setX( (float) Math.random() * 250f )
					.setY( (float) Math.random() * 250f )
					.build();
			}
			
			for( int i = 0; i < 3; i++ ) {
				new Eagle()
					.setX( (float) Math.random() * 250f )
					.setY( (float) Math.random() * 250f )
					.build();
			}
		}
		
		if(spawningTimer == 100)
		{
			for( int i = 0; i < 3; i++ ) {
				new AngryBoulder()
					.setX( (float) Math.random() * 250f )
					.setY( (float) Math.random() * 250f )
					.build();
			}

			for( int i = 0; i < 1; i++ ) {
				new Tumbleweed()
					.setX( (float) Math.random() * 250f )
					.setY( (float) Math.random() * 250f )
					.build();
			}
		}
	}
}
