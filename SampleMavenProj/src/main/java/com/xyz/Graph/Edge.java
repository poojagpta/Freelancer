package com.xyz.Graph;

public class Edge  {
	  private final String id; 
	  private final Vertex source;
	  private final Vertex destination;
	  private final int weight; 
	  
	  public Edge(String id, Vertex source, Vertex destination, int weight) {
	    this.id = id;
	    this.source = source;
	    this.destination = destination;
	    this.weight = weight;
	  }
	  
	  public String getId() {
	    return id;
	  }
	  public Vertex getDestination() {
	    return destination;
	  }

	  public Vertex getSource() {
	    return source;
	  }
	  public int getWeight() {
	    return weight;
	  }
	  
	  @Override
	  public String toString() {
	    return source + " " + destination;
	  }
	  
	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Edge other = (Edge) obj;
	    if (id == null) {
	      if (other.id != null)
	        return false;
	    } else if (!id.equals(other.id))
	      return false;
	    return true;
	  }
	  
	} 