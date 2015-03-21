package robot;

import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;

/**
 * This represents the physical robot with all of its systems.
 * @author Jason
 */
public class Robot {

	public static final RobotMap robotMap = new RobotMap("props.properties");

	public static final RegulatedMotor leftMotor = getMotor(robotMap.LEFT_MOTOR);
	public static final RegulatedMotor rightMotor = getMotor(robotMap.RIGHT_MOTOR);
	public static final RegulatedMotor sanicMotor = getMotor(robotMap.ULTRASONIC_MOTOR);

	public static final EV3ColorSensor leftColor = new EV3ColorSensor(robotMap.LEFT_COLOR);
	public static final EV3ColorSensor rightColor = new EV3ColorSensor(robotMap.RIGHT_COLOR);

	public static final EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(robotMap.ULTRASONIC);

	public static final MotorPair drive = new MotorPair(leftMotor, rightMotor);
	public static final IO io = new IO();
	public static final Audio audio = new Audio();

	private final float collisionThreshold = .05f;//needs to be tested

	private boolean isRunning;
	private final int spdToGetInSpace = (int) Math.min(Robot.leftMotor.getMaxSpeed(), Robot.rightMotor.getMaxSpeed());
	private final double correctionFactor = 0.8; // needs to be tested
	private int nodeIndex = 0;
	private int[] currentNode;
	
	private UltrasonicMapper ultrasonic;

