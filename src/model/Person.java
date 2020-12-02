package model;

public class Person {

	public Country c;
	
	public Person (Country country) {
		c=country;
	}

	/**
	 * @return the c
	 */
	public Country getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(Country c) {
		this.c = c;
	}
	
	
}
