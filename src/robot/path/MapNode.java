package robot.path;

import robot.RobotMap;

public class MapNode {
	public static int ENTRY=1;
	public static void main(String[] args){//runs a djikstra's on manual input
		MapNode local=RobotMap.MAP[ENTRY];
		
	}
	private int[] neighbors;
	private int[] dirs;//heh, cuz it's like pwd. -1=left, 1=right, 0=straight
	
	/*public enum Direction{
		LEFT,RIGHT,FORWARD //jklol, fuck this shit
	}*/
	
}
