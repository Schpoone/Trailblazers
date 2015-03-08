import lejos.robotics.RegulatedMotor;

/**
 * Represents the Drive system for a robot
 * 
 * @author Jason
 */
public class Drive {
	
	protected RegulatedMotor leftMotor;
	protected RegulatedMotor rightMotor;
	
	/**
	 * Instantiates the Drive system made up of the leftMotor which controls
	 * the left wheel and the rightMotor which controls the right wheel
	 * 
	 * @param leftMotor
	 * @param rightMotor
	 */
	public Drive(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}
	
	/**
	 * This method, when called, drives the robot forward at the specified speed
	 * 
	 * @param percentMaxSpeed percentage of the maximum speed (in the form .50 for 50%)
	 * @author Jason
	 */
	public void driveForward(double percentMaxSpeed) {
		//hopefully the motors move at the same speed
		int speed = (int) (percentMaxSpeed*Math.min(leftMotor.getMaxSpeed(), rightMotor.getMaxSpeed()));
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();
	}
	
	/**
	 * This method, when called, drives the robot backward at the specified speed
	 * 
	 * @param percentMaxSpeed percentage of the maximum speed (in the form .50 for 50%)
	 * @author Jason
	 */
	public void driveBackward(double percentMaxSpeed) {
		//hopefully the motors move at the same speed
		int speed = (int) (percentMaxSpeed*Math.min(leftMotor.getMaxSpeed(), rightMotor.getMaxSpeed()));
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.backward();
		rightMotor.backward();
	}
	
	/**
	 * The right motor would move at max speed and the left motor would move less
	 * Increasing the concavity increases the difference between the speeds
	 * so, it would drive in a tighter curve
	 * 
	 * @param concavity The tightness of the curve; Must be between 0 and 1
	 * @author Jason
	 */
	public void curveLeftForwards(double concavity) {
		rightMotor.setSpeed((int) rightMotor.getMaxSpeed());
		leftMotor.setSpeed((int) (rightMotor.getSpeed() - rightMotor.getSpeed()*concavity));
		leftMotor.forward();
		rightMotor.forward();
	}
	
	/**
	 * The left motor would move at max speed and the right motor would move less
	 * Increasing the concavity increases the difference between the speeds
	 * so, it would drive in a tighter curve
	 * 
	 * @param concavity The tightness of the curve; Must be between 0 and 1
	 * @author Jason
	 */
	public void curveRightForwards(double concavity) {
		leftMotor.setSpeed((int) leftMotor.getMaxSpeed());
		rightMotor.setSpeed((int) (leftMotor.getSpeed() - leftMotor.getSpeed()*concavity));
		leftMotor.forward();
		rightMotor.forward();
	}
	
	/**
	 * The right motor would move at max speed and the left motor would move less
	 * Increasing the concavity increases the difference between the speeds
	 * so, it would drive in a tighter curve
	 * 
	 * @param concavity The tightness of the curve; Must be between 0 and 1
	 * @author Jason
	 */
	public void curveLeftBackwards(double concavity) {
		rightMotor.setSpeed((int) rightMotor.getMaxSpeed());
		leftMotor.setSpeed((int) (rightMotor.getSpeed() - rightMotor.getSpeed()*concavity));
		leftMotor.backward();
		rightMotor.backward();
	}
	
	/**
	 * The left motor would move at max speed and the right motor would move less
	 * Increasing the concavity increases the difference between the speeds
	 * so, it would drive in a tighter curve
	 * 
	 * @param concavity The tightness of the curve; Must be between 0 and 1
	 * @author Jason
	 */
	public void curveRightBackwards(double concavity) {
		leftMotor.setSpeed((int) leftMotor.getMaxSpeed());
		rightMotor.setSpeed((int) (leftMotor.getSpeed() - leftMotor.getSpeed()*concavity));
		leftMotor.backward();
		rightMotor.backward();
	}
	
}