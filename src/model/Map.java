package model;

import graphs.AdjListGraph;
import graphs.Vertex;

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
		map1.addEdge(map1.searchVertex(u), map1.searchVertex(v));
	}
	
	public boolean addPerson(int v) {
		boolean f = false;
		if(map1.searchVertex(v)!=null) {
		Vertex<Integer> c = map1.searchVertex(v);
		c.addPerson();
		f=true;
		}
		return f;
		
	}
	
	public int[] getTheArrayWithDistance(int N) {
		int[] dist = new int[N+1];
        boolean[] V=new boolean[N+1];
        dist[0]=0;
        dist[1]=0;
        map1.dfs(1,V,0,dist);
        return dist;
	}
	
	
}
