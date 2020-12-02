package model;

import graphs.AdjListGraph;

public class Map {
	
	private AdjListGraph<Integer> map1;
	
	public Map() {
		map1 = new AdjListGraph<Integer>(false,false);
	}

	public void addCountry(int v) {
		map1.addVertex(v);
	}
	
	public void addRoad(int u,int v) {
		if(!map1.isInGraph(u)) {
			addCountry(u);
		}
		if(!map1.isInGraph(v)) {
			addCountry(v);
		}
		
	}
	
	
}
