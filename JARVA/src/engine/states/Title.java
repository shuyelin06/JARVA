package engine.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.Settings;
import engine.Main;
import ui.display.images.ImageManager;
import ui.input.Button;

public class Title extends BasicGameState {
	private int id;
	
	private Button startButton;
	private Button title;
	
	private boolean canStart;
	
	// Constructor
	public Title(int id) { 
		this.id = id;
	}
	
	@Override
	public int getID() { return id; }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException { }

	@Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		canStart = false;
		
		title = new Button()
				.setCenterX(Settings.Resolution_X / 2)
				.setCenterY(Settings.Resolution_Y / 2)
				.setW(Settings.Resolution_X)
				.setH(Settings.Resolution_Y)
				.setImage("titleScreen")
				;
		
		startButton = new Button()
				.setCenterX(Settings.Resolution_X / 2)
				.setCenterY(Settings.Resolution_Y / 2)
				.setW(3f * (0.05208333333f * Settings.Resolution_X))
				.setH(1f * (0.09259259259f * Settings.Resolution_Y))
				.setImage("startButton")
				;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
//		g.drawString("Title", Settings.Resolution_X / 2, Settings.Resolution_Y / 2);
	
		title.render(g);
		//Draw All Buttons
		startButton.render(g);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
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