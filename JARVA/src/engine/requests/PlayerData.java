package engine.requests;

public class PlayerData {
	
	private String name;
	private float score;
	
	public PlayerData() {
		this.score = 0;
		this.name = "null";
	}
	
	public String getName() { return name; }
	public float getScore() { return score; }
	
	public PlayerData setName(String name) {
		this.name = name;
		return this;
	}
	
	public PlayerData setScore(float score) {
		this.score = score;
		return this;
	}
	
	public String toString() {
		return getName() + "---" + getScore();
	}
}