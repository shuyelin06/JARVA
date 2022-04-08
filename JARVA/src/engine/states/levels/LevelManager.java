package engine.states.levels;

import java.util.ArrayList;

import engine.states.Game;
import objects.entities.units.AngryBoulder;
import objects.entities.units.Eagle;
import objects.entities.units.Tumbleweed;

public class LevelManager 
{
	private Game game;
	private Level currentLevel;
	private int level;
	
	private ArrayList<Level> levels;
	
	public LevelManager(Game game)
	{
		this.game = game;
		
		level = 0;
		
		levels = new ArrayList<Level>();
		
		levels.add(new LOne(game));
		levels.add(new LTwo(game));
		levels.add(new LThree(game));
		
		currentLevel = levels.get(0);
	}
	
	public void update()
	{
		currentLevel.update();
		
		if(game.getEnemies().size() == 0 && 
				currentLevel.getSpawningTimer() > currentLevel.getLastSpawnTime()) 
		{
			level++;
			currentLevel = levels.get(level - 1);
			
			System.out.println(level);
		}
	}
}
