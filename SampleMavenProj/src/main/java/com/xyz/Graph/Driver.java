package com.xyz.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Driver {

	public Graph buildGraph(List<String> wordFile) {
		Graph g = null;
		List<ArrayList<String>> buckets = new ArrayList<ArrayList<String>>();

		// Create the buckets of words differ by one letter
		for (int i = 0; i < wordFile.size(); i++) {
			String word = wordFile.get(i);

			for (int j = 0; j < word.length(); j++) {
				StringBuilder b = new StringBuilder(word);
				b.replace(j, j + 1, "_");
				boolean added = false;
				for (int k = 0; k < buckets.size(); k++) {
					if (b.toString().equals(buckets.get(k).get(0))) {
						buckets.get(k).add(word);
						added = true;
						break;
					}
				}
				if (!added) {
					ArrayList bucket = new ArrayList();
					bucket.add(b.toString());
					bucket.add(word);
					buckets.add(bucket);
				}

			}
		}

		// Now create graph by creating vertices and edges
		List<Vertex> vertexList = new ArrayList<Vertex>();
        List<Edge> edgesList=new ArrayList<Edge>();
		for (ArrayList<String> bucket : buckets) {
			for (int i = 1; i < bucket.size(); i += 2) {
				Vertex source = new Vertex(bucket.get(i), bucket.get(i));
				vertexList.add(source);
				int count=0;
				if(i==bucket.size()-1){
					count=1;
				}else{
					count++;
				}
				
				Vertex destination = new Vertex(bucket.get(count),
						bucket.get(count));
				vertexList.add(destination);
				Edge edge = new Edge(bucket.get(i) + "_" + bucket.get(count),
						source, destination, 1);

				edgesList.add(edge);
			}			
		}

		g=new Graph(vertexList, edgesList);
		return g;
	}

	 private List<Vertex> nodes;
	  private List<Edge> edges;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	/*	List<String> words=new ArrayList<String>();
		words.add("fool");
		words.add("pool");
		words.add("cool");
		words.add("tool");
		
		words.add("foul");
		words.add("foil");
		words.add("poll");
		words.add("fail");
		
		
		words.add("page");
		words.add("pale");
		words.add("sale");
		words.add("pole");
		words.add("pope");
		words.add("sage");
		
		
		Driver driver=new Driver();
		
		driver.buildGraph(words);*/

		Driver driver=new Driver();
		driver.nodes = new ArrayList<Vertex>();
		driver.edges = new ArrayList<Edge>();
		    for (int i = 0; i < 11; i++) {
		      Vertex location = new Vertex("Node_" + i, "Node_" + i);
		      driver.nodes.add(location);
		    }

		    driver.addLane("Edge_0", 0, 1, 85);
		    driver.addLane("Edge_1", 0, 2, 217);
		    driver.addLane("Edge_2", 0, 4, 173);
		    driver.addLane("Edge_3", 2, 6, 186);
		    driver.addLane("Edge_4", 2, 7, 103);
		    driver.addLane("Edge_5", 3, 7, 183);
		    driver.addLane("Edge_6", 5, 8, 250);
		    driver.addLane("Edge_7", 8, 9, 84);
		    driver.addLane("Edge_8", 7, 9, 167);
		    driver.addLane("Edge_9", 4, 9, 502);
		    driver.addLane("Edge_10", 9, 10, 40);
		    driver.addLane("Edge_11", 1, 10, 600);

		    // Lets check from location Loc_1 to Loc_10
		    Graph graph = new Graph(driver.nodes, driver.edges);
		    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		    dijkstra.execute(driver.nodes.get(0));
		    LinkedList<Vertex> path = dijkstra.getPath(driver.nodes.get(10));
	}
	 private  void addLane(String laneId, int sourceLocNo, int destLocNo,
		      int duration) {
		    Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
		    edges.add(lane);
		  }

}
