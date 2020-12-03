package graphs;

import java.util.*;

public class AdjMatrixGraph<T> implements IGraph<T> {

	private boolean directed;
	private boolean weighted;
	private int numberOfVertices;
	private int numberOfEdges;

	private List<Vertex<T>> vertices;
	private List<ArrayList<Integer>> adjMatrix;
	private List<ArrayList<Double>> weightsMatrix;
	private HashMap<T, Vertex<T>> map;
	private boolean[][] marked;

	public AdjMatrixGraph(boolean directed, boolean weighted) {
		this.directed = directed;
		this.weighted = weighted;
		numberOfVertices = 0;
		numberOfEdges = getNumberOfEdges();
		vertices = new LinkedList<Vertex<T>>();
		adjMatrix = new ArrayList<ArrayList<Integer>>();
		weightsMatrix = new ArrayList<ArrayList<Double>>();
		map = new HashMap<>();
	}

	public List<Vertex<T>> getVertices() {
		return vertices;
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

	@Override
	public void addVertex(T value) {
		if (!isInGraph(value)) {
			Vertex<T> vertex = new Vertex<T>(value);
			map.put(value, vertex);
			vertices.add(vertex);

			ArrayList<Integer> adj = new ArrayList<>();
			ArrayList<Double> we = new ArrayList<>();
			for (int i = 0; i < adjMatrix.size(); i++) {
				adjMatrix.get(i).add(0);
				weightsMatrix.get(i).add((double) INF);
			}
			for (int i = 0; i < vertices.size(); i++) {
				adj.add(0);
				we.add((double) INF);
			}
			we.set(we.size() - 1, 0.0);
			adjMatrix.add(adj);
			weightsMatrix.add(we);

			numberOfVertices++;
		}
	}

	@Override
	public void addEdge(T x, T y) {
		Vertex<T> from = searchVertex(x);
		Vertex<T> to = searchVertex(y);
		addEdge(from, to);
	}

	public void addEdge(Vertex<T> from, Vertex<T> to) {
		addEdge(from, to, 1D);
	}

	@Override
	public void addEdge(T x, T y, double w) {
		if (weighted) {
			Vertex<T> from = searchVertex(x);
			Vertex<T> to = searchVertex(y);
			addEdge(from, to, w);
		}
	}

	private void addEdge(Vertex<T> from, Vertex<T> to, double w) {
		if (from != null && to != null) {

			adjMatrix.get(getIndexOf(from)).set(getIndexOf(to), 1);
			weightsMatrix.get(getIndexOf(from)).set(getIndexOf(to), Math.min(w, weightsMatrix.get(getIndexOf(from)).get(getIndexOf(to))));
			if (!isDirected()) {
				adjMatrix.get(getIndexOf(to)).set(getIndexOf(from), 1);
				weightsMatrix.get(getIndexOf(to)).set(getIndexOf(from), Math.min(w, weightsMatrix.get(getIndexOf(to)).get(getIndexOf(from))));
			}
			numberOfEdges++;
		}

	}

	public void removeVertex(Vertex<T> v) {
		int index = getIndexOf(v);
		for (int i = 0; i < adjMatrix.size(); i++) {
			adjMatrix.get(i).remove(index);
			weightsMatrix.get(i).remove(index);
		}
		adjMatrix.remove(index);
		weightsMatrix.remove(index);

		vertices.remove(v);
		map.remove(v.getValue());
		numberOfVertices--;
	}

	public void removeVertex(T v) {
		if (isInGraph(v)) {
			removeVertex(searchVertex(v));
		}
	}

	public void removeEdge(Vertex<T> from, Vertex<T> to) {
		adjMatrix.get(getIndexOf(from)).set(getIndexOf(to), 0);
		weightsMatrix.get(getIndexOf(from)).set(getIndexOf(to), (double) INF);
		if (!isDirected()) {
			adjMatrix.get(getIndexOf(to)).set(getIndexOf(from), 0);
			weightsMatrix.get(getIndexOf(to)).set(getIndexOf(from), (double) INF);
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
		int index = getIndexOf(x);
		for (int i = 0; i < adjMatrix.get(index).size(); i++) {
			if (adjMatrix.get(index).get(i) == 1.0) {
				neigh.add(vertices.get(i));
			}
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
		int indX = getIndexOf(x);
		int indY = getIndexOf(y);
		if (isInGraph(x.getValue()) && isInGraph(y.getValue())) {
			w = weightsMatrix.get(indX).get(indY);
		}
		return w;
	}

	public void setEdgeWeight(Vertex<T> x, Vertex<T> y, double w) {
		int indX = getIndexOf(x);
		int indY = getIndexOf(y);
		if (isInGraph(x.getValue()) && isInGraph(y.getValue()) && weighted) {
			weightsMatrix.get(indX).set(indY, w);

			if (!isDirected()) {
				weightsMatrix.get(indY).set(indX, w);
			}
		}
	}

	public Vertex<T> searchVertex(T value) {
		return map.get(value);
	}

	public int getIndexOf(Vertex<T> v) {
		int index = -1;
		boolean searching = true;
		for (int i = 0; i < vertices.size() && searching; i++) {
			if (vertices.get(i).equals(v)) {
				index = i;
				searching = false;
			}
		}
		return index;
	}

	public void bfs(Vertex<T> s) {
		for (Vertex<T> u : vertices) {
			u.setColor(Vertex.WHITE);
			u.setD(INF);
			u.setPred(null);
		}
		s.setColor(Vertex.GRAY);
		s.setD(0);
		s.setPred(null);
		Queue<Vertex<T>> q = new LinkedList<>();
		q.offer(s);
		while (!q.isEmpty()) {
			Vertex<T> u = q.poll();
			List<Vertex<T>> neigh = getNeighbors(u);
			for (int i = 0; i < neigh.size(); i++) {
				Vertex<T> v = neigh.get(i);
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
		int time = 0;
		for (Vertex<T> u : vertices) {
			if (u.getColor() == Vertex.WHITE)
				time = dfsVisit(u, time);
		}
		return closestW();
	}

	private int dfsVisit(Vertex<T> u, int time) {
		time++;
		u.setD(time);
		u.setColor(Vertex.GRAY);
		List<Vertex<T>> neigh = getNeighbors(u);
		for (int i = 0; i < neigh.size(); i++) {
			Vertex<T> v = neigh.get(i);
			if (v.getColor() == Vertex.WHITE) {
				v.setPred(u);
				time = dfsVisit(v, time);
			}
		}
		u.setColor(Vertex.BLACK);
		time++;
		u.setF(time);
		return time;
	}

	private void initSingleSource(Vertex<T> s) {
		for (Vertex<T> u : vertices) {
			u.setD(INF);
			u.setPred(null);
		}
		s.setD(0);
	}

	public void dijkstra(Vertex<T> s) {
		initSingleSource(s);
		PriorityQueue<Vertex<T>> queue = new PriorityQueue<>();
		queue.add(s);
		while (!queue.isEmpty()) {
			Vertex<T> u = queue.poll();
			List<Vertex<T>> neigh = getNeighbors(u);
			for (Vertex<T> v : neigh) {
				double weight = getEdgeWeight(u, v);
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

	public double[][] getWeightsMatrix() {
		double[][] weights = new double[vertices.size()][vertices.size()];
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights.length; j++) {
				weights[i][j] = weightsMatrix.get(i).get(j);
			}
		}
		return weights;
	}

	public void prim(Vertex<T> r) {
		for (int i = 0; i < marked.length; i++) {
			Arrays.fill(marked, false);
		}
		for (Vertex<T> u : vertices) {
			u.setD(INF);
			u.setColor(Vertex.WHITE);
		}
		r.setD(0);
		r.setPred(null);
		PriorityQueue<Vertex<T>> queue = new PriorityQueue<>();
		for (Vertex<T> u : vertices) {
			queue.add(u);
		}
		while (!queue.isEmpty()) {
			Vertex<T> u = queue.poll();
			List<Vertex<T>> neigh = getNeighbors(u);
			for (Vertex<T> v : neigh) {
				if (v.getColor() == Vertex.WHITE && getEdgeWeight(u, v) < v.getD()) {
					marked[getIndexOf(u)][getIndexOf(v)] = true;
					queue.remove(v);
					v.setD(getEdgeWeight(u, v));
					queue.add(v);
					v.setPred(u);
				}
			}
			u.setColor(Vertex.BLACK);
		}
	}
	
	public ArrayList<Edge<T>> kruskal() {
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

	public ArrayList<Edge<T>> getEdges() {
		ArrayList<Edge<T>> edges = new ArrayList<>();
		for (int i = 0; i < adjMatrix.size(); i++) {
			for (int j = 0; j < adjMatrix.get(i).size(); j++) {
				if(adjMatrix.get(i).get(j) == 1) {
					edges.add(new Edge<>(vertices.get(i), vertices.get(j), weightsMatrix.get(i).get(j)));
				}
			}
		}
		return edges;
	}

	public List<ArrayList<Integer>> getAdjMatrix() {
		return adjMatrix;
	}
	
	public List<T> getContents(){
		List<Vertex<T>> vertices= getVertices();
		List<T> contents=new ArrayList<>();
		for(int i=0;i<vertices.size();i++) {
			contents.add(vertices.get(i).getValue());
		}
		return contents;
	}

	public boolean[][] getMarked() {
		return marked;
	}

	public void setMarked(boolean[][] marked) {
		this.marked = marked;
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
