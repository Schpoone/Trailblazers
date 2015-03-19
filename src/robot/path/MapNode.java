package robot.path;

import robot.RobotMap;
import java.util.LinkedList;

public class MapNode {
	private int[][] neighbors;//First value is index, second distance to node, third direction
	public int distance=Integer.MAX_VALUE,previous,index;
	
	
	public static void main(String[] args){//runs a djikstra's on manual input
		int target;
		LinkedList<Integer> stuff=new LinkedList<Integer>(),seen=new LinkedList<Integer>();
		stuff.add(RobotMap.START);
		while(stuff.size()>0){
			MapNode current=RobotMap.MAP[stuff.pop()];
			int location=current.index;
			for (int i:current.neighbors[1]){
				int distance=0;
				if (i==target){
					getDistance(i);
				}
					
				}
			}
			
		}
	}
	

	
}
