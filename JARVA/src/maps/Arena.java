package maps;

import engine.Settings;

public class Arena {
	// Center Tile
	protected int centerX;
	protected int centerY;
	
	// 2D Tiles List
	protected Tile[][] tiles; // Column (x) * Row (y)
	protected int sizeX;
	protected int sizeY;
	
	public Arena(Tile[][] tiles, int centerX, int centerY) {
		this.tiles = tiles;
		this.sizeX = tiles.length;
		this.sizeY = tiles[0].length;
		
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	// Return Centers
	public int getCenterX() { return centerX; }
	public int getCenterY() { return centerY; }
	
	// Returns all tiles
	public Tile[][] getTiles() { return tiles; }
	
	// Returns the tile at the x/y tile coordinate
	public Tile getTile(int x, int y) { 
		int i = x + centerX;
		int j = y + centerY;
		
		if(i < 0 || i >= sizeX) return null;
		else if(j < 0 || j >= sizeY) return null;
		return tiles[i][j]; 
	}
	
	// Returns the tile at a x/y game coordinate
	public Tile getTile(float x, float y) {
		int i = (int) (x / Settings.Tile_Size + 0.5f);
		int j = (int) (y / Settings.Tile_Size + 0.5f);
		
		return getTile(i, j);		
	}
	
}