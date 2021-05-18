package de.wbongartz.tsp_solver_ga.genetic_algorithm.component;

import de.wbongartz.tsp_solver_ga.genetic_algorithm.*;

/**
 * @author Wolfgang Bongartz
 *
 */
public interface Evaluation_Algorithm {

	/**
	 * Apply the algorithm upon the individuals within a generation. 
	 * Compute an evaluation-value for each individual.
	 * @param generation
	 * @throws Exception 
	 */
	void apply(Generation generation) throws Exception;
	
}
