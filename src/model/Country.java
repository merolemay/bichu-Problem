package model;

 class Country {



 }
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

  class Person {

	private int value;


	public Person (int value) {
		this.value=value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
class Main {

	private BufferedReader br;
	private BufferedWriter bw;
	private AdjListGraph<Integer> a;

	public Main() {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		a = new AdjListGraph<Integer>(true,true);
	}


	public static void main(String args[]) {
		Main m = new Main();
		try {
			m.exe();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**/
	}

	public void exe() throws NumberFormatException, IOException {
		int numCountries = Integer.parseInt(br.readLine());
		for(int i=0;i<numCountries;i++) {
			a.addVertex(i+1);

		}
		for(int i=0;i<numCountries-1;i++) {
			String[] line = br.readLine().split(" ");
			int v1 = Integer.parseInt(line[0]);
			int v2 = Integer.parseInt(line[1]);
			a.addEdge(v1, v2, 0);

		}
		int numGirls = Integer.parseInt(br.readLine());
		for(int i=0;i<numGirls;i++) {
			int country = Integer.parseInt(br.readLine());
			a.searchVertex(country).addPerson(country);
		}
		System.out.println(a.dfs());
		br.close();
	}

}

class AdjListGraph<T> {

	private boolean directed;
	private boolean weighted;
	private int numberOfVertices;
	private int numberOfEdges;
	ArrayList<Integer> dist ;

	private List<Vertex<T>> vertices;
	private HashMap<T, AdjVertex<T>> map;

	public AdjListGraph(boolean directed, boolean weighted) {
		this.directed = directed;
		this.weighted = weighted;
		numberOfVertices = 0;
		numberOfEdges = 0;
		vertices = new LinkedList<Vertex<T>>();
		map = new HashMap<>();
		dist = new ArrayList<>();
	}

	public List<Vertex<T>> getVertices() {
		return vertices;
	}

	public HashMap<T, AdjVertex<T>> getMap() {
		return map;
	}

	public void setMap(HashMap<T, AdjVertex<T>> map) {
		this.map = map;
	}

	public int getNumberOfVertices() {
		return numberOfVertices;
	}

	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	public boolean isDirected() {
		return directed;
	}

	public boolean isWeighted() {
		return weighted;
	}

	public void addVertex(T value) {
		if (!isInGraph(value)) {
			AdjVertex<T> vertex = new AdjVertex<T>(value);
			map.put(value, vertex);
			vertex.setIndex(numberOfVertices);
			vertices.add(vertex);
			numberOfVertices++;
		}
	}

	
	public void addEdge(T x, T y) {
		AdjVertex<T> from = searchVertex(x);
		AdjVertex<T> to = searchVertex(y);
		addEdge(from, to);
	}

	public void addEdge(AdjVertex<T> from, AdjVertex<T> to) {
		addEdge(from, to, 1D);
	}

	
	public void addEdge(T x, T y, double w) {
		
		if (weighted) {
			AdjVertex<T> from = searchVertex(x);
			AdjVertex<T> to = searchVertex(y);
			addEdge(from, to, w);
		}
	}

	public void addEdge(AdjVertex<T> from, AdjVertex<T> to, double w) {
		if (from != null && to != null) {
			Edge<T> edge = new Edge<T>(from, to, w);
			from.getAdjList().add(edge);
			if (!isDirected()) {
				edge = new Edge<T>(to, from, w);
				to.getAdjList().add(edge);
			}
			numberOfEdges++;
		}
	}

	public void removeVertex(Vertex<T> v) {
		for (int i = 0; i < vertices.size(); i++) {
			removeEdge(vertices.get(i), v);
			if (isDirected()) {
				removeEdge(v, vertices.get(i));
			}
		}
		vertices.remove(v);
		map.remove(v.getValue());
		numberOfVertices--;
	}

	public void removeVertex(T v) {
		if (isInGraph(v)) {
			removeVertex(searchVertex(v));
		}
	}

	public void removeEdge(Vertex<T> x, Vertex<T> y) {
		AdjVertex<T> from = (AdjVertex<T>) x;
		AdjVertex<T> to = (AdjVertex<T>) y;
		List<Edge<T>> adjFrom = from.getAdjList();
		Edge<T> e = from.findEdge(to);
		if (e != null)
			adjFrom.remove(e);

		if (!isDirected()) {
			List<Edge<T>> adjTo = to.getAdjList();
			e = to.findEdge(from);
			if (e != null)
				adjTo.remove(e);
		}

		numberOfEdges--;
	}

	public void removeEdge(T x, T y) {
		if (isInGraph(x) && isInGraph(y)) {
			removeEdge(searchVertex(x), searchVertex(y));
		}
	}

	public List<Vertex<T>> getNeighbors(Vertex<T> x) {
		List<Vertex<T>> neigh = new ArrayList<>();
		AdjVertex<T> from = (AdjVertex<T>) x;
		List<Edge<T>> adj = from.getAdjList();
		for (int i = 0; i < adj.size(); i++) {
			neigh.add(adj.get(i).getDestination());
		}
		return neigh;
	}

	public boolean areAdjacent(Vertex<T> x, Vertex<T> y) {
		return getNeighbors(x).contains(y);
	}

	public boolean isInGraph(T value) {
		return searchVertex(value) != null;
	}

	public double getEdgeWeight(Vertex<T> x, Vertex<T> y) {
		double w = 0;
		if (isInGraph(x.getValue()) && isInGraph(y.getValue())) {
			AdjVertex<T> from = (AdjVertex<T>) x;
			AdjVertex<T> to = (AdjVertex<T>) y;
			Edge<T> e = from.findEdge(to);
			if (e != null)
				w = e.getWeight();
		}
		return w;
	}

	public void setEdgeWeight(Vertex<T> x, Vertex<T> y, double w) {
		if (isInGraph(x.getValue()) && isInGraph(y.getValue()) && weighted) {
			AdjVertex<T> from = (AdjVertex<T>) x;
			AdjVertex<T> to = (AdjVertex<T>) y;
			Edge<T> e = from.findEdge(to);
			if (e != null)
				e.setWeight(w);

			if (!isDirected()) {
				e = to.findEdge(from);
				if (e != null)
					e.setWeight(w);
			}
		}
	}

	public AdjVertex<T> searchVertex(T value) {
		return map.get(value);
	}

	public int getIndexOf(Vertex<T> v) {
		int index = -1;
		boolean searching = true;
		for (int i = 0; i < vertices.size() && searching; i++) {
			if (vertices.get(i) == v) {
				index = i;
				searching = false;
			}
		}
		return index;
	}




	public void bfs(Vertex<T> x) {
		AdjVertex<T> s = (AdjVertex<T>) x;
		for (Vertex<T> u : vertices) {
			u.setColor(Vertex.WHITE);
			u.setD(12345678);
			u.setPred(null);
		}
		s.setColor(Vertex.GRAY);
		s.setD(0);
		s.setPred(null);
		Queue<AdjVertex<T>> q = new LinkedList<>();
		q.offer(s);
		while (!q.isEmpty()) {
			AdjVertex<T> u = q.poll();
			for (int i = 0; i < u.getAdjList().size(); i++) {
				AdjVertex<T> v = (AdjVertex<T>) u.getAdjList().get(i).getDestination();
				if (v.getColor() == Vertex.WHITE) {
					v.setColor(Vertex.GRAY);
					v.setD(u.getD() + 1);
					v.setPred(u);
					q.offer(v);
				}
			}
			u.setColor(Vertex.BLACK);
		}
	}

	public int dfs() {

		for (Vertex<T> u : vertices) {
			u.setColor(Vertex.WHITE);
			u.setPred(null);
		}

		for (Vertex<T> u : vertices) {
			int time = 0;

			if (u.getColor() == Vertex.WHITE)
				time = dfsVisit((AdjVertex<T>) u, time);
		}
		return closestW();
	}


	private int dfsVisit(AdjVertex<T> u, int time) {
		time++;
		u.setD(time);
		u.setColor(Vertex.GRAY);
		for (int i = 0; i < u.getAdjList().size(); i++) {

			AdjVertex<T> v = (AdjVertex<T>) u.getAdjList().get(i).getDestination();
			if (v.getColor() == Vertex.WHITE) {
				v.setPred(u);
				time = dfsVisit(v, time);

			}
		}
		u.setColor(Vertex.BLACK);
		time=0;
		u.setF(time);
		return time;
	}


	private void initSingleSource(AdjVertex<T> s) {
		for (Vertex<T> u : vertices) {
			u.setD(12345678);
			u.setPred(null);
		}
		s.setD(0);
	}

	public void dijkstra(Vertex<T> x) {
		AdjVertex<T> s = (AdjVertex<T>) x;
		initSingleSource(s);
		PriorityQueue<AdjVertex<T>> queue = new PriorityQueue<>();
		queue.add(s);
		while (!queue.isEmpty()) {
			AdjVertex<T> u = queue.poll();
			for (Edge<T> e : u.getAdjList()) {
				AdjVertex<T> v = (AdjVertex<T>) e.getDestination();
				double weight = e.getWeight();
				double distanceFromU = u.getD() + weight;
				if (distanceFromU < v.getD()) {
					queue.remove(v);
					v.setD(distanceFromU);
					v.setPred(u);
					queue.add(v);
				}
			}
		}
	}

	public void dijkstraSourceDest(Vertex<T> x, Vertex<T> y) {
		AdjVertex<T> s = (AdjVertex<T>) x;
		initSingleSource(s);
		PriorityQueue<AdjVertex<T>> queue = new PriorityQueue<>();
		queue.add(s);
		AdjVertex<T> u = null;
		while (!queue.isEmpty()) {
			u = queue.poll();
			for (Edge<T> e : u.getAdjList()) {
				AdjVertex<T> v = (AdjVertex<T>) e.getDestination();
				double weight = e.getWeight();
				double distanceFromU = u.getD() + weight;
				if (distanceFromU < v.getD()) {
					queue.remove(v);
					v.setD(distanceFromU);
					v.setPred(u);
					queue.add(v);
				}
			}
			if(u.compareTo(y)==0) break;
		}
	}

	public double[][] floydwarshall() {
		double[][] weights = getWeightsMatrix();
		for (int k = 0; k < vertices.size(); k++) {
			for (int i = 0; i < vertices.size(); i++) {
				for (int j = 0; j < vertices.size(); j++) {
					weights[i][j] = Math.min(weights[i][j], weights[i][k] + weights[k][j]);
				}
			}
		}
		return weights;
	}

	private double[][] getWeightsMatrix() {
		double[][] weights = new double[vertices.size()][vertices.size()];
		for (int i = 0; i < weights.length; i++) {
			Arrays.fill(weights[i], 12345678);
		}
		for (int i = 0; i < vertices.size(); i++) {
			weights[i][i] = 0;
			AdjVertex<T> u = (AdjVertex<T>) vertices.get(i);
			for (Edge<T> e : u.getAdjList()) {
				AdjVertex<T> v = (AdjVertex<T>) e.getDestination();
				double weight = e.getWeight();
				weights[i][getIndexOf(v)] = weight;
			}
		}
		return weights;
	}

	public void prim(Vertex<T> s) {
		AdjVertex<T> r = (AdjVertex<T>) s;
		for (Vertex<T> u : vertices) {
			u.setD(12345678);
			u.setColor(Vertex.WHITE);
		}
		r.setD(0);
		r.setPred(null);
		PriorityQueue<AdjVertex<T>> queue = new PriorityQueue<>();
		for (Vertex<T> u : vertices) {
			queue.add((AdjVertex<T>) u);
		}
		while (!queue.isEmpty()) {
			AdjVertex<T> u = queue.poll();
			for (Edge<T> e : u.getAdjList()) {
				AdjVertex<T> v = (AdjVertex<T>) e.getDestination();
				if (v.getColor() == Vertex.WHITE && e.getWeight() < v.getD()) {
					e.setMarked(true);
					queue.remove(v);
					v.setD(e.getWeight());
					queue.add(v);
					v.setPred(u);
				}
			}
			u.setColor(Vertex.BLACK);
		}
	}

	/*public ArrayList<Edge<T>> kruskal() {	
		ArrayList<Edge<T>> result = new ArrayList<>();
		int e = 0;
		int i = 0;
		ArrayList<Edge<T>> edges = getEdges();
		Collections.sort(edges);
		UnionFind uf = new UnionFind(vertices.size());
		i = 0;
		while (e < vertices.size() - 1 && i < edges.size()) {
			Edge<T> edge = edges.get(i);
			i++;

			int x = uf.find(getIndexOf(edge.getSource()));
			int y = uf.find(getIndexOf(edge.getDestination()));

			if (x != y) {
				result.add(edge);
				e++;
				uf.union(x, y);
			}
		}
		return result;
	}
	*/

	public ArrayList<Edge<T>> getEdges() {
		ArrayList<Edge<T>> edges = new ArrayList<>();
		for (int i = 0; i < vertices.size(); i++) {
			AdjVertex<T> v = (AdjVertex<T>) vertices.get(i);
			for (int j = 0; j < v.getAdjList().size(); j++) {
				edges.add(v.getAdjList().get(j));
			}
		}
		return edges;
	}

	public List<T> getContents(){
		List<Vertex<T>> vertices= getVertices();
		List<T> contents=new ArrayList<>();
		for(int i=0;i<vertices.size();i++) {
			contents.add(vertices.get(i).getValue());
		}
		return contents;
	}

	public int closestW() {
		int min= 999999;
		for (Vertex<T> u : vertices) {
			if(u.hasAprincess()){
				min =(u.getPerson().getValue()<min)?u.getPerson().getValue():min;
			}
		}

		return  min;
	}
}


 class Vertex<T> implements Comparable<Vertex<T>>{

	public static final int WHITE = 0;
	public static final int GRAY = 1;
	public static final int BLACK = 2;


	private T value;

	/**
	 * Distance or Initial TimeSTamp for DFS
	 */
	private double d;
	/**
	 * Final TimeStamp for DFS
	 */
	private int f;

	private int index;

	private int color;

	private Vertex<T> pred;

	private int distance;

	private boolean visited;

	 Person girl=null;

	public Vertex(T value) {
		this.value=value;
		pred=null;
		color=WHITE;
		visited = false;
	}

	public boolean hasAprincess() {
		return girl!=null;
	}
	public boolean getVisited() {
		return visited;
	}

	public void setVisited(boolean t) {
		visited=t;
	}

	public void addPerson(int n) {
		girl = new Person(n);
	}

	public Person getPerson() {
		return girl;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Vertex<T> getPred() {
		return pred;
	}

	public void setPred(Vertex<T> pred) {
		this.pred = pred;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index=index;
	}

	@Override
	public int compareTo(Vertex<T> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}

 class AdjVertex<T> extends Vertex<T> {

	private List<Edge <T>> adjList;

	public AdjVertex(T value) {
		super(value);
		adjList= new LinkedList<Edge<T>>();
	}

	public List<Edge<T>> getAdjList(){
		return adjList;
	}

	public boolean isAdjacent(AdjVertex<T> vertex) {
		for(int i = 0; i<adjList.size(); i++) {
			if (adjList.get(i).getDestination()==vertex)
				return true;
		}
		return false;
	}

	public Edge<T> findEdge(AdjVertex<T> vertex){
		for (int i=0; i<adjList.size(); i++) {
			if (adjList.get(i).getDestination()==vertex)
				return adjList.get(i);
		}
		return null;
	}


}
 class Edge<T> implements Comparable<Edge<T>>{

	private double weight;

	private Vertex<T> source;
	private Vertex<T> destination;
	private boolean marked;

	public Edge(Vertex<T> source, Vertex<T> destination) {
		this(source, destination, 12341);
		this.marked = false;
	}

	public Edge(Vertex<T> source, Vertex<T> destination, double weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.marked = false;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Vertex<T> getSource() {
		return source;
	}

	public Vertex<T> getDestination() {
		return destination;
	}

	@Override
	public int compareTo(Edge<T> o) {
		return Double.compare(weight, o.weight);
	}

	@Override
	public String toString() {
		return ""+source+" - "+destination+", "+weight;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
		//System.out.println(marked);

	}
	
class UnionFind {

	private int[] parent;
	private int[] rank;


	public int find(int i) {

		int p = parent[i];
		if (i == p) {
			return i;
		}
		return parent[i] = find(p);

	}


	public void union(int i, int j) {

		int root1 = find(i);
		int root2 = find(j);

		if (root2 == root1) return;

		if (rank[root1] > rank[root2]) {
			parent[root2] = root1;
		} else if (rank[root2] > rank[root1]) {
			parent[root1] = root2;
		} else {
			parent[root2] = root1;
			rank[root1]++;
		}
	}


	public UnionFind(int max) {

		parent = new int[max];
		rank = new int[max];

		for (int i = 0; i < max; i++) {
			parent[i] = i;
		}
	}


	public String toString() {
		return "<UnionFind\np " + Arrays.toString(parent) + "\nr " + Arrays.toString(rank) + "\n>";
	}
}
 }
 
 
