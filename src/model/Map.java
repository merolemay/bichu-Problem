package model;

import graphs.Graph;

public class Map {
	
	private Graph<Integer> map;
	
	public Map() {
		map = new Graph<Integer>();
	}

	public void addCountry(int value,double x,double y) {
		map.newVertex(value, x, y);
	}
	
	public void addRoad(int a,int b) {
		map.newEdge(a, b, 1.0);
	}
	
	
}
