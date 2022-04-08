package engine.states.levels;

import engine.states.Game;

public abstract class Level 
{
	protected int spawningTimer;
	protected int lastSpawnTime;
	
	public Level(Game game)
	{
		unitSpawning();
		
		spawningTimer = 0;
		lastSpawnTime = 120;
	}
	
	public int getLastSpawnTime() { return lastSpawnTime; }
	public int getSpawningTimer() { return spawningTimer; }
	
	public void unitSpawning()
	{
		
	}
	
	public void update()
	{
		spawningTimer++;
	}
}
