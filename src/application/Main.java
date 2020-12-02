package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import graphs.AdjListGraph;

public class Main {
	
	private BufferedReader br;
	private BufferedWriter bw;
	private AdjListGraph<Integer> a;
	
	public Main() {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		a = new AdjListGraph<Integer>(false,false);
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
		
		
	}
	
	public void exe() throws NumberFormatException, IOException {
		int numCountries = Integer.parseInt(br.readLine());
		for(int i=0;i<numCountries;i++) {
			a.addVertex(i);
		}
		for(int i=0;i<numCountries;i++) {
			String[] line = br.readLine().split(" ");
			int v1 = Integer.parseInt(line[0]);
			int v2 = Integer.parseInt(line[1]);
			a.addEdge(v1, v2, 0);
		}
		int numGirls = Integer.parseInt(br.readLine());
		for(int i=0;i<numGirls;i++) {
			int country = Integer.parseInt(br.readLine());
			a.searchVertex(country).addPerson();
		}
		br.close();
	}

}
