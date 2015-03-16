import java.util.LinkedList;
import java.util.Queue;

import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
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

	public final MotorPair drive;
	public final IO io;

	public final RegulatedMotor leftMotor = getMotor(robotMap.LEFT_MOTOR);
	public final RegulatedMotor rightMotor = getMotor(robotMap.RIGHT_MOTOR);
	public final RegulatedMotor sanicMotor = getMotor(robotMap.ULTRASONIC_MOTOR);

	public final EV3ColorSensor leftColor = new EV3ColorSensor(robotMap.LEFT_COLOR);
	public final EV3ColorSensor rightColor = new EV3ColorSensor(robotMap.RIGHT_COLOR);

	public final EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(robotMap.ULTRASONIC);
	public final EV3GyroSensor gyro = new EV3GyroSensor(robotMap.GYROSCOPE);
	
	private int clrlt;
	private int clrrt;
	private float distanceCM;
	private float rate;
	private float angle;
	
	private final Queue<Path> paths;
	private Path curPath;
	private boolean isRunning;

	public Robot() {
		this.paths = new LinkedList<Path>();
		this.isRunning = true;
		this.drive = new MotorPair(leftMotor, rightMotor);
		this.io = new IO(ultra, leftColor, rightColor, null, sanicMotor);
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

			clrlt = leftColor.getColorID();
			clrrt = rightColor.getColorID();
			
			float[] store = new float[2];
			
			ultra.getDistanceMode().fetchSample(store, 0);
			distanceCM = 100*store[0];
			
			gyro.getAngleAndRateMode().fetchSample(store, 0);
			rate = store[0];
			angle = store[1];
			

			// calculate what the robot should do next

			// act on calculation

			// maybe wait a bit
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// if the next path wasn't calculated, calculate it here
		this.drive.driveForward(1);
		long endTime = System.currentTimeMillis() + 5000;
		while(System.currentTimeMillis() < endTime) {
			this.drive.forward();
			System.out.println("Running");
		}
		this.drive.stop();

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
			System.out.println("Tried to read motor " + motor + ", but can't find a match.");
			return null;
		}
	}

}
