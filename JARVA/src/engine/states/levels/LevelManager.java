package engine.states.levels;

import java.util.ArrayList;

import engine.states.Game;
import objects.entities.units.AngryBoulder;
import objects.entities.units.BighornSheep;
import objects.entities.units.Eagle;
import objects.entities.units.Tumbleweed;

public class LevelManager 
{
	private final static float SpawnRadius = 50f;
	
	private float arenaWidth;
	private float arenaHeight;
	
	private Game game;
	private Level currentLevel;
	private int level;
	
	private ArrayList<Level> levels;
	
	public LevelManager(Game game)
	{
		this.game = game;
		
		this.arenaWidth = Game.ArenaManager.getArena().getWidth();
		this.arenaHeight = Game.ArenaManager.getArena().getHeight();
		
		level = 0;
		
		levels = new ArrayList<Level>();
		
		levels.add(new LOne(game));
		levels.add(new LTwo(game));
		levels.add(new LThree(game));
		
		currentLevel = levels.get(0);
		
		// Initializing Spawn Timers
		BighornSheep.SpawnTimer = 25f;
		BighornSheep.SpawnCooldown = 0f;
		
		AngryBoulder.SpawnTimer = 5f;
		AngryBoulder.SpawnCooldown = 0f;

		Tumbleweed.SpawnTimer = 3.5f;
		Tumbleweed.SpawnCooldown = 0f;

		Eagle.SpawnTimer = 10f;
		Eagle.SpawnCooldown = 0f;
	}
	
	public float randomX() {
		return ( (float) Math.random() * arenaWidth - arenaWidth / 2f);
	}
	public float randomY() {
		return ( (float) Math.random() * arenaHeight - arenaHeight / 2f);
	}
	
	public void update()
	{
		final float Ticks = Game.getTicks();
		if( Ticks > 2.5f ) {
			AngryBoulder.SpawnCooldown -= Game.TicksPerFrame();
			if( AngryBoulder.SpawnCooldown < 0 ) {
				AngryBoulder.SpawnTimer = AngryBoulder.SpawnTimer - AngryBoulder.SpawnTimer / 250f;
				AngryBoulder.SpawnCooldown = AngryBoulder.SpawnTimer;
				
				for( int i = 0; i < 1 * ((int) Game.Difficulty); i++ ) {
					float x = randomX();
					float y = randomY();
					while( Game.Player.getDistance(x, y) < SpawnRadius ) {
						x = randomX();
						y = randomY();
					}
					
					new AngryBoulder()
						.setX(x)
						.setY(y)
						.build();
				}
			}
		} 
		
		if( Ticks > 2.5f ) {
			Tumbleweed.SpawnCooldown -= Game.TicksPerFrame();
			if( Tumbleweed.SpawnCooldown < 0 ) {
				Tumbleweed.SpawnTimer = Tumbleweed.SpawnTimer - Tumbleweed.SpawnTimer / 250f;
				Tumbleweed.SpawnCooldown = Tumbleweed.SpawnTimer;

				for( int i = 0; i < 1 * ((int) Game.Difficulty); i++ ) {
					float x = randomX();
					float y = randomY();
					while( Game.Player.getDistance(x, y) < SpawnRadius ) {
						x = randomX();
						y = randomY();
					}
					
					new Tumbleweed()
						.setX(x)
						.setY(y)
						.build();
				}
			}
		}
		
		// MONKE
		if( Ticks > 15f ) {}
		
		
		if( Ticks > 30f ) {
			Eagle.SpawnCooldown -= Game.TicksPerFrame();
			if( Eagle.SpawnCooldown < 0 ) {
				Eagle.SpawnTimer = Eagle.SpawnTimer - Eagle.SpawnTimer / 250f;
				Eagle.SpawnCooldown = Eagle.SpawnTimer;

				for( int i = 0; i < 1 * ((int) Game.Difficulty); i++ ) {
					float x = randomX();
					float y = randomY();
					while( Game.Player.getDistance(x, y) < SpawnRadius ) {
						x = randomX();
						y = randomY();
					}
					
					new Eagle()
						.setX(x)
						.setY(y)
						.build();
				}
			}
		}
		
		if( Ticks > 60f ) {
			BighornSheep.SpawnCooldown -= Game.TicksPerFrame();
			if( BighornSheep.SpawnCooldown < 0 ) {
				BighornSheep.SpawnTimer = BighornSheep.SpawnTimer - BighornSheep.SpawnTimer / 250f;
				BighornSheep.SpawnCooldown = BighornSheep.SpawnTimer;
				
				for( int i = 0; i < 10 * ((int) Game.Difficulty); i++ ) {
					float x = randomX();
					float y = randomY();
					while( Game.Player.getDistance(x, y) < SpawnRadius ) {
						x = randomX();
						y = randomY();
					}
					
					new BighornSheep()
						.setX(x)
						.setY(y)
						.build();
				}
			}
		}
		
	}
}
