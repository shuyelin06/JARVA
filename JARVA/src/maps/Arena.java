package maps;

import engine.Settings;

public class Arena {
	// Center Tile
	private int centerX;
	private int centerY;
	
	// 2D Tiles List
	private Tile[][] tiles;
	private int sizeX;
	private int sizeY;
	
	public Arena() {
		
	}
	
	// Returns the tile at the x/y tile coordinate
	public Tile getTile(int i, int j) { 
		if(i > sizeX && j > sizeY) return null;
		return tiles[i][j]; 
	}
	
	// Returns the tile at a x/y game coordinate
	public Tile getTile(float x, float y) {
		int i = (int) (x / Settings.Tile_Size);
		int j = (int) (y / Settings.Tile_Size);
		
		return tiles[i][j];
		
	}
	
}