package robot;


import lejos.robotics.RegulatedMotor;

/**
 * this class is used to control a pair of motors. It is implemented to have immediate feedback 
 * from the motors when values are assigned to them by giving them both their own thread, and
 * reading the fields of this class to assign values to the motors.
 * @author yonip
 */
public class MotorPair {

	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private boolean started;
	private int speedRight;
	private int speedLeft;
	private int dirLeft;
	private int dirRight;
	private int oldSpeedRight;
	private int oldSpeedLeft;
	private int oldDirLeft;
	private int oldDirRight;
	private boolean stopRight;
	private boolean stopLeft;
	private boolean speedLeftChanged;
	private boolean speedRightChanged;
	private boolean dirLeftChanged;
	private boolean dirRightChanged;

	/**
	 * constructs an new MotorPair instance using the given motors, and initializes a motor thread
	 * @param leftMotor the left motor
	 * @param rightMotor the right motor
	 * @author yonip
	 */
	public MotorPair(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		speedRight = 0;
		speedLeft = 0;
		stopRight = true;
		stopLeft = true;
		speedLeftChanged = true;
		speedRightChanged = true;
		dirLeftChanged = false;
		dirRightChanged = false;
		started = false;
	}

	/**
	 * initializes motor threads. Call only once
	 */
	private void initThreads() {
		Thread ltMotorThread = new Thread("Left Motor Thread") {
			@Override
			public void run() {
				while(true) {
					if(stopLeft)
						leftMotor.stop();
					if(speedLeftChanged)
						leftMotor.setSpeed(speedLeft);
					if(dirLeftChanged)
						if(dirLeft == 1) {
							leftMotor.forward();
						} else {
							leftMotor.backward();
						}
					//System.out.println("left");
				}
			}
		};

		Thread rtMotorThread = new Thread("Right Motor Thread") {
			@Override
			public void run() {
				while(true) {
					if(stopRight)
						rightMotor.stop();
					if(speedRightChanged)
						rightMotor.setSpeed(speedRight);
					if(dirRightChanged)
						if(dirRight == 1) {
							rightMotor.forward();
						} else {
							rightMotor.backward();
						}
					//System.out.println("right");
				}
			}
		};

		ltMotorThread.start();
		rtMotorThread.start();
	}

	/**
	 * uses stopRight() and stopLeft() to make sure both are stopped
	 * @author yonip
	 */
	public void stop() {
		stopRight();
		stopLeft();
	}

	/**
	 * makes sure the right motor is stopped
	 * @author yonip
	 */
	public void stopRight() {
		stopRight = true;
	}

	/**
	 * makes sure the left motor is stopped
	 * @author yonip
	 */
	public void stopLeft() {
		stopLeft = true;
	}

	/**
	 * uses releaseRight() and releaseLeft() to make sure neither are stopped
	 * @author yonip
	 */
	public void release() {
		releaseRight();
		releaseLeft();
	}

	/**
	 * makes sure the right motor is not stopped
	 * @author yonip
	 */
	public void releaseRight() {
		stopRight = false;
		dirRightChanged = true;
	}

	/**
	 * makes sure the left motor is not stopped
	 * @author yonip
	 */
	public void releaseLeft() {
		stopLeft = false;
		dirLeftChanged = true;
	}

	/**
	 * uses forward() to set the direction to forwards and release to make sure the motors are not stopped
	 * @author yonip
	 */
	public void goForward() {
		forward();
		release();
	}

	/**
	 * uses setDirRight() and setDirLeft() to set the direction as forwards
	 * @author yonip
	 */
	public void forward() {
		setDirRight(1);
		setDirLeft(1);
	}

	/**
	 * sets the direction of the right motor
	 * @param dir the new direction, where 1 is forward and -1 is backward
	 * @author yonip
	 */
	public void setDirRight(int dir) {
		oldDirRight = dirRight;
		dirRight = dir;
		dirRightChanged = oldDirRight != dirRight;
	}

	/**
	 * sets the direction of the left motor
	 * @param dir the new direction, where 1 is forward and -1 is backward
	 * @author yonip
	 */
	public void setDirLeft(int dir) {
		oldDirLeft = dirLeft;
		dirLeft = dir;
		dirLeftChanged = oldDirLeft != dirLeft;
	}

	/**
	 * uses backward() to set the direction to backwards and release to make sure the motors are not stopped
	 * @author yonip
	 */
	public void goBackward() {
		backward();
		release();
	}

