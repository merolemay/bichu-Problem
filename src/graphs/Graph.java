package graphs;

import java.util.*;

public class Graph<T> {
	
	Map<T, Position> vertexMap;
    List<Vertex> vertices;
    List<Edge> edges;

    public Graph(){
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    //creates a new vertex and places it in map
    public void newVertex(T value, double x, double y){
        vertices.add(new Vertex(value,x,y));
    }

    //creates new edge and places it in list
    public void newEdge(Vertex vertexA, Vertex vertexB, Double weight){
        vertexA.addNeighbour(vertexB, weight);
    }

    //retrieves list of all vertices
    public List<Vertex> getVertices(){
        return vertices;
    }

    public Vertex getVertex(T value){
        Vertex toReturn = null;
        for(Vertex v: vertices){
            if (v.getValue().equals(value)){
                toReturn = v;
            }
        }
        return toReturn;
    }

    public void deleteVertex(Vertex vertex){
        vertex.clearNeighbours();
        vertices.remove(vertex);
    }

    //returns list of all edges
    public List<Edge> getEdges() {
        return edges;
    }

    //retrieves position of given vertex
    public Position position(Vertex vertex){
        return vertex.getPosition();
    }

    //retrieves list verices connected to a given vertex
    public List<Vertex> neighbours(Vertex vertex){
        return vertex.getNeighbours();
    }

    //checks if an edge exists
    public boolean edgeExists(Vertex vertexA, Vertex vertexB){
        if(vertexA.getNeighbours().contains(vertexB)){
            return true;
        }
        return false;
    }

    //returns the weight of a given edge
    public Double weight(Vertex vertexA, Vertex vertexB){
        for(Edge edge: edges){
            if(edge.vertexA==vertexA&&edge.vertexB==vertexB || edge.vertexB==vertexA&&edge.vertexA==vertexB){
                return edge.weight;
            }
        }
        return null;
    }

    public void removeEdge(Vertex vertexA, Vertex vertexB){
        vertexA.removeNeighbour(vertexB);
        Edge toRemove = null;
        for(Edge edge: edges){
            if(edge.vertexA==vertexA&&edge.vertexB==vertexB || edge.vertexB==vertexA&&edge.vertexA==vertexB){
                toRemove = edge;
            }
        }
        edges.remove(toRemove);
    }

    public Vertex getVertex(int A){
        return vertices.get(A);
    }

    public void deleteVertex(int A){
        Vertex v = vertices.get(A);
        v.clearNeighbours();
        vertices.remove(v);
    }

    //creates new edge and places it in list
    public void newEdge(int A, int B, Double weight){
        Vertex vertexA = vertices.get(A);
        Vertex vertexB = vertices.get(B);
        vertexA.addNeighbour(vertexB, weight);
    }

    //retrieves position of given vertex
    public Position position(int A){
        Vertex v = vertices.get(A);
        return v.getPosition();
    }

    //retrieves list verices connected to a given vertex
    public List<Vertex> neighbours(int A){
        Vertex v = vertices.get(A);
        return v.getNeighbours();
    }

    //checks if an edge exists
    public boolean edgeExists(int A, int B){
        Vertex vertexA = vertices.get(A);
        Vertex vertexB = vertices.get(B);
        if(vertexA.getNeighbours().contains(vertexB)){
            return true;
        }
        return false;
    }

    //returns the weight of a given edge
    public Double weight(int A, int B){
        Vertex vertexA = vertices.get(A);
        Vertex vertexB = vertices.get(B);
        for(Edge edge: edges){
            if(edge.vertexA==vertexA&&edge.vertexB==vertexB || edge.vertexB==vertexA&&edge.vertexA==vertexB){
                return edge.weight;
            }
        }
        return null;
    }
    
    public void dfs() {
    	for(int i=0;i<getVertices().size();i++) {
    		
    	}
    }
	
	public class Vertex {
		
		private T value;
		private Position position;
		private List<Vertex>  neighbours;
		private boolean visited;
		
		public Vertex(T value,Position position) {
			this.value = value;
			this.position = position;
			neighbours= new ArrayList<>();
			visited = false;
		}
		
	    public Vertex(T value, double x, double y){
            this.value = value;
            this.position = new Position(x,y);
            neighbours = new ArrayList<>();
        }
	    
	     public void addNeighbour(Vertex vertex, double weight){
	            neighbours.add(vertex);
	            vertex.addNeighbour(this,weight);
	            edges.add(new Edge(this,vertex,weight));
	        }
	     
	        public boolean removeNeighbour(Vertex vertex){
	            if(neighbours.contains(vertex)){
	                neighbours.remove(vertex);
	                Edge toRemove = new Edge(this,this,0);
	                for(Edge e: edges){
	                    if(this.equals(e.vertexA)&&vertex.equals(e.vertexB)||(this.equals(e.vertexB)&&vertex.equals(e.vertexA))){
	                        toRemove = e;
	                        break;
	                    }
	                }
	                edges.remove(toRemove);
	                return true;
	            }
	            return false;
	        }

	        public void clearNeighbours(){
	            for(Vertex v: neighbours){
	                v.removeNeighbour(this);
	            }
	            neighbours.clear();
	        }

		public Position getPosition() {
			return position;
		}

		public void setPosition(Position position) {
			this.position = position;
		}

		public List<Vertex> getNeighbours() {
			return neighbours;
		}

		public void setNeighbours(List<Vertex> neighbours) {
			this.neighbours = neighbours;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public T getValue() {
			return value;
		}
		
		public boolean getVisited() {
			return visited;
		}
		
		public void setVisited(boolean f) {
			visited=f;
		}
	}
	
	  public class Position{
	        public double x;
	        public double y;

	        public Position(double x, double y){
	            this.x = x;
	            this.y = y;
	        }
	    }
	  
	public class Edge {
		
        private Vertex vertexA;
        private Vertex vertexB;
        private Double weight;

        public Edge(Vertex vertexA, Vertex vertexB, double weight){
            this.vertexA = vertexA;
            this.vertexB = vertexB;
            this.weight = weight;
        }
        
        
        public Edge(Vertex vertexA, Vertex vertexB){
            this.vertexA = vertexA;
            this.vertexB = vertexB;
            weight = 0.0;
        }
        
		public Vertex getVertexA() {
			return vertexA;
		}

		public Vertex getVertexB() {
			return vertexB;
		}

		public Double getWeight() {
			return weight;
		}

		public void setWeight(Double weight) {
			this.weight = weight;
		}

        
		
	}
	
}
