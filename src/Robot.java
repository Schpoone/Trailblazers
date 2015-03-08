import java.util.LinkedList;
import java.util.Queue;

import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.pathfinding.Path;

public class Robot {
	public static final RobotMap robotMap = new RobotMap("props.properties");
	
	public final RegulatedMotor leftMotor = getMotor(robotMap.LEFT_MOTOR);
	public final RegulatedMotor rightMotor = getMotor(robotMap.RIGHT_MOTOR);
	
	public final EV3ColorSensor leftColor = new EV3ColorSensor(robotMap.LEFT_COLOR);
	public final EV3ColorSensor rightColor = new EV3ColorSensor(robotMap.RIGHT_COLOR);
	public final EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(robotMap.ULTRASONIC);
	public final EV3GyroSensor gyro = new EV3GyroSensor(robotMap.GYROSCOPE);
	
	private final Queue<Path> paths;
	private Path curPath;
	private boolean isRunning;
	
	public Robot() {
		paths = new LinkedList<Path>();
		isRunning = true;
		// calculate one or more paths here and add them to the queue
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * main loop of the robot, runs once per path
	 */
	public void runPath() {
		curPath = paths.remove();
		while(!curPath.isEmpty()) { // currently assuming empty path == done
			// read from sensors and such
	
			// calculate what the robot should do next
			
			// act on calculation
			
			// maybe wait a bit
		}
		// if the next path wasn't calculated, calculate it here
	}
	
	/**
	 * gets a motor based on a given port
	 * @param motor the motor's port
	 * @return the corresponding motor object
	 */
	private RegulatedMotor getMotor(char motor) {
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
			System.out.println("Motor is null");
			return null;
		}
	}
	
}