	public Robot() {
		System.out.println("Robot init start");
		this.isRunning = true;

		drive.start();
		ultrasonic = new UltrasonicMapper();
		System.out.println("Robot init finish");
		
	}

	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * main loop of the robot, runs once per path
	 */
	public void runPath() {
		//Robot.audio.call();
		ultrasonic.start();
		while(nodeIndex < robotMap.PATH.length) {
			io.read(); // read from sensors

			currentNode = robotMap.PATH[nodeIndex]; // is the current node. don touch
			
			if(currentNode[robotMap.TYPE_INDEX] == robotMap.INTERSECTION) {
				defaultDrive(currentNode[robotMap.SPEED_INDEX]);
			}
			
			if (currentNode[robotMap.TYPE_INDEX] == robotMap.PARKING) {
				while (!driveThroughLot(true, currentNode[robotMap.DIRECTION_INDEX])) {}
				if (currentNode[robotMap.DIRECTION_INDEX] == robotMap.LEFT) {
					parkLeft();
				}
				if (currentNode[robotMap.DIRECTION_INDEX] == robotMap.RIGHT) {
					parkRight();
				}
				wait(3000);
				if (currentNode[robotMap.DIRECTION_INDEX] == robotMap.LEFT) {
					//unparkLeft();
				}
				if (currentNode[robotMap.DIRECTION_INDEX] == robotMap.RIGHT) {
					unparkRight();//WHY DOESN'T THIS EXIST
					driveThroughLot(false, currentNode[robotMap.DIRECTION_INDEX]);
				}
				nodeIndex++;
			}
			
			if (currentNode[robotMap.TYPE_INDEX] == robotMap.DRIVE_BY_LOT) {
				driveThroughLot(false, currentNode[robotMap.DIRECTION_INDEX]);
				nodeIndex++;
			}

			// maybe wait a bit
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// I am done now. DONE!
		Robot.drive.stop();
		//Robot.audio.call();
	}

	/**
	 * gets a motor based on a given port
	 * @param motor the motor's port
	 * @return the corresponding motor object
	 */
	private static RegulatedMotor getMotor(char motor) {
		switch(motor) {
		case 'A':
			return Motor.A;
		case 'B':
			return Motor.B;
		case 'C':
			return Motor.C;
		case 'D':
			return Motor.D;
		default:
			System.out.println("Tried to read motor " + motor + ", but can't find a match.");
			return null;
		}
	}

	private void defaultDrive(int speed) {
		this.defaultDrive(speed, Color.RED);
	}
	
	/**
	 * Drives forward at a speed until it reaches a specified color in front of it
	 * Will stop if a collision is detected
	 * 
	 * @param speed that the robot should move at
	 * @param pauseColor is the color the robot needs to stop at
	 */
	private void defaultDrive(int speed, int pauseColor) {
		if(Robot.io.getLeftColor() != Color.BLACK)
			//System.out.println("Color: " + Robot.io.getLeftColor());
		if(ultrasonic.objects[90][0] <= collisionThreshold) {
			Robot.drive.stop();
		} else if (pauseColor > -1 && (Robot.io.getLeftColor() == pauseColor && Robot.io.getRightColor() == pauseColor)) {
			System.out.println("Hit red line");
			this.stop(3);
			if(currentNode[robotMap.DIRECTION_INDEX] == robotMap.RIGHT) {
				intersectionRight();
			} else if(currentNode[robotMap.DIRECTION_INDEX] == robotMap.LEFT) {
				intersectionLeft();
			}
			nodeIndex++;
			System.out.println(nodeIndex);
		} else if((Robot.io.getLeftColor() == Color.WHITE || Robot.io.getLeftColor() == Color.YELLOW || Robot.io.getLeftColor() == Color.RED) && Robot.io.getRightColor() != Robot.io.getLeftColor()) {
			System.out.println("Line of left");
			Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*correctionFactor));
		} else if((Robot.io.getRightColor() == Color.WHITE || Robot.io.getLeftColor() == Color.YELLOW || Robot.io.getRightColor() == Color.RED) && Robot.io.getLeftColor() != Robot.io.getRightColor()) {
			System.out.println("Line on right");
			Robot.drive.setSpeedLeft((int) (Robot.drive.getSpeedLeft()*correctionFactor));
		} else if(Robot.drive.getSpeedLeft() != Robot.drive.getSpeedRight()) {
			Robot.drive.setSpeed(Math.max(Robot.drive.getSpeedLeft(), Robot.drive.getSpeedRight()));
		} else {
			Robot.drive.setSpeed(speed);
			Robot.drive.goForward();
		}
	}

	/**
	 * Stop for a certain amount of seconds
	 * @param sec
	 */
	private void stop(int sec) {
		Robot.drive.stop();
		this.wait(sec * 1000);
	}

	/**
	 * go into a parking lot to the robot's left, it is safe, and is a red parking lot, aka exit
	 * we will NOT park left into blue lots
	 */
	private void parkLeft() {
		//Assuming we have found an open spot
		//idk how to write this in a way that you can keep looping through this
		Robot.drive.setSpeed(spdToGetInSpace);
		Robot.drive.goForward();
		wait(100);

		// turn left 90 degrees, aligning with the red line line
		boolean leftPassed = false, rightPassed = false;
		while(!leftPassed || !rightPassed) {
			// turn left
			Robot.drive.setLeftVel(-spdToGetInSpace);
			Robot.drive.setRightVel(spdToGetInSpace);

			Robot.io.read();
			leftPassed = Robot.io.getLeftColor() == Color.RED;
			rightPassed = Robot.io.getLeftColor() == Color.RED;
		}
		// good nuff, dont overshoot, pls
		Robot.drive.stop();

		// theoretically we are facing the parking space head on, treat it like a path, but with a wall
		while (Robot.io.getLeftColor() != Color.WHITE && Robot.io.getRightColor() != Color.WHITE) { // while we're not at the back of the parking space
			Robot.io.read();
			if(Robot.io.getLeftColor() == Color.WHITE && Robot.io.getRightColor() != Color.WHITE) { // hit the left edge, correcting...
				Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*correctionFactor));
			} else if(Robot.io.getRightColor() == Color.WHITE && Robot.io.getLeftColor() != Color.WHITE) { // hit the right edge, correcting...
				Robot.drive.setSpeedLeft((int) (Robot.drive.getSpeedLeft()*correctionFactor));
			} else { // we're fine now, proceed as planned
				Robot.drive.setSpeed(spdToGetInSpace); 
				Robot.drive.goForward();
			}

		}
		Robot.drive.stop();
	}

	/**
	 * go into a parking lot to the robot's right, it is safe, and is a blue parking lot, aka exit
	 * we will ONLY park right into blue lots
	 */
	private void parkRight() {
		//Assuming we have found an open spot
		//idk how to write this in a way that you can keep looping through this
		Robot.drive.setSpeed(spdToGetInSpace);
		Robot.drive.goForward();
		wait(100);

		//turn right 90 degrees, aligning with the blue line line
		boolean leftPassed = false, rightPassed = false;
		// turn right
		Robot.drive.setLeftVel(spdToGetInSpace);
		Robot.drive.setRightVel(-spdToGetInSpace);
		while(!leftPassed || !rightPassed) {
			Robot.io.read();
			leftPassed = Robot.io.getLeftColor() == Color.BLUE;
			rightPassed = Robot.io.getLeftColor() == Color.BLUE;
		}
		// good nuff, dont overshoot, pls
		Robot.drive.stop();

		// theoretically we are facing the parking space head on, treat it like a path, but with a wall
		while (Robot.io.getLeftColor() != Color.WHITE && Robot.io.getRightColor() != Color.WHITE) { // while we're not at the back of the parking space
			Robot.io.read();
			if(Robot.io.getLeftColor() == Color.WHITE && Robot.io.getRightColor() != Color.WHITE) { // hit the left edge, correcting...
				Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*correctionFactor));
			} else if(Robot.io.getRightColor() == Color.WHITE && Robot.io.getLeftColor() != Color.WHITE) { // hit the right edge, correcting...
				Robot.drive.setSpeedLeft((int) (Robot.drive.getSpeedLeft()*correctionFactor));
			} else { // we're fine now, proceed as planned
				Robot.drive.setSpeed(spdToGetInSpace); 
				Robot.drive.goForward();
			}

		}
		Robot.drive.stop();
	}

	/**
	 * get out of a blue parking space. we have the right of way here.
	 * leaves robot facing down the road to the next node
	 */
	private void unparkLeft() {
		while (Robot.io.getLeftColor() != Color.BLUE && Robot.io.getRightColor() != Color.BLUE) { // while we are not out of the parking space
			Robot.io.read();
			if((Robot.io.getLeftColor() == Color.WHITE || Robot.io.getLeftColor() == Color.YELLOW) && (Robot.io.getRightColor() != Color.WHITE || Robot.io.getRightColor() == Color.YELLOW)) { // hit the left edge, correcting...
				Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*correctionFactor));
			} else if((Robot.io.getRightColor() == Color.WHITE || Robot.io.getRightColor() == Color.YELLOW) && (Robot.io.getLeftColor() != Color.WHITE || Robot.io.getLeftColor() == Color.YELLOW)) { // hit the right edge, correcting...
				Robot.drive.setSpeedLeft((int) (Robot.drive.getSpeedLeft()*correctionFactor));
			} else { // we're fine now, proceed as planned
				Robot.drive.setSpeed(spdToGetInSpace); 
				Robot.drive.goBackward();
			}
		}
		// now we're out
		// turn right
		boolean rightHit = false;
		Robot.drive.setLeftVel(spdToGetInSpace);
		Robot.drive.setRightVel(-spdToGetInSpace);
		while(!rightHit) {
			Robot.io.read();
			rightHit = Robot.io.getRightColor() == Color.WHITE;
		}
		// no overshoot, pls
		Robot.drive.stop();
	}
	
	private void unparkRight() {
		while (Robot.io.getLeftColor() != Color.BLUE && Robot.io.getRightColor() != Color.BLUE) { // while we are not out of the parking space
			Robot.io.read();
			if((Robot.io.getLeftColor() == Color.WHITE || Robot.io.getLeftColor() == Color.YELLOW) && (Robot.io.getRightColor() != Color.WHITE || Robot.io.getRightColor() == Color.YELLOW)) { // hit the left edge, correcting...
				Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*correctionFactor));
			} else if((Robot.io.getRightColor() == Color.WHITE || Robot.io.getRightColor() == Color.YELLOW) && (Robot.io.getLeftColor() != Color.WHITE || Robot.io.getLeftColor() == Color.YELLOW)) { // hit the right edge, correcting...
				Robot.drive.setSpeedLeft((int) (Robot.drive.getSpeedLeft()*correctionFactor));
			} else { // we're fine now, proceed as planned
				Robot.drive.setSpeed(spdToGetInSpace); 
				Robot.drive.goBackward();
			}
		}
		// now we're out
		// turn right
		boolean leftHit = false;
		Robot.drive.setRightVel(spdToGetInSpace);
		Robot.drive.setLeftVel(-spdToGetInSpace);
		while(!leftHit) {
			Robot.io.read();
			leftHit = Robot.io.getLeftColor() == Color.WHITE || Robot.io.getLeftColor() == Color.YELLOW;
		}
		// no overshoot, pls
		Robot.drive.stop();
	}

	/**
	 * moves the robot to the middle of the intersection and turn left
	 * assumes robot is stopped and waiting time is over
	 */
	private void intersectionLeft() {
		moveForward(robotMap.INTERSECTION_DISTANCE, 740);
		// turn left
		boolean leftHit = false;
		Robot.drive.setLeftVel(-spdToGetInSpace);
		Robot.drive.setRightVel(spdToGetInSpace);
		while(!leftHit) {
			Robot.io.read();
			leftHit = Robot.io.getLeftColor() == Color.WHITE || Robot.io.getLeftColor() == Color.YELLOW;
		}
		// no overshoot, pls
		Robot.drive.stop();
	}

	/**
	 * moves the robot to the middle of the intersection and turn right
	 * assumes robot is stopped and waiting time is over
	 */
	private void intersectionRight() {
		moveForward(robotMap.INTERSECTION_DISTANCE, robotMap.SLOW);
		// turn right
		boolean rightHit = false;
		Robot.drive.setLeftVel(spdToGetInSpace);
		Robot.drive.setRightVel(-spdToGetInSpace);
		while(!rightHit) {
			Robot.io.read();
			rightHit = Robot.io.getRightColor() == Color.WHITE || Robot.io.getRightColor() == Color.YELLOW;
		}
		// no overshoot, pls
		Robot.drive.stop();
	}
	
	/**
	 * Will drive through a parking lot at parking lot speeds
	 * Will check forward and to the side(s) where there is a lot
	 * Will stop when open spot (where we need to park) is found
	 * @param isParking whether we need to park in this lot or not
	 * @return true when an open space has been found
	 */
	private boolean driveThroughLot(boolean isParking, int direction) {
		if (/*direction where object was detected = direction*/isParking) { 
			wait(3000);
			if (ultrasonic.objects[90][0] <= collisionThreshold)//check forward
				moveForward(robotMap.PARKING_SPACE_DISTANCE, robotMap.LOT_SPEED);
			if (isParking) return true;
		} else if(Robot.io.getSanicDistance() <= collisionThreshold) {
			Robot.drive.stop();
		} else if((Robot.io.getLeftColor() == Color.WHITE || Robot.io.getLeftColor() == Color.BLUE) && Robot.io.getRightColor() != Robot.io.getLeftColor()) {
			Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*correctionFactor));
		} else if((Robot.io.getRightColor() == Color.WHITE || Robot.io.getRightColor() == Color.BLUE) && Robot.io.getLeftColor() != Robot.io.getRightColor()) {
			Robot.drive.setSpeedLeft((int) (Robot.drive.getSpeedLeft()*correctionFactor));
		} else if(Robot.drive.getSpeedLeft() != Robot.drive.getSpeedRight()) {
			Robot.drive.setSpeed(Math.max(Robot.drive.getSpeedLeft(), Robot.drive.getSpeedRight()));
		} else {
			Robot.drive.setSpeed(robotMap.LOT_SPEED);
			Robot.drive.goForward();
		}
		return false;
	}
	
	private void wait(int milliseconds) {
		long want = System.currentTimeMillis()+milliseconds;
		while (System.currentTimeMillis() < want) {
			
		}
	}
	
	private void moveForward(int distance, int speed) {
		Robot.rightMotor.resetTachoCount();
		Robot.leftMotor.resetTachoCount();
		int avgCount = (leftMotor.getTachoCount() + rightMotor.getTachoCount())/2;
		while(avgCount < distance) {
			defaultDrive(speed);
			avgCount = (leftMotor.getTachoCount() + rightMotor.getTachoCount())/2;
		}
		Robot.drive.stop();
	}

	public void kill() {
		this.isRunning = false;
	}

}
