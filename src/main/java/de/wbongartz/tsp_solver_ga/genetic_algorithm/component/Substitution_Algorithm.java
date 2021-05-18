package de.wbongartz.tsp_solver_ga.genetic_algorithm.component;

import de.wbongartz.tsp_solver_ga.genetic_algorithm.*;

import java.util.ArrayList;

/**
 * Apply the substitution process to produce a new generation.
 * "Ersetzungsschema", "survival of the fittest"
 * @author Wolfgang Bongartz
 *
 */
public interface Substitution_Algorithm {

	Generation apply(ArrayList<Individual> children, ArrayList<Pair> parents, Generation currentGeneration);

	/**
	 * Computes how many children are needed to apply the substitution algorithm. 
	 * Depends on how many individuals from the current generation should become part of the next generation.
	 * @param generationSize
	 * @return
	 */
	int computeNumberOfChildren(int generationSize);
	
}
