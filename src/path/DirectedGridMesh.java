package path;

import lejos.robotics.mapping.LineMap;
import lejos.robotics.pathfinding.FourWayGridMesh;
import lejos.robotics.pathfinding.Node;

public class DirectedGridMesh extends FourWayGridMesh {

	/**
	 * Copied from leJOS documentation.
	 * 	Instantiates a grid mesh of nodes which won't interconnect between any map geometry. 
	 * 	Will also keep away the set parameter from map geometry. 
	 * 	Grid spacing is adjustable via the constructor.
	 * @param map The map containing geometry.
	 * @param gridSpace The size of each grid square.
	 * @param clearance The safety zone between all nodes/connections and the map geometry.
	 */
	public DirectedGridMesh(LineMap map, float gridSpace, float clearance) {
		super(map, gridSpace, clearance);
	}
	
	/**
	 * Does nothing
	 */
	@Override
	public int addNode(Node node, int neighbors) {
		System.err.println("What are you doing? DirectedGridMesh.addNode()");
		return 0;
	}
	
	/**
	 * Adds the 2nd node to the 1st node's neighbor list
	 * @param node1 1st node
	 * @param node2 node to add as neighbor to node1
	 * @return true if the nodes were connected successfully, false otherwise
	 */
	@Override
	public boolean connect(Node node1, Node node2) {
		return node1.addNeighbor(node2);
	}
	
	/**
	 * Removes node2 from node1's neighbors
	 * @param node1 affected node
	 * @param node2 node to remove
	 * @return true if the nodes were disconnected successfully, false otherwise
	 */
	@Override
	public boolean disconnect(Node node1, Node node2) {
		return node1.removeNeighbor(node2);
	}
	
	/**
	 * Do not remove nodes. There is no need to.
	 * @return false
	 */
	@Override
	public boolean removeNode(Node node) {
		return false;
	}

}
