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


	public int[][] NODE_MAP = null; // waht dis even for?

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
		this.SLOW = 200;
		this.LOT_SPEED = 300;

		this.PARKING_SPACE_DISTANCE = 200;
		this.INTERSECTION_DISTANCE = 300; // 100 is temporary, pls fix

		this.LEFT_MOTOR = 'A';
		this.RIGHT_MOTOR = 'D';
		this.ULTRASONIC_MOTOR = 'B';

		this.LEFT_COLOR = SensorPort.S1;
		this.RIGHT_COLOR = SensorPort.S4;
		this.ULTRASONIC = SensorPort.S3;

		// [direction, parking/intersection, speed]
		this.PATH = new int[][]{ // mebbe read from file
				{this.LEFT,this.INTERSECTION,this.SLOW}, // get out of starting place
				{this.LEFT,this.INTERSECTION,this.SLOW} // turn onto path
		};	
		
		initMap();

		System.out.println("RobotMap init end");
	}

	public DirectedGridMesh makeMap() {

		return null;
	}

	public void initMap() {
		//{properties, nextnode, direction, length (cm), no. of stop signs, speed (cm/sec)}
		int n = 0, ne= 45, e = 90, se = 135, s = 180, sw = -135, w = -90, nw = -45;
		int fast = 16, slow = 10;

		NODE_MAP = new int[][]{
		//	{type of node, linknode1, direction1, distance1, linknode2, direction2, distance2}

/*0*/		{0,	11,	n,	25,	1,	fast},
/*1*/		{0,	12,	s,	25,	1,	fast},
/*2*/		{1,	32,	s,	445,3,	fast},
/*3*/		{1,	24,	e,	60,	1,	fast},
/*4*/		{1,	26,	e,	80,	1,	fast},
/*5*/		{1,	25,	s,	70,	1,	fast},
/*6*/		{1,	47,	s,	70,	1,	fast},
/*7*/		{1,	51,	e,	70,	1,	fast},
/*8*/		{1,	55,	s,	70,	1,	fast},
/*9*/		{1,	59,	e,	95,	1,	fast},
/*10*/		{1,	62,	n,	50,	1,	fast},
/*11*/		{3,	13,	w,	90,	1,	fast},
/*12*/		{3,	64,	e,	45,	1,	fast},
/*13*/		{3,	14,	s,	305,1,	fast},
/*14*/		{3,	32,	e,	495,3,	fast},
/*15*/		{4,	11,	w,	130,0,	fast,	17,	s,	110,1,	fast},
/*16*/		{3,	17,	s,	60,	1,	fast},
/*17*/		{4,	18,	se,	140,1,	slow,	19,	s,	85,	1,	fast},
/*18*/		{4,	16,	w,	70,	1,	slow,	3, 	e,	90,	0,	fast},
/*19*/		{3,	4,	e,	160,0,	fast},
/*20*/		{5,	0,	0,	0,	0,	0},
/*21*/		{3,	22,	s,	80,	1,	slow},
/*22*/		{3,	4,	e,	35,	0,	fast},
/*23*/		{4,	15,	w,	275,1,	fast,	24,	s, 	50,	1,	fast},
/*24*/		{2,	5,	s,	25,	0,	fast,	29,	e,	130,1,	fast},
/*25*/		{2,	21,	w,	110,1,	slow,	26,	s,	95,	1,	fast},
/*26*/		{2,	27,	s,	80,	1,	fast,	31,	e,	130,1,	fast},
/*27*/		{3,	32,	e,	130,1,	fast},
/*28*/		{3,	23,	w,	130,1,	fast},
/*29*/		{2,	28,	n,	50,	1,	fast,	34,	e,	130,1,	fast},
/*30*/		{2,	29,	n,	95,	1,	fast,	25,	e,	130,1,	fast},
/*31*/		{2,	30,	n,	80,	1,	fast,	39,	e,	130,1,	fast},
/*32*/		{4,	31,	n,	80,	1,	fast,	44,	e,	210,1,	fast},
/*33*/		{4,	28,	w,	130,1,	fast,	34,	s,	50,	1,	fast},
/*34*/		{4,	35,	s,	50,	1,	fast,	42,	e,	80,	1,	fast},
/*35*/		{3,	36,	sw,	70,	1,	slow},
/*36*/		{4,	30,	s,	95,	1,	slow,	37,	se,	70,	1,	slow},
/*37*/		{4,	39,	s,	35,	1,	slow,	38,	ne,	70,	1,	slow},
/*38*/		{3,	35,	nw,	70,	1,	slow},
/*39*/		{2,	40,	s,	80,	1,	slow,	48,	e,	155,1,	fast},
/*40*/		{3,	44,	e,	75,	1,	fast},
/*41*/		{3,	33,	w,	80,	1,	fast},
/*42*/		{4,	41,	n,	50,	1,	fast,	46,	e,	75,	1,	fast},
/*43*/		{3,	48,	e,	75,	1,	fast},
/*44*/		{4,	43,	n,	80,	1,	fast,	52,	e,	170,1,	fast},
/*45*/		{4,	33,	w,	155,1,	fast,	46,	s,	50, 1,	fast},
/*46*/		{2,	6,	s,	25,	1,	fast,	54,	e,	145,1,	fast},
/*47*/		{2,	38,	w,	110,1,	slow,	48,	s,	80,	1,	fast},
/*48*/		{2,	49,	s,	80,	1,	fast,	51,	e,	95, 1,	fast},
/*49*/		{3,	52,	e,	95,	1,	fast},
/*50*/		{3,	47,	w,	95,	1,	fast},
/*51*/		{2,	56,	e,	50,	1,	fast,	50,	n,	80,	1,	fast},
/*52*/		{4,	51,	n,	80,	1,	fast,	61,	e,	170,1,	fast},
/*53*/		{4,	45,	w,	145,1,	fast,	54,	s,	50,	1,	fast},
/*54*/		{2,	8,	s,	35,	0,	fast,	9,	e,	35,	0,	fast},
/*55*/		{4,	50,	w,	50,	1,	fast,	56,	s,	80,	1,	fast},
/*56*/		{2,	60,	e,	120,1,	fast,	57,	s, 	80,	1,	fast},
/*57*/		{3,	61,	e,	120,1,	fast},
/*58*/		{3,	53,	w,	120,1,	fast},
/*59*/		{3,	58,	n,	50,	1,	fast},
/*60*/		{2,	58,	n,	145,2,	fast,	63,	e,	95,	1,	fast},
/*61*/		{4,	64,	e,	95,	1,	fast,	60,	n,	80,	1,	fast},
/*62*/		{3,	53,	w,	115,1,	fast},
/*63*/		{3,	10,	n,	175,0,	slow},
/*64*/		{3,	63,	n,	80,	1,	slow}};
	//calculate the length of array node for dijkstra calculation
	int length = 0;
	for(int[] i:NODE_MAP){
		if(i[0] == 2 || i[0] == 4)	length+=2;
		if(i[0] == 0 || i[0] == 1 || i[0] == 3)	length+=1;
	}
	
	//Create the list of nodes containing the time distance and nodes
	EDGE = new int[length-1][4];
	int NodeIndex = 0;
	for(int i =0; i < EDGE.length; i++){
		if(NODE_MAP[NodeIndex][0] != 5) calculateEDGE(NodeIndex,new int[]{NODE_MAP[NodeIndex][1], 
				NODE_MAP[NodeIndex][2], NODE_MAP[NodeIndex][3], 
				NODE_MAP[NodeIndex][4], NODE_MAP[NodeIndex][5]}, EDGE[i] );
		if(NODE_MAP[NodeIndex].length>6){
			i++;
			calculateEDGE(NodeIndex,new int[]{NODE_MAP[NodeIndex][6], 
					NODE_MAP[NodeIndex][7], NODE_MAP[NodeIndex][8], 
					NODE_MAP[NodeIndex][9], NODE_MAP[NodeIndex][10]}, EDGE[i] );}
		NodeIndex++;
	}
	
		return;
	}
	
	//The method set edges and calculate time distance
	public void calculateEDGE(int node0, int[] property, int[] list){
		list[0] = node0;
		list[1] = property[0];
		list[3] = property[1];
		int time = (int)((double)property[2]/property[4] + (double)property[3]*3);
		if(list[0] == 3)	time+=2;
		if(list[0] == 4 || list[0] == 2) time +=3;
		list[2] = time;
	}
}
