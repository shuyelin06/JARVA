package engine.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.Main;
import engine.Settings;
import ui.display.images.ImageManager;
import ui.input.Button;

public class End extends BasicGameState {
	private int id;
	
	private int timer;
	private boolean restart;
	
	private Button restartButton;
	
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
		
		timer = 60;
		restart = false;
		
		restartButton = new Button()
				.setCenterX(Settings.Resolution_X / 2)
				.setCenterY(Settings.Resolution_Y / 2)
				.setW(3f * (0.05208333333f * Settings.Resolution_X))
				.setH(1f * (0.09259259259f * Settings.Resolution_Y))
				.setImage( "restartButton" );
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int n) throws SlickException {
		timer--;
		if( restart ) {
			Settings.LastState = Main.END_ID;
			sbg.enterState(Main.TITLE_ID);
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.scale(1f, 1f);
		g.resetTransform();
		
		if( timer < 0 ) restartButton.render(g);
		
		g.setColor(Color.red);
		String endMessage = "Still a WIP! Press the placeholder image below to restart";
		g.drawString(endMessage, Main.getScreenWidth() / 2f - g.getFont().getWidth(endMessage) / 2f, Main.getScreenHeight() / 2f - 90f);
		String timeSurvived = "Time Survived (Score Feature to Come): " + Game.Ticks + " seconds";
		g.drawString(timeSurvived, Main.getScreenWidth() / 2f - g.getFont().getWidth(timeSurvived) / 2f, Main.getScreenHeight() / 2f - 75f);
	}
	
	public void mousePressed(int button, int x, int y) {
		Point mouse = new Point(x, y);
		if ( timer < 0 && restartButton.isWithin(mouse)) {
			restart = true;
		}
	}

}