package engine.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.Settings;
import engine.Main;
import ui.display.hud.panels.MessagePanel;
import ui.display.images.ImageManager;
import ui.input.Button;
import ui.sound.SoundManager;

public class Title extends BasicGameState {
	private int id;
	
	public static String identifier;
	private MessagePanel idPanel;
	
	private Button background;
	private Button title;
	
	private Button startButton;
	private boolean canStart;
	
	// Constructor
	public Title(int id) { 
		this.id = id;
	}
	
	public String getIdentifier() { return identifier; }
	
	@Override
	public int getID() { return id; }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException { }

	@Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		canStart = false;
		
		idPanel = new MessagePanel();
		idPanel
			.setCentered(true)
			.setTextColor(Color.red)
			.setTextHeight(25);
		idPanel
			.setX(Settings.Resolution_X - Settings.Resolution_X / 14)
			.setY((int) (Settings.Resolution_Y - 0.027f * Settings.Resolution_Y))
			.setWidth(Settings.Resolution_X / 7)
			.setHeight((int) (0.054f * Settings.Resolution_Y))
			.setBackground(null)
			.setOutlineColor(null);
		
		identifier = "Player_" + (int) Math.floor(Math.random() * 1000000);
		
		background = new Button()
				.setCenterX(Settings.Resolution_X / 2)
				.setCenterY(Settings.Resolution_Y / 2)
				.setW(Settings.Resolution_X)
				.setH(Settings.Resolution_Y)
				.setImage("titleScreen");
		
		title = new Button()
				.setCenterX(Settings.Resolution_X / 2)
				.setCenterY(Settings.Resolution_Y / 2)
				.setW(Settings.Resolution_X)
				.setH(Settings.Resolution_Y)
				.setImage("title");
		
		startButton = new Button()
				.setCenterX(Settings.Resolution_X / 2)
				.setCenterY(Settings.Resolution_Y / 2)
				.setW(3f * (0.05208333333f * Settings.Resolution_X))
				.setH(1f * (0.09259259259f * Settings.Resolution_Y))
				.setImage("startButton");
		
		SoundManager.playBackgroundMusic("chill leafy monkey");
	}
	
	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) {
		SoundManager.stopBackgroundMusic();		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.scale(1f, 1f);
		g.resetTransform();
		
		// Draw All Buttons
		background.render(g);
		title.render(g);
		startButton.render(g);
		
		idPanel.setMessage(identifier);
		idPanel.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		Input input = gc.getInput();
		startButton.update( input.getMouseX(), input.getMouseY() );
		
		if (canStart) {
			Settings.LastState = Main.TITLE_ID;
			sbg.enterState(Main.GAME_ID);
		}
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_Q) {
			canStart = true;
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		Point mouse = new Point(x, y);
		if (startButton.isWithin(mouse)) {
			canStart = true;
		}
	}
	
}