package engine.requests;

public class PlayerData {
	
	private String name;
	private int score;
	
	public PlayerData() {
		this.score = 0;
		this.name = "null";
	}
	
	public PlayerData setName(String name) {
		this.name = name;
		return this;
	}
	
	public PlayerData setScore(int score) {
		this.score = score;
		return this;
	}
}