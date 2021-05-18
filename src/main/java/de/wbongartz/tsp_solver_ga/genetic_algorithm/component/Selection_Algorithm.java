/**
 * 
 */
package de.wbongartz.tsp_solver_ga.genetic_algorithm.component;

import de.wbongartz.tsp_solver_ga.genetic_algorithm.Generation;
import de.wbongartz.tsp_solver_ga.genetic_algorithm.Pair;

import java.util.ArrayList;

/**
 * @author Wolfgang Bongartz
 *
 */
public interface Selection_Algorithm {

	/**
	 * 
	 * "Heiratsschema"
	 * @param generation
	 * @param numberOfPairs Number of pairs to be produced by the algorithm.
	 * @return
	 */
	ArrayList<Pair> apply(Generation generation, int numberOfPairs);

}
