package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import graphs.AdjListGraph;

public class BishuAdjListGraph {
	
	private BufferedReader br;
	private BufferedWriter bw;
	private AdjListGraph<Integer> a;
	
	public BishuAdjListGraph() {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		a = new AdjListGraph<Integer>(true,true);
	}
	
	
	public static void main(String args[]) {
		BishuAdjListGraph m = new BishuAdjListGraph();
		try {
			m.exe();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
