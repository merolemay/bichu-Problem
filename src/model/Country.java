package model;

public class Country {
	
	private int number;
	private Person p;

	public Country(int n,Person person) {
		number=n;
		p=person;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the p
	 */
	public Person getP() {
		return p;
	}

	/**
	 * @param p the p to set
	 */
	public void setP(Person p) {
		this.p = p;
	}
	
	
}
