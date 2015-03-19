package robot;
import robot.path.DirectedGridMesh;
import robot.path.MapNode;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

/**
 * Stores all the constants relevant to the robot. 
 * TODO: read from a .properties file
 * @author yonip
 *
 */
public class RobotMap {
	
	//======================== Mapping and Pathfinding ========================
	
	/**
	 * the path of the robot, hardcoded at the beginning of each round.
	 * effectively a list of [direction, parking/intersection, speed]
	 */
	public final int[][] PATH;
	
	public final int FORWARD;
	
	public final int LEFT;
	
	public final int RIGHT;
	
	public final int PARKING;
	
	public final int INTERSECTION;
	
	public final int FAST;
	
	public final int SLOW;
	
	public final int INTERSECTION_DISTANCE;
	
	public final int DIRECTION;
	
	public final int TYPE;
	
	public final int SPEED;
	
	public final static int START=1;
	
	public final static MapNode[] MAP=new MapNode[67];
	
	//================================ Motors =================================
	// these may not be strictly necessary, but it's good to have all constants in one place
	
	/**
	 * the left motor port
	 */
	public final char LEFT_MOTOR;
	
	/**
	 * the right motor port
	 */
	public final char RIGHT_MOTOR;
	
	/**
	 * the motor port for the motor that controls the ultrasonic sensor
	 */
	public final char ULTRASONIC_MOTOR;
	
	
	//================================ Sensors ================================

	/**
	 * port for the left color sensor
	 */
	public final Port LEFT_COLOR;
	
	/**
	 * port for the right color sensor
	 */
	public final Port RIGHT_COLOR;
	
	/**
	 * port for the ultrasonic sensor
	 */
	public final Port ULTRASONIC;
	
	
	
	/**
	 * read the values for everything, or most everything, form a properties file.
	 * only exception might be the map
	 * @param input the path of the properties file
	 */
	public RobotMap(String input) {
		System.out.println("RobotMap init start");
		this.PATH = new int[][]{ // mebbe read from file
			{0,3,6}
		};
		this.DIRECTION = 0;
		this.TYPE = 1;
		this.SPEED = 2;
		int c = 0;
		this.FORWARD = c++;
		this.LEFT = c++;
		this.RIGHT = c++;
		this.PARKING = c++;
		this.INTERSECTION = c++;
		this.FAST = 740;
		this.SLOW = 500;
		
		this.INTERSECTION_DISTANCE = 100; // 100 is temporary, pls fix
		
		this.LEFT_MOTOR = 'A';
		this.RIGHT_MOTOR = 'D';
		this.ULTRASONIC_MOTOR = 'B';
		
		this.LEFT_COLOR = SensorPort.S1;
		this.RIGHT_COLOR = SensorPort.S4;
		this.ULTRASONIC = SensorPort.S3;
		System.out.println("RobotMap init end");
	}
	
	public DirectedGridMesh makeMap() {
		
		return null;
	}
}
