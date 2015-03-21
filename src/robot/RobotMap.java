package robot;
import robot.path.DirectedGridMesh;
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


	public final int[][] PATH_PROPERTIES = null; // waht dis even for?

	/**
	 * the path of the robot, hardcoded at the beginning of each round.
	 * effectively a list of [direction, parking/intersection, speed]
	 */
	public final int[][] PATH;

	public final int DIRECTION_INDEX;

	public final int TYPE_INDEX;

	public final int SPEED_INDEX;

	//Directions
	public final int FORWARD ;

	public final int LEFT;

	public final int RIGHT;

	//Types
	public final int PARKING;

	public final int DRIVE_BY_LOT;

	public final int INTERSECTION;

	//Speeds
	public final int FAST;

	public final int SLOW;

	public final int LOT_SPEED;//arbitrary value

	//Random constants

	public final int PARKING_SPACE_DISTANCE; // 100 is temporary, pls fix

	public final int INTERSECTION_DISTANCE; // 100 is temporary, pls fix

	//public final static MapNode[] MAP=new MapNode[67];

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

		this.DIRECTION_INDEX = 0;
		this.TYPE_INDEX = 1;
		this.SPEED_INDEX = 2;
		this.FORWARD = 0;
		this.LEFT = 1;
		this.RIGHT = 2;
		this.PARKING = 3;
		this.DRIVE_BY_LOT = 4;
		this.INTERSECTION = 5;
		this.FAST = 740;
		this.SLOW = 500;
		this.LOT_SPEED = 300;

		this.PARKING_SPACE_DISTANCE = 100;
		this.INTERSECTION_DISTANCE = 100; // 100 is temporary, pls fix

		this.LEFT_MOTOR = 'A';
		this.RIGHT_MOTOR = 'D';
		this.ULTRASONIC_MOTOR = 'B';

		this.LEFT_COLOR = SensorPort.S1;
		this.RIGHT_COLOR = SensorPort.S4;
		this.ULTRASONIC = SensorPort.S3;

		// [direction, parking/intersection, speed]
		this.PATH = new int[][]{ // mebbe read from file
				{this.FORWARD,this.INTERSECTION,this.SLOW}
		};	

		System.out.println("RobotMap init end");
	}

	public DirectedGridMesh makeMap() {

		return null;
	}

	public void initMap() {
		//{properties, nextnode, direction, length (cm), no. of stop signs, speed (cm/sec)}
		int n = 0, ne= 45, e = 90, se = 135, s = 180, sw = -135, w = -90, nw = -45;
		int fast = 16, slow = 10;

		//		this.PATH_PROPERTIES = new int[][]{ // mebbe read from file
		//		/*0-4*/  {0,11,n,25,1,fast},{0,12,s,25,1,fast},{1,32,e,560,3,fast},{1,24,},{},
		//		/*5-9*/  {},{},{},{},{},
		//		/*10-14*/{},{},{},{},{},
		//		/*15-19*/{},{},{},{},{},
		//		/*20-24*/{},{},{},{},{},
		//		/*25-29*/{},{},{},{},{},
		//		/*30-34*/{},{},{},{},{},
		//		/*35-39*/{},{},{},{},{},
		//		/*40-44*/{},{},{},{},{},
		//		/*45-49*/{},{},{},{},{},
		//		/*50-54*/{},{},{},{},{},
		//		/*55-59*/{},{},{},{},{},
		//		/*60-64*/{},{},{},{},{},
		//		/*65-69*/{},{},{},{},{},
		//		};
		return;
	}
}
