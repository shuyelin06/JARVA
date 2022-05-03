package engine.states.levels;

import java.util.ArrayList;

import engine.Settings;
import engine.states.Game;
import objects.entities.units.AngryBoulder;
import objects.entities.units.BananaTree;
import objects.entities.units.BighornSheep;
import objects.entities.units.Eagle;
import objects.entities.units.Monkey;
import objects.entities.units.Tumbleweed;
import ui.sound.SoundManager;

public class LevelManager 
{
	private final static float SpawnRadius = 40f;
	
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
		AngryBoulder.SpawnTimer = 10f;
		AngryBoulder.SpawnCooldown = 0f;

		Tumbleweed.SpawnTimer = 7.5f;
		Tumbleweed.SpawnCooldown = 0f;

		Eagle.SpawnTimer = 30f;
		Eagle.SpawnCooldown = 0f;
		
		Monkey.SpawnTimer = 15f;
		Monkey.SpawnCooldown = 0f;
		
		BananaTree.SpawnTimer = 20f; 
		BananaTree.SpawnCooldown = 0f; 
		
		BighornSheep.SpawnTimer = 30f;
		BighornSheep.SpawnCooldown = 0f;
		BighornSheep.NumberMultiplier = 1;
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
		if( Ticks > 5f ) {
			AngryBoulder.SpawnCooldown -= Game.TicksPerFrame();
			if( AngryBoulder.SpawnCooldown < 0 ) {
				SoundManager.playSoundEffect("rockspawn", Settings.SpawnVolume);
				AngryBoulder.SpawnTimer = AngryBoulder.SpawnTimer - AngryBoulder.SpawnTimer / 250f;
				AngryBoulder.SpawnCooldown = AngryBoulder.SpawnTimer;
				
				for( int i = 0; i < ((int) Game.Difficulty); i++ ) {
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
		
		if( Ticks > 5f ) {
			Tumbleweed.SpawnCooldown -= Game.TicksPerFrame();
			if( Tumbleweed.SpawnCooldown < 0 ) {
				SoundManager.playSoundEffect("weedspawn", Settings.SpawnVolume);
				Tumbleweed.SpawnTimer = Tumbleweed.SpawnTimer - Tumbleweed.SpawnTimer / 250f;
				Tumbleweed.SpawnCooldown = Tumbleweed.SpawnTimer;

				for( int i = 0; i < ((int) Game.Difficulty); i++ ) {
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
		
		if( Ticks > 18f ) {
			Eagle.SpawnCooldown -= Game.TicksPerFrame();
			if( Eagle.SpawnCooldown < 0 ) {
				SoundManager.playSoundEffect("eaglespawn", Settings.SpawnVolume);
				Eagle.SpawnTimer = Eagle.SpawnTimer - Eagle.SpawnTimer / 250f;
				Eagle.SpawnCooldown = Eagle.SpawnTimer;

				for( int i = 0; i < 1 + ((int) (Game.Difficulty * 0.5f)); i++ ) {
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
		
		// MONKE and TREE
		if( Ticks > 40f ) {
			BananaTree.SpawnCooldown -= Game.TicksPerFrame();
			Monkey.SpawnCooldown -= Game.TicksPerFrame();
			
			if( BananaTree.SpawnCooldown < 0 ) {
				BananaTree.SpawnCooldown = BananaTree.SpawnTimer;
				
				// Randomize the Sign
				final int sign = (int) Math.signum( Math.random() - 0.5f );
				float x = (100 + (float) Math.random() * 25f) * sign;
				float y = randomY();
				
				new BananaTree()
					.setX(x)
					.setY(y)
					.build();
			}
			
			if( Monkey.SpawnCooldown < 0 ) {
				SoundManager.playSoundEffect("monkeyspawn", Settings.SpawnVolume);
				Monkey.SpawnTimer = Monkey.SpawnTimer - Monkey.SpawnTimer / 250f;
				Monkey.SpawnCooldown = Monkey.SpawnTimer;
				
				for( int i = 0; i < ((int) Game.Difficulty); i++ ) {
					float x = randomX() * 0.5f;
					float y = randomY() * 0.5f;
					while( Game.Player.getDistance(x, y) < SpawnRadius ) {
						x = randomX();
						y = randomY();
					}
					
					new Monkey()
						.setX(x)
						.setY(y)
						.build();
				}
			}
		}
		
		// Ram Rush
		if( Ticks > 120f ) {
			BighornSheep.SpawnCooldown -= Game.TicksPerFrame();
			if( BighornSheep.SpawnCooldown < 0 ) {
				SoundManager.playSoundEffect("ramspawn", Settings.SpawnVolume);
				BighornSheep.NumberMultiplier++;
				BighornSheep.SpawnTimer = BighornSheep.SpawnTimer - BighornSheep.SpawnTimer / 250f;
				BighornSheep.SpawnCooldown = BighornSheep.SpawnTimer;
				
				// General Spawn Area
				float x = randomX();
				float y = randomY();
				while( Game.Player.getDistance(x, y) < SpawnRadius * 1.5f ) {
					x = randomX();
					y = randomY();
				}
				
				for( int i = 0; i < 10 * BighornSheep.NumberMultiplier; i++ ) {
					final float Spread = (float) (Math.random() * SpawnRadius * 0.25f) - SpawnRadius * 0.25f / 2f;
					new BighornSheep()
						.setX(x + Spread)
						.setY(y + Spread)
						.build();
				}
			}
		}
		
	}
}
