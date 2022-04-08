package engine.states.levels;

import engine.states.Game;
import objects.entities.units.AngryBoulder;
import objects.entities.units.Eagle;
import objects.entities.units.Tumbleweed;

public class LTwo extends Level
{
	public LTwo(Game game) 
	{
		super(game);
		
		lastSpawnTime = 100;
	}

	public void update()
	{
		if(spawningTimer == 20)
		{
			new Eagle()
			.setX(200f)
			.setY(-100f)
			.build();
		}
		
		if(spawningTimer == 100)
		{
			new AngryBoulder()
			.setX(2f)
			.setY(5f)
			.build();

			new Tumbleweed()
			.setX(300f)
			.setY(500f)
			.setYVelocity(-0.5f)
			.build();
		}
	}
}
