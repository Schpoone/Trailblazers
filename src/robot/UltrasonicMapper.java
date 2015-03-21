package robot;


public class UltrasonicMapper extends Thread{

	public float[][] objects = new float[180][1];
	
	private Thread thisParent;
	
	private int currentDegree;
	private int currentCount;
	private final double gearRatio = 28.0/12;
	private final double countDegreeRatio;
	
	/**
	 * 
	 * @param parent
	 */
	public UltrasonicMapper()
	{
		//done in IO
		//Robot.ultra.enable();
		
		//this.thisParent = parent;
		
		int initialCount;
		int finalCount;
		
		Robot.sanicMotor.setSpeed(270);
		initialCount = Robot.sanicMotor.getTachoCount();
		Robot.sanicMotor.rotateTo((int) (90*gearRatio));
		finalCount = Robot.sanicMotor.getTachoCount();
		
		this.countDegreeRatio = 90.0/((double) (finalCount - initialCount));
		
	}
	
	@Override
	/**
	 * moves the sensor back and forth and keeps track of any objects it senses
	 */
	public void run() {
		// TODO Auto-generated method stub
		
		//read sensor
		//read motor position
		while(!this.isInterrupted())
		{
			this.currentCount = Robot.sanicMotor.getTachoCount();
			this.currentDegree = (int) (this.currentCount * countDegreeRatio);
			
			if(!Robot.sanicMotor.isMoving() && currentDegree < 2)
				Robot.sanicMotor.rotateTo((int) (160*gearRatio), true);
			else if(!Robot.sanicMotor.isMoving())
				Robot.sanicMotor.rotateTo(0, true);
			
			float sampleArray[] = new float[Robot.ultra.getDistanceMode().sampleSize()];
			Robot.ultra.getDistanceMode().fetchSample(sampleArray, 0);
			
			this.currentCount = Robot.sanicMotor.getTachoCount();
			this.currentDegree = (int) (this.currentCount * countDegreeRatio);
			
			this.objects[currentDegree+10][0] = sampleArray[0];
			System.out.println(currentDegree + ": " + sampleArray[0]);
		}
	}

}
