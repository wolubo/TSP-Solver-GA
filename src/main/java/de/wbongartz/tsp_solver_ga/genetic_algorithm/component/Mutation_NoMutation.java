/**
 * 
 */
package de.wbongartz.tsp_solver_ga.genetic_algorithm.component;

import de.wbongartz.tsp_solver_ga.genetic_algorithm.Individual;

import java.util.ArrayList;

/**
 * Does not apply a mutation-scheme.
 * @author Wolfgang Bongartz
 */
public class Mutation_NoMutation implements Mutation_Algorithm {

	/* (non-Javadoc)
	 * @see genetic_algorithm.mutation.MutationAlgorithm#apply(java.util.ArrayList)
	 */
	@Override
	public ArrayList<Individual> apply(ArrayList<Individual> children) {
		return children;
	}

}
