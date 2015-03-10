import path.DirectedGridMesh;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.pathfinding.AstarSearchAlgorithm;
import lejos.robotics.pathfinding.NodePathFinder;
import lejos.robotics.pathfinding.ShortestPathFinder;

/**
 * Stores all the constants relevant to the robot. 
 * TODO: read from a .properties file
 * @author yonip
 *
 */
public class RobotMap {
	
	//======================== Mapping and Pathfinding ========================
	
	/**
	 * the nodes the robot has to visit, where the first is the starting point, the rest in no particular order
	 */
	public final int[] NODES;
	
	/**
	 * a map of the course
	 */
	public final DirectedGridMesh MAP;

	/**
	 * a path finder that uses MAP to find a path between two nodes
	 */
	public final NodePathFinder PATHFINDER;
	
	
	
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
	 * the port for the gyroscope
	 */
	public final Port GYROSCOPE;
	
	
	
	/**
	 * read the values for everything, or most everything, form a properties file.
	 * only exception might be the map
	 * @param input the path of the properties file
	 */
	public RobotMap(String input) {
		this.NODES = new int[]{-1,-1,-1,-1}; // TODO: fix values
		this.MAP = makeMap(); // TODO: implement this or something
		this.PATHFINDER = new NodePathFinder(new AstarSearchAlgorithm(), MAP);
		
		this.LEFT_MOTOR = 'A'; // Change pls
		this.RIGHT_MOTOR = 'A'; // Change pls
		
		this.LEFT_COLOR = SensorPort.S1; // Change pls
		this.RIGHT_COLOR = SensorPort.S1; // Change pls
		this.ULTRASONIC = SensorPort.S1; // Change pls
		this.GYROSCOPE = SensorPort.S1; // Change pls
	}
	
	public DirectedGridMesh makeMap() {
		
		return null;
	}
}
