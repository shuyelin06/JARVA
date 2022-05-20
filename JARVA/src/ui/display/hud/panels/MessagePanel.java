package ui.display.hud.panels;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import engine.Main;

public class MessagePanel extends Panel {
	
	private LinkedList<String> messages; // Message queue
	
	private float textHeight; // text height
	private float padding; // corner padding
	
	private boolean centered; // center text or not
	
	private Color textColor; // background color
	private String message; // current message
	
	private int timer; // blinking timer
	
	// Default Constructor
	public MessagePanel() {
		this.centered = false;
		
		this.timer = 60;
		this.messages = new LinkedList<>();
		
		this.textHeight = 20f;
		this.padding = 15f;
		
		this.message = "";
	}
	
	// Helper Methods
	public void addMessage(String string) {
		this.timer = 60;
		this.messages.addLast(string);
	}
	public void nextMessage() {
		if( messages.size() > 0 ) message = messages.pop();
	}
	
	// Accessor Methods
	public int getQueueSize() { return messages.size(); }
	public boolean getCentered() { return centered; }
	
	public String getMessage() { return message; }
	
	// Mutator Methods
	public MessagePanel setMessage(String newMessage) { this.message = newMessage; return this; }
	public MessagePanel setTextColor(Color newColor) { this.textColor = newColor; return this; }
	
	public MessagePanel setTextHeight(float newHeight) { this.textHeight = newHeight; return this; }
	
	public MessagePanel setPadding(float newPadding) { this.padding = newPadding; return this; }
	public MessagePanel setCentered(boolean center) { this.centered = center; return this; }
	
	@Override
	protected void panelRender(Graphics g) {
		// If there are more messages, draw a little triangle in the bottom corner
		if( messages.size() > 0 ) {
			timer++;
			if( timer > 60 ) {
				g.setColor(Color.white);
				float size = (float) Math.sqrt( w * w + h * h ) * 0.01f;
				g.fillOval(x + w / 2 - padding, y + h / 2 - padding, size, size);
				
				if( timer > 120 ) {
					timer = 0;
				}
			}
		}
		
		// Determine Corners for Drawing Text
		final int TopLeftX = (int) (x - w / 2 + padding);
		final int TopLeftY = (int) (y - h / 2 + padding);
		
		// Determine Extent to Which Text can be Drawn
		final float Scale = textHeight / g.getFont().getLineHeight();
		g.scale(Scale, Scale);
		
		final int DrawWidth = (int) (w - 4 * padding); 
		ArrayList<String> lines = splitText((int) (DrawWidth / Scale), g.getFont());
		
		// Draw Text
		final int DrawHeight = (int) (h - 2 * padding);
		
		String drawnText = "";
		String remainingText = "";
		
		g.setColor(textColor);
		for( int i = 0; i < lines.size(); i++ ) {
			// If the line will go out of the box, move to another textbox
			if( textHeight * i > DrawHeight ) {
				for( int j = i; j < lines.size(); j++ ) {
					remainingText += lines.get(j);
				}
				this.message = drawnText;
				
				if( messages.size() > 0 ) messages.add(1, remainingText);
				else messages.add(remainingText);
				
				
				break;
			} 
			// Else, draw the text
			else {
				drawnText += lines.get(i);
				if(centered) {
					final float Width = g.getFont().getWidth(lines.get(i)) * Scale;
					g.drawString(lines.get(i), (x - Width / 2) / Scale, (TopLeftY + textHeight * 0.85f * i) / Scale );
				} else {
					g.drawString(lines.get(i), TopLeftX / Scale, (TopLeftY + textHeight * 0.85f * i) / Scale );
				}
			}	
		}
		
		// Rescale to default scaling
		g.scale(1 / Scale, 1 / Scale);
	}
	
	// Split text to fit in the box
	private ArrayList<String> splitText(int DrawExtent, Font Font) {
		ArrayList<String> lines = new ArrayList<>();
		
		String[] split = message.split(" ");
		
		String line = "";
		int extent = 0;
		for(String text: split) {
			int width = (int) (Font.getWidth(text + " ") * 1.15f);
			
			// If can append
			if( extent + width < DrawExtent ) {
				line += text + " ";
				extent += width;
			} else {
				lines.add(line);
				
				line = text + " ";
				extent = width;
			}
		}
		lines.add(line);
		
		return lines;
	}
	
	@Override
	public boolean click(float x, float y) { 
		if( isWithin(x, y) ) {
			nextMessage();
			return true; 
		} else return false;
	}
}