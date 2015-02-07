import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


public class Navigation {
	public static void main(String args[]) throws IOException {
		int[] nodes = {
			0,12,19
		};
		
		// Get paths
		LinkedList<Integer> path = getPath(nodes);
		
		// Write paths
		FileWriter fout = new FileWriter(new File("Path.txt"));
		int node,prev=0,dir,len;
		String out;
		for (int i=0; i<path.size(); i++) {
			node = path.get(i);
			if (i==0) dir = 0;
			else dir = (node==graph[prev][2]) ? 0 : ((node==graph[prev][1]) ? -1 : 1);
			len = graph[node][0];
			out = node + " " + dir + " " + len + "\n";
			fout.write(out);
			System.out.print(out);
			prev = node;
		}
		fout.flush();
		fout.close();
	}
	
	public static int n = -1;
	public static int[][] graph = {
		{ 10,   1, n, n, 0 }, // Starting node
		{100,   2, n, n, 0 },
		{140,   3, n, n, 0 },
		{ 70,  10,12, n, 0 },
		{120,   7, 1, n, 0 },
		{ 63,   6, n,17, 0 },
		{ 38,   n, 7, n, 0 },
		{ 49,   5, 8, n, 0 },
		{ 37,  20, 9, n, 0 },
		{ 27,  11, n, n, 0 },
		{ 40,   n,11, n, 0 },
		{ 10,  30, n, n, 0 },
		{ 10,   n,30, n, 0 },
		{ 10,  14, n, n, 0 },
		{ 10,  20, n, n, 0 },
		{ 10,  16, 4, n, 0 },
		{ 10,  24,18, n, 0 },
		{ 10,   n,24,18, 0 },
		{ 10,   n,19,13, 0 },
		{ 10,  28,21, n, 0 },
		{ 10,   n,28,21, 0 },
		{ 10,  30, n, n, 0 },
		{ 10,  19,13, n, 0 },
		{ 10,  15, n, n, 0 },
		{ 10,  23,32, n, 0 },
		{ 10,   n,23,32, 0 },
		{ 10,   n,22,25, 0 },
		{ 10,  22,25, n, 0 },
		{ 10,  27,37, n, 0 },
		{ 10,   n,27,37, 0 },
		{ 10,  29,40, n, 0 },
		{ 10,  45,33, n, 0 },
		{ 10,   n,45,33, 0 },
		{ 10,  36, n,34, 0 },
		{ 10,   n,35,26, 0 },
		{ 10,   n,34,36, 0 },
		{ 10,  49,38, n, 0 },
		{ 10,   n,49,38, 0 },
		{ 10,  40, n, n, 0 },
		{ 10,   n, n,34, 0 },
		{ 10,  42,55, n, 0 },
		{ 10,  30, n, n, 0 },
		{ 10,   n, n,49, 0 },
		{ 10,  44,30, n, 0 },
		{ 10,  58,46, n, 0 },
		{ 10,   n,58,46, 0 },
		{ 10,   n,48,39, 0 },
		{ 10,  48,39, n, 0 },
		{ 10,  53,50, n, 0 },
		{ 10,   n,53,50, 0 },
		{ 10,  55, n, n, 0 },
		{ 10,   n,47, n, 0 },
		{ 10,  47, n, n, 0 },
		{ 10,  52,60, n, 0 },
		{ 10,   n,52,60, 0 },
		{ 10,  54,67, n, 0 },
		{ 10,  57,43, n, 0 },
		{ 10,  63,59, n, 0 },
		{ 10,   n,63,59, 0 },
		{ 10,  65,61, n, 0 },
		{ 10,   n,65,61, 0 },
		{ 10,  67, n, n, 0 },
		{ 10,  56, n, n, 0 },
		{ 10,  62, n, n, 0 },
		{ 10,   n,62, n, 0 },
		{ 10,  64,69, n, 0 },
		{ 10,   n,64,69, 0 },
		{ 10,  66,71, n, 0 },
		{ 10,  56, n, n, 0 },
		{ 10,  68, n, n, 0 },
		{ 10,   n,68, n, 0 },
		{ 10,  70, n, n, 0 },
	};
	
	public static LinkedList<Integer> getPath(int[] nodes) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.add(nodes[0]);
		for (int i=0; i<nodes.length-1; i++) {
			path.addAll(dij(nodes[i],nodes[i+1]));
		}
		return path;
	}
	
	public static LinkedList<Integer> dij(int a,int b) {
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.push(a);
		int[][] table = new int[graph.length][2];
		for (int i=0; i<table.length; i++) {
			table[i][0] = 0;
			table[i][1] = -1;
		}
		
		int node,l,f,r;
		while (queue.size()>0 && table[b][1]==-1) {
			node = queue.poll();
			l = graph[node][1];
			f = graph[node][2];
			r = graph[node][3];
			if (l>-1 && (table[l][1]==-1 || table[node][0]+graph[l].length<table[l][0])) {
				queue.push(l);
				table[l][0] = table[node][0]+graph[l].length;
				table[l][1] = node;
			}
			if (f>-1 && (table[f][1]==-1 || table[node][0]+graph[f].length<table[f][0])) {
				queue.push(f);
				table[f][0] = table[node][0]+graph[f].length;
				table[f][1] = node;
			}
			if (r>-1 && (table[r][1]==-1 || table[node][0]+graph[r].length<table[r][0])) {
				queue.push(r);
				table[r][0] = table[node][0]+graph[r].length;
				table[r][1] = node;
			}
		}
		
		LinkedList<Integer> path = new LinkedList<Integer>();
		int current = b;
		while (current!=-1 && current!=a) {
			path.add(0,current);
			current = table[current][1];
		}
		
		return path;
	}

	public static int distance(int x1, int y1, int x2, int y2)
	{
		return (int)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}
