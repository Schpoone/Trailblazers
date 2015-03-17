package robot;
import java.util.LinkedList;
import java.util.Queue;

import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.pathfinding.Path;

/**
 * This represents the physical robot with all of its systems.
 * @author Jason
 * 
 * TODO: Add an I/O class as a field for a Robot (would have getters for all motors and sensors)
 */
public class Robot {
	
	public static final RobotMap robotMap = new RobotMap("props.properties");

	public static final RegulatedMotor leftMotor = getMotor(robotMap.LEFT_MOTOR);
	public static final RegulatedMotor rightMotor = getMotor(robotMap.RIGHT_MOTOR);
	public static final RegulatedMotor sanicMotor = getMotor(robotMap.ULTRASONIC_MOTOR);

	public static final EV3ColorSensor leftColor = new EV3ColorSensor(robotMap.LEFT_COLOR);
	public static final EV3ColorSensor rightColor = new EV3ColorSensor(robotMap.RIGHT_COLOR);

	public static final EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(robotMap.ULTRASONIC);
	public static final EV3GyroSensor gyro = new EV3GyroSensor(robotMap.GYROSCOPE);
	
	public static final MotorPair drive = new MotorPair(leftMotor, rightMotor);
	public static final IO io = new IO();
	public static final Audio audio = new Audio();

	private final Queue<Path> paths;
	private Path curPath;
	private boolean isRunning;

	public Robot() {
		System.out.println("Robot init start");
		this.paths = new LinkedList<Path>();
		this.isRunning = true;
		drive.start();
		// calculate one or more paths here and add them to the queue
		System.out.println("Robot init finish");
	}

	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * main loop of the robot, runs once per path
	 */
	public void runPath() {
		//curPath = paths.remove();
		while(isRunning) { // currently assuming empty path == done
			io.read(); // read from sensors
			// calculate what the robot should do next

			// act on calculation
			this.defaultDrive(740);

			// maybe wait a bit
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// if the next path wasn't calculated, calculate it here

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
		if (Robot.io.getLeftColor() == Color.RED && Robot.io.getRightColor() == Color.RED) {
			Robot.drive.stop();
		} else if(Robot.io.getLeftColor() == Color.WHITE) {
			Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*0.8));
		} else if(Robot.io.getRightColor() == Color.WHITE) {
			Robot.drive.setSpeedLeft((int) (Robot.drive.getSpeedLeft()*0.8));
		} else if(Robot.drive.getSpeedLeft() != Robot.drive.getSpeedRight()) {
			Robot.drive.setSpeed(Math.max(Robot.drive.getSpeedLeft(), Robot.drive.getSpeedRight()));
		} else {
			Robot.drive.setVel(speed);
			Robot.drive.goForward();
		}
	}
	
	public void kill() {
		this.isRunning = false;
	}

}
