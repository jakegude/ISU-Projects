/**
 * 		@author jakegude
 */
package cs228hw4.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CS228Dijkstra<V> implements Dijkstra<V> {
	
	private DiGraph<V> mygraph;
	private HashMap<V, V> paths = new HashMap<>();
	private HashMap<V, Integer> costs = new HashMap<>();
	private V start;
	
	public CS228Dijkstra(DiGraph<V> graph) {
		mygraph = graph;
	}
	
	/**
	    * Uses Dijkstra's shortest path algorithm to calculate and store the 
	    * shortest paths to every vertex in the graph as well as the total distances
	    * of each of those paths.  This should run in O(E log V) time, where E is
	    * the size of the edge set, and V is the size of the vertex set.
	    * 
	    * @param start the vertex from which shortest paths should be calculated
	    */
	public void run(V start) {
		Iterator<V> it = mygraph.iterator();
		ArrayList<V> open = new ArrayList<>();
		ArrayList<V> closed = new ArrayList<>();
		this.start = start;
		
		open.add(start);
		
		while (it.hasNext()) {
			V v = it.next();
			costs.put(v, Integer.MAX_VALUE);
			paths.put(v, null);
		}

		costs.put(start, 0);

		while (!open.isEmpty()) {
			V u = open.get(0);

			for (int i = 0; i < open.size(); i++) {
				if (costs.get(open.get(i)) < costs.get(u)) {
					u = open.get(i);
				}
			}
			
			open.remove(u);
			closed.add(u);
			
			for (V v : mygraph.getNeighbors(u)) {
				if (!closed.contains(v)) {
					int alt = costs.get(u) + mygraph.getEdgeCost(u, v);
					if (alt < costs.get(v)) {
						costs.put(v, alt);
						paths.put(v, u);
						open.add(v);
					}
				}
			}
		}
	}

	/**
	 	* Retrieve, in O(V) time, the pre-calculated shortest path to the given 
	    * node.
	    * @param vertex the vertex to which the shortest path, from the start 
	    *        vertex, is being requested
	    * @return a list of vertices comprising the shortest path from the start 
	    *         vertex to the given destination vertex, both inclusive
	    */
	public List<V> getShortestPath(V vertex) {
		ArrayList<V> retlist = new ArrayList<>();
				
		if (vertex.equals(start) || start.equals(vertex)) {
			retlist.add(vertex);
			return retlist;
		}
		
		int index = 0;
		while (!vertex.equals(paths.get(index))) {
			index++;
		}
		
		for (int i = index; i < paths.size(); i++) {
			retlist.add(paths.get(i));
		}
		
		return retlist;
	}

	 /**
	    * Retrieve, in constant time, the total cost to reach the given vertex from
	    * the start vertex via the shortest path.  If there is no path, this value
	    * is Integer.MAX_VALUE.
	    * @param vertex the vertex to which the cost of the shortest path, from the
	    *        start vertex, is desired
	    * @return the cost of the shortest path to the given vertex from the start
	    *         vertex or Integer.MAX_VALUE if there is path
	    */
	public int getShortestDistance(V vertex) {
			return costs.get(vertex);
	}
}