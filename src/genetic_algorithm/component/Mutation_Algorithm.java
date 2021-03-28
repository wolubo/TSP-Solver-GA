/**
 * 
 */
package genetic_algorithm.component;

import genetic_algorithm.Individual;

import java.util.ArrayList;

/**
 * @author Wolfgang Bongartz
 *
 */
public interface Mutation_Algorithm {

	/**
	 * Apply mutation-algorithm upon children to create a set of mutated children.
	 * @param children
	 * @return
	 */
	ArrayList<Individual> apply(ArrayList<Individual> children);

}
