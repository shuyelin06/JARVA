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
		super.update();
		
		if(spawningTimer == 20)
		{
			new Eagle()
			.setX(200f)
			.setY(-100f)
			.build();
		}
		
		if(spawningTimer == 100)
		{
			for(int i = 0; i < 5; i++)
			{
				new AngryBoulder()
				.setX(20 + i *  60f)
				.setY(-100f)
				.build();
			}

			new Tumbleweed()
			.setX(300f)
			.setY(500f)
			.setYVelocity(-0.5f)
			.build();
		}
	}
}
