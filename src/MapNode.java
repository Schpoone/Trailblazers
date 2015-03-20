

import java.util.LinkedList;

public class MapNode {
	public static void main(String[] args) {
		
	}
	public static String djikstra(int target, int start){
		MapNode[] localMap=MAP;
		LinkedList<Integer> stuff=new LinkedList<Integer>(),seen=new LinkedList<Integer>();
		stuff.add(start);
		while(stuff.size()>0){
			MapNode current=localMap[getMin(localMap, seen)];
			for (int i:current.neighbors[0]){
				if(!seen.contains(i)) stuff.add(i);
				int distance=localMap[current.index].distance+current.neighbors[1][current.getRelativeIndex(i)];
				if(distance<localMap[i].distance){
					localMap[i].previous=current.index;
					localMap[i].distance=distance;
				}
			}
			stuff.remove(stuff.indexOf(current.index));
			seen.add(current.index);
		}
		String path="";
		MapNode end=localMap[target],current=localMap[end.previous];
		while(end.previous!=-1){
			path=path+current.neighbors[2][current.getRelativeIndex(end.index)]+"\n";
		}
		return path;
			
	}
	public static final MapNode[] MAP=null;
	private int[][] neighbors;//First value is index, second distance to node, third direction
	public int distance=Integer.MAX_VALUE,previous=-1,index;
	
	public MapNode(int[] indexes, int[] distances, int[] directions){
		this.neighbors=new int[3][];
		neighbors[0]=indexes;
		neighbors[1]=distances;
		neighbors[3]=directions;
		
	}	
	public MapNode(int[][] neighbors){
		this.neighbors=neighbors;
	}
	
	public static int getMin(MapNode[] map,LinkedList<Integer> seen){
		int min=0;
		for (int i=1;i<map.length;i++){
			if(map[i].distance<map[min].distance && !seen.contains(i)){
				min=i;
			}
		}
		return min;
	}	
	public int getRelativeIndex(int absoluteIndex) {
		for(int i:neighbors[0]){
			if(i==absoluteIndex) return i;
		}
		error();
		return -1;
	}
	public static void error(){
		System.err.print("|^^^^^^^^^^^^](ﾉ◕ヮ◕)ﾉ*:･ﾟ✧\n|YOU FUCKED UP| ‘|”“”;.., ___.\n|_…_…______===|= _|__|…, ] |\n”(@ )’(@ )”“”“*|(@ )(@ )*****(@");
	}


	
}