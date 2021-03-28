package genetic_algorithm.component;

import genetic_algorithm.*;

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
