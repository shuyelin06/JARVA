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
import ui.display.hud.panels.MessagePanel;
import ui.display.hud.panels.Panel;
import ui.display.images.ImageManager;
import ui.input.Button;
import ui.sound.SoundManager;

public class End extends BasicGameState {
	private int id;
	
	private int timer;
	private boolean restart;
	private boolean swapped;
	
	private MessagePanel messagePanel;
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
		
		this.messagePanel = (MessagePanel) (new MessagePanel()
				.setTextHeight( 50 )
				.setCentered(true)
				.setX( Settings.Resolution_X / 2 )
				.setY( Settings.Resolution_Y / 2)
				.setWidth( (int) (Settings.Resolution_X * 0.65f) )
				.setHeight( (int) (Settings.Resolution_Y * 0.45f) ));
		
		timer = 120;
		swapped = false;
		restart = false;
		
		restartButton = new Button()
				.setCenterX(Settings.Resolution_X / 2)
				.setCenterY(Settings.Resolution_Y / 2)
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
		
		g.setColor(Color.red);
		
		messagePanel.setMessage(
				"Still a WIP! Press the placeholder image below to restart" + "\n" + 
				"Your IQ:" + (int) Math.floor(Game.Ticks)
				);
		messagePanel.render(g);
		
		if( timer < 0 ) restartButton.render(g);
	}
	
	public void mousePressed(int button, int x, int y) {
		Point mouse = new Point(x, y);
		if ( timer < 0 && restartButton.isWithin(mouse)) {
			restart = true;
		}
	}

}