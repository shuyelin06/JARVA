package objects.geometry;

public class Projection {
	public float min;
	public float max;
	
	public Projection(float min, float max) {
		this.min = min;
		this.max = max;
	}
	
	public boolean overlaps(Projection p) {
		if(max < p.min || p.max < min) return false;
		else return true;
	}
	
	public float getMin() { return min; }
	public float getMax() { return max; }
}