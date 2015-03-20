package robot;
import java.util.LinkedList;
import java.util.Queue;

import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.pathfinding.Path;

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

	private final Queue<Path> paths;
	private Path curPath;
	private boolean isRunning;
	private int leftOwed=0;//how much the robot is facing left compared to how much it should be, measured in calls to defaultdrive
	private int rightOwed=0;//how much the robot is facing right compared to how much it should be, measured in calls to defaultdrive
	private final int spdToGetInSpace = (int) Math.min(Robot.leftMotor.getMaxSpeed(), Robot.rightMotor.getMaxSpeed());
	private final double correctionFactor = 0.8; // needs to be tested
	private int nodeIndex = 0;
	private int[] currentNode;
	
	private Thread ultrasonic;

	public Robot() {
		System.out.println("Robot init start");
		this.paths = new LinkedList<Path>();
		this.isRunning = true;
		drive.start();
		// calculate one or more paths here and add them to the queue
		System.out.println("Robot init finish");
		
		ultrasonic = new Thread(new UltrasonicMapper(ultrasonic));
	}

	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * main loop of the robot, runs once per path
	 */
	public void runPath() {
		while(nodeIndex < robotMap.PATH.length) {
			io.read(); // read from sensors

			currentNode = robotMap.PATH[nodeIndex];
			if(currentNode[1] == robotMap.INTERSECTION) {
				defaultDrive(currentNode[2]);
			}

			// maybe wait a bit
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// I am done now. DONE!

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

	private void defaultDrive(int speed, int pauseColor) {
		if(Robot.io.getSanicDistance() <= collisionThreshold) {
			Robot.drive.stop();
		} else if (pauseColor > -1 && (Robot.io.getLeftColor() == pauseColor && Robot.io.getRightColor() == pauseColor)) {
			this.stop(3);
			if(currentNode[robotMap.DIRECTION] == robotMap.RIGHT) {
				intersectionRight();
			} else if(currentNode[robotMap.DIRECTION] == robotMap.LEFT) {
				intersectionLeft();
			}
		} else if((Robot.io.getLeftColor() == Color.WHITE || Robot.io.getLeftColor() == Color.RED) && Robot.io.getRightColor() != Robot.io.getLeftColor()) {
			Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*correctionFactor));
		} else if((Robot.io.getRightColor() == Color.WHITE || Robot.io.getRightColor() == Color.RED) && Robot.io.getLeftColor() != Robot.io.getRightColor()) {
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
		try {
			this.wait(sec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * go into a parking lot to the robot's left, it is safe, and is a red parking lot, aka exit
	 * we will NOT park left into blue lots
	 */
	private void parkLeft() {
		//Assuming we have found an open spot
		//idk how to write this in a way that you can keep looping through this
		long startTime = System.currentTimeMillis();
		Robot.drive.setSpeed(spdToGetInSpace);
		Robot.drive.goForward();
		while (System.currentTimeMillis() - startTime < 100) {}

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
		long startTime = System.currentTimeMillis();
		Robot.drive.setSpeed(spdToGetInSpace);
		Robot.drive.goForward();
		while (System.currentTimeMillis() - startTime < 100) {}

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
			if(Robot.io.getLeftColor() == Color.WHITE && Robot.io.getRightColor() != Color.WHITE) { // hit the left edge, correcting...
				Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*correctionFactor));
			} else if(Robot.io.getRightColor() == Color.WHITE && Robot.io.getLeftColor() != Color.WHITE) { // hit the right edge, correcting...
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

	/**
	 * moves the robot to the middle of the intersection and turn left
	 * assumes robot is stopped and waiting time is over
	 */
	private void intersectionLeft() {
		Robot.rightMotor.resetTachoCount();
		Robot.leftMotor.resetTachoCount();
		int avgCount = (leftMotor.getTachoCount() + rightMotor.getTachoCount())/2;
		while(avgCount < robotMap.INTERSECTION_DISTANCE) {
			defaultDrive(740);
			avgCount = (leftMotor.getTachoCount() + rightMotor.getTachoCount())/2;
		}
		Robot.drive.stop();
		// turn left
		boolean leftHit = false;
		Robot.drive.setLeftVel(-spdToGetInSpace);
		Robot.drive.setRightVel(spdToGetInSpace);
		while(!leftHit) {
			Robot.io.read();
			leftHit = Robot.io.getLeftColor() == Color.WHITE;
		}
		// no overshoot, pls
		Robot.drive.stop();
	}

	/**
	 * moves the robot to the middle of the intersection and turn right
	 * assumes robot is stopped and waiting time is over
	 */
	private void intersectionRight() {
		Robot.rightMotor.resetTachoCount();
		Robot.leftMotor.resetTachoCount();
		int avgCount = (leftMotor.getTachoCount() + rightMotor.getTachoCount())/2;
		while(avgCount < robotMap.INTERSECTION_DISTANCE) {
			defaultDrive(740);
			avgCount = (leftMotor.getTachoCount() + rightMotor.getTachoCount())/2;
		}
		Robot.drive.stop();
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

	public void kill() {
		this.isRunning = false;
	}

}
