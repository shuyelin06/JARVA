package engine.states;

import ui.display.images.ImageLoader;
import ui.sound.SoundLoader;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.Main;
import engine.Settings;

public class Loading extends BasicGameState {
	private StateBasedGame sbg;
	private int id;
	
	private String message;
	private int state;
	
	// Constructor
	public Loading(int id) { 
		this.id = id;
		
	}
	
	@Override
	public int getID() { return id; }

	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.sbg = sbg;
		
		this.message = "Loading";
		this.state = 0;
		
		System.out.println("Done");
	}


	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString(message, Settings.Resolution_X / 2, Settings.Resolution_Y / 2);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		switch(state) {
			case 0:
				ImageLoader.loadImages(new File("res/images/"));
				state = 1;
				message = "Loading Images";
				break;
			
			case 1:
				SoundLoader.loadSounds(new File("res/sounds/"));
				state = 2;
				message = "Loading Sounds";
				break;
			
			default:
				message = "Done";
				leave();
				break;
		}
	}
	
	private void leave() { sbg.enterState(Main.GAME_ID); }
}