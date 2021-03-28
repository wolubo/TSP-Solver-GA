/**
 * 
 */
package genetic_algorithm.component;

import genetic_algorithm.Individual;
import genetic_algorithm.Pair;

import java.util.ArrayList;

/**
 * "Rekombinations-Schema"
 * @author Wolfgang Bongartz
 */
public interface Crossover_Algorithm {

	/**
	 * Apply the crossover-algorithm upon the parents to create a set of children.
	 * @param parents
	 * @return
	 */
	ArrayList<Individual> apply(ArrayList<Pair> parents);
	
	int computeNumberOfChildrenPerPair();

}
