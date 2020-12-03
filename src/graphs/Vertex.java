package graphs;

import model.Person;

public class Vertex<T> implements Comparable<Vertex<T>>{

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
	
	private Person girl=null;
	
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