	/**
	 * uses setDirRight() and setDirLeft() to set the direction as backwards
	 * @author yonip
	 */
	public void backward() {
		setDirRight(-1);
		setDirLeft(-1);
	}

	/**
	 * uses setSpeedRight() and setDirRight() to set the new speed and direction for the motor
	 * assumes setSpeedRight() and setDirRight() each tell their motor to stop if the velocity is 0
	 * @param vel the velocity for the motors, where negative is backwards
	 * @author yonip
	 */
	public void setRightVel(int vel) {
		if(vel > 0) {
			setSpeedRight(vel);
			setDirRight(1);
		} else {
			setSpeedRight(-vel);
			setDirRight(-1);
		}
	}

	/**
	 * uses setSpeedLeft() and setDirLeft() to set the new speed and direction for the motor
	 * assumes setSpeedLeft() and setDirLeft() each tell the motor to stop if the velocity is 0
	 * @param vel the velocity for the motor, where negative is backwards
	 * @author yonip
	 */
	public void setLeftVel(int vel) {
		if(vel > 0) {
			setSpeedLeft(vel);
			setDirLeft(1);
		} else {
			setSpeedLeft(-vel);
			setDirLeft(-1);
		}
	}

	/**
	 * uses setVelLeft() and setVelRight() to set the new speed and direction for the motors
	 * assumes setVelLeft() and setVelRight() each tell their motor to stop if the velocity is 0
	 * @param vel the velocity for the motors, where negative is backwards
	 * @author yonip
	 */
	public void setVel(int vel) {
		setRightVel(vel);
		setLeftVel(vel);
	}

	/**
	 * uses setSpeedLeft() and setSpeedRight() to set the new speed for the motors
	 * assumes setSpeedLeft() and setSpeedRight() each tell their motor to stop if the speed is 0
	 * @param speed the new speed for the motors
	 * @author yonip
	 */
	public void setSpeed(int speed) {
		setSpeedLeft(speed);
		setSpeedRight(speed);
	}

	/**
	 * sets the speed of the left motor and updates the old speed and whether the motor changed
	 * stops the left motor if the speed is 0
	 * @param speed the new speed for the left motor
	 * @author yonip
	 */
	public void setSpeedLeft(int speed) {
		oldSpeedLeft = speedLeft;
		speedLeft = speed;
		if(speed == 0)
			stopLeft();
		speedLeftChanged = oldSpeedLeft != speedLeft;
	}

	/**
	 * sets the speed of the right motor and updates the old speed and whether the motor changed
	 * stops the right motor if the speed is 0
	 * @param speed the new speed for the right motor
	 * @author yonip
	 */
	public void setSpeedRight(int speed) {
		oldSpeedRight = speedRight;
		speedRight = speed;
		if(speed == 0)
			stopRight();
		speedRightChanged = oldSpeedRight != speedRight;
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
		setSpeed(speed);
		goForward();
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
		setSpeed(speed);
		goBackward();
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
		setSpeedRight((int) rightMotor.getMaxSpeed());
		setSpeedLeft((int) (rightMotor.getSpeed() - rightMotor.getSpeed()*concavity));
		goForward();
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
		setSpeedLeft((int) leftMotor.getMaxSpeed());
		setSpeedRight((int) (leftMotor.getSpeed() - leftMotor.getSpeed()*concavity));
		goForward();
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
		setSpeedRight((int) rightMotor.getMaxSpeed());
		setSpeedLeft((int) (rightMotor.getSpeed() - rightMotor.getSpeed()*concavity));
		goBackward();
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
		setSpeedLeft((int) leftMotor.getMaxSpeed());
		setSpeedRight((int) (leftMotor.getSpeed() - leftMotor.getSpeed()*concavity));
		goBackward();
	}

	/**
	 * @return the speed of the right motor
	 */
	public int getSpeedRight() {
		return speedRight;
	}

	/**
	 * @return the speed of the left motor
	 */
	public int getSpeedLeft() {
		return speedLeft;
	}

	/**
	 * @return the direction of the left motor
	 */
	public int getDirLeft() {
		return dirLeft;
	}

	/**
	 * @return the direction of the right motor
	 */
	public int getDirRight() {
		return dirRight;
	}

	/**
	 * @return whether the right motor is set to stop
	 */
	public boolean isStopRight() {
		return stopRight;
	}

	/**
	 * @return whether the left motor is set to stop
	 */
	public boolean isStopLeft() {
		return stopLeft;
	}

	public void start() {
		if(!started) {
			started = true;
			this.initThreads();
		}
	}

}
