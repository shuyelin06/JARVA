package ui.display.hud.panels;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import engine.requests.PlayerData;
import engine.requests.Request;
import engine.states.End;
import engine.states.Game;

public class LeaderBoard extends Panel {
	
	MessagePanel title;
	MessagePanel playerScore;
	MessagePanel[] userDisplays;
	
	public LeaderBoard() {
		this.title = new MessagePanel();
		this.playerScore = new MessagePanel();
		
		userDisplays = new MessagePanel[End.LeaderboardCount + 1];
		for( int i = 0; i < userDisplays.length; i++ ) {
			userDisplays[i] = new MessagePanel();
		}
	}
	
	@Override
	public void render(Graphics g) {
		title.render(g);
		playerScore.render(g);
		for(MessagePanel p: userDisplays) { p.render(g); }
	}
	
	// Clear the content on the leaderboard
	public void clear() {
		final String Separator = "     ---     ";
		
		playerScore.setMessage("Your IQ (Score): " + Game.Ticks);
		userDisplays[0].setMessage("Username" + Separator + "Score");
		
		for(int i = 1; i < userDisplays.length; i++ ) {
			userDisplays[i]
					.setMessage("");
		}
	}
	
	public void refresh() {
		final String Separator = "     ---     ";
		
		ArrayList<PlayerData> data = Request.GET(End.LeaderboardCount);
		
		playerScore.setMessage("Your IQ (Score): " + Game.Ticks);
		userDisplays[0].setMessage("Username" + Separator + "Score");
		if(data == null) {
			for(int i = 1; i < userDisplays.length; i++ ) {
				userDisplays[i]
						.setMessage("Unable to retrieve data");
			}
		} else {
			for(int i = 1; i < userDisplays.length; i++ ) {
				if( i > data.size() ) break;
				
				PlayerData player = data.get(i - 1);
				userDisplays[i]
						.setMessage(player.getName() + Separator + player.getScore());
			}
		}
		
	}
	
	public void initialize() {
		title
			.setMessage("Leaderboard")
			.setTextColor(Color.darkGray)
			.setTextHeight(50)
			.setCentered(true)
			.setPadding(15f);
		title
			.setX(x)
			.setY(y - h / 2 - (int) (h * 0.15f) )
			.setWidth((int) (w * 0.65f))
			.setHeight((int) (h * 0.10f));
		
		playerScore
			.setMessage("Your IQ (Score): ")
			.setTextColor(Color.red)
			.setTextHeight(40)
			.setCentered(true)
			.setPadding(15f);
		playerScore
			.setX(x)
			.setY(y - h / 2 - (int) (h * 0.05f) )
			.setWidth((int) (w * 0.65f))
			.setHeight((int) (h * 0.10f));
		
		for( int i = 0; i < userDisplays.length; i++ ) {
			userDisplays[i]
					.setCentered(true)
					.setTextColor(Color.black)
					.setTextHeight(40)
					.setX(x)
					.setY(y - h / 2 + (int) ((i + 0.5f) * h / userDisplays.length))
					.setWidth( (int) (w * 0.90f) )
					.setHeight( h / userDisplays.length );
		}
		userDisplays[0]
				.setTextHeight(60)
				.setTextColor(Color.darkGray);
	}
}