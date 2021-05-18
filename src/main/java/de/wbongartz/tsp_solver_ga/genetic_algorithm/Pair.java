/**
 * 
 */
package de.wbongartz.tsp_solver_ga.genetic_algorithm;

/**
 * @author Wolfgang Bongartz
 *
 */
public class Pair {
	
	private Individual _p1;
	private Individual _p2;
	
	public Pair(Individual male, Individual female) {
		set_p1(male);
		set_p2(female);
	}

	/**
	 * @return the _p1
	 */
	public Individual get_p1() {
		return _p1;
	}

	/**
	 * @param p1 the _p1 to set
	 */
	public void set_p1(Individual p1) {
		this._p1 = p1;
	}

	/**
	 * @return the _p2
	 */
	public Individual get_p2() {
		return _p2;
	}

	/**
	 * @param p2 the p2 to set
	 */
	public void set_p2(Individual p2) {
		this._p2 = p2;
	}

}
