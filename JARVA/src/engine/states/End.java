package engine.states;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.Main;
import engine.Settings;
import engine.requests.PlayerData;
import engine.requests.Request;
import ui.display.hud.panels.LeaderBoard;
import ui.display.hud.panels.MessagePanel;
import ui.display.hud.panels.Panel;
import ui.display.images.ImageManager;
import ui.input.Button;
import ui.sound.SoundManager;

public class End extends BasicGameState {
	
	final public static int LeaderboardCount = 10;
	
	private int id;
	
	private int refreshTimer;
	private int timer;

	private boolean refresh;
	private boolean restart;
	private boolean exit;
	
	private LeaderBoard leaderBoard;
	
	private MessagePanel idPanel;
	
	private Button refreshButton;
	private Button restartButton;
	private Button exitButton;
	
	// Constructor
	public End(int id) { 
		this.id = id;
	}
	
	@Override
	public int getID() { return id; }
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {}
	
	@Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setMouseGrabbed(false);
		
		timer = 30;
		restart = false;
		
		this.leaderBoard = new LeaderBoard();
		leaderBoard
				.setX( Settings.Resolution_X / 2 )
				.setY( Settings.Resolution_Y / 2 + (int) (Settings.Resolution_Y * 0.1f))
				.setWidth( (int) (Settings.Resolution_X * 0.65f) )
				.setHeight( (int) (Settings.Resolution_Y * 0.75f) );
		leaderBoard.initialize();
		
		idPanel = new MessagePanel();
		idPanel
			.setCentered(true)
			.setTextColor(Color.red)
			.setTextHeight(25);
		idPanel
			.setX(Settings.Resolution_X - Settings.Resolution_X / 14)
			.setY((int) (Settings.Resolution_Y - 0.027f * Settings.Resolution_Y))
			.setWidth(Settings.Resolution_X / 7)
			.setHeight((int) (0.054f * Settings.Resolution_Y));
		
		exitButton = new Button()
				.setCenterX( 0.5f * (0.05208333333f * Settings.Resolution_X) )
				.setCenterY( Settings.Resolution_Y - 1.5f * (0.09259259259f * Settings.Resolution_Y) )
				.setW(1f * (0.05208333333f * Settings.Resolution_X))
				.setH(1f * (0.09259259259f * Settings.Resolution_Y))
				.setImage( "closeButton" );
		
		refreshButton = new Button()
				.setCenterX( Settings.Resolution_X / 2 + Settings.Resolution_X * 0.65f * 0.65f / 2 + 1f * (0.05208333333f * Settings.Resolution_X) )
				.setCenterY( Settings.Resolution_Y / 2 + (int) (Settings.Resolution_Y * 0.1f) - (Settings.Resolution_Y * 0.75f) / 2 - 1f * (0.09259259259f * Settings.Resolution_Y) )
				.setW(1f * (0.05208333333f * Settings.Resolution_X))
				.setH(1f * (0.09259259259f * Settings.Resolution_Y))
				.setImage( "refreshButton" );
		
		restartButton = new Button()
				.setCenterX( 1.5f * (0.05208333333f * Settings.Resolution_X) )
				.setCenterY( Settings.Resolution_Y - 0.5f * (0.09259259259f * Settings.Resolution_Y) )
				.setW(3f * (0.05208333333f * Settings.Resolution_X))
				.setH(1f * (0.09259259259f * Settings.Resolution_Y))
				.setImage( "restartButton" );
		
		SoundManager.playBackgroundMusic("funky blue monkey");
	}
	
	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) {
		SoundManager.stopBackgroundMusic();		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int n) throws SlickException {
		Input input = gc.getInput();
		refreshButton.update( input.getMouseX(), input.getMouseY() );
		restartButton.update( input.getMouseX(), input.getMouseY() );
		exitButton.update( input.getMouseX(), input.getMouseY() );
		
		timer--;
		if(timer == 0) {
			PlayerData data = new PlayerData()
					.setName(Title.identifier)
					.setScore(Game.Ticks);
			Request.POST(data);
			leaderBoard.refresh();
		}
		
		if( exit ) { gc.exit(); return; }
		if( refresh ) {
			refreshTimer--;
			if(refreshTimer < 0) {
				leaderBoard.refresh();
				refresh = false;
			}
		}
		if( restart ) {
			Settings.LastState = Main.END_ID;
			sbg.enterState(Main.TITLE_ID);
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.scale(1f, 1f);
		g.resetTransform();
		
		g.setColor(Color.red);
		
		leaderBoard.render(g);
		idPanel.setMessage(Title.identifier);
		idPanel.render(g);
		
		exitButton.render(g);
		refreshButton.render(g);
		restartButton.render(g);
	}
	
	public void mousePressed(int button, int x, int y) {
		Point mouse = new Point(x, y);
		if(exitButton.isWithin(mouse)) { exit = true; }
		if(refreshButton.isWithin(mouse)) { 
			System.out.println("Clear");
			refresh = true; 
			refreshTimer = 30;
			leaderBoard.clear(); }
		if (restartButton.isWithin(mouse)) { restart = true; }
	}
	
}