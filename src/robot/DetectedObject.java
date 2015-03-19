package robot;

public class DetectedObject {
	private final double detectedTime;
	private double distance;
	private int degree;
	
	public DetectedObject(double time, int degree, double distance)
	{
		this.detectedTime = time;
		this.degree = degree;
		this.distance = distance;
	}
	
}
