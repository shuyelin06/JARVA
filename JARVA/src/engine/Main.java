package engine;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.states.End;
import engine.states.Game;
import engine.states.Loading;
import engine.states.Title;

public class Main extends StateBasedGame 
{
	// GameState IDs
	public static final int LOADING_ID = 0;
	public static final int TITLE_ID = 1;
	public static final int GAME_ID = 2;
	public static final int END_ID = 3;
      
    // BasicGameStates 
    private BasicGameState loading;
    private BasicGameState title;
    private BasicGameState game;
    private BasicGameState end;
    
    private static AppGameContainer appgc;
    
    
	public Main(String name) { 
		super(name); 
		
		this.loading = new Loading(LOADING_ID);
		this.title = new Title(TITLE_ID);
		this.game = new Game(GAME_ID);
		this.end = new End(END_ID);
	}

	public static int getScreenWidth() { return appgc.getScreenWidth(); }
	public static int getScreenHeight() { return appgc.getScreenHeight(); }
	
	public static int getFPS() { return appgc.getFPS(); }
	
	public void initStatesList(GameContainer gc) throws SlickException 
	{
		
		addState(loading);
		
		addState(title);
		addState(game);		
		addState(end);
	}

	public static void main(String[] args) 
	{
		try 
		{
			appgc = new AppGameContainer(new Main("JARVA"));
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
			Settings.Resolution_X = appgc.getScreenWidth();
			Settings.Resolution_Y = appgc.getScreenHeight();
			
			appgc.setDisplayMode(Settings.Resolution_X, Settings.Resolution_Y, false);
			appgc.setTargetFrameRate(Settings.Frames_Per_Second);
			
			appgc.start();
			appgc.setVSync(true);
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
}