/**
 * 
 */
package tsp_solver;

import genetic_algorithm.Individual;
import genetic_algorithm.interfaces.Result;

/**
 * @author Wolfgang Bongartz
 *
 */
public class TpsIndividual extends Individual<String> implements Result {

	/**
	 * 
	 * @param chromosome
	 */
	public TpsIndividual(String[] chromosome) {
		super(chromosome);
	}

	/**
	 * @param other
	 */
	public TpsIndividual(TpsIndividual other) {
		super(other);
	}

}
