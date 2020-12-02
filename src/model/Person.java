package model;

import graphs.Vertex;

public class Person {

	private Vertex<Integer> c;

	
	public Person (Vertex<Integer> country) {
		c=country;
	}

	/**
	 * @return the c
	 */
	public Vertex<Integer> getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(Vertex<Integer> c) {
		this.c = c;
	}
	
	
}
