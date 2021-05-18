/**
 * 
 */
package de.wbongartz.tsp_solver_ga.genetic_algorithm.component;

import java.util.*;

import de.wbongartz.tsp_solver_ga.genetic_algorithm.*;


/**
 * "Ersetzungsschema Elitismus"
 * Keep a certain number of top individuals from the current generation.
 * @author Wolfgang Bongartz
 *
 */
public class Substitution_Elitism implements Substitution_Algorithm {
	
	private int _keep;
	
	/**
	 * 
	 * @param keep Amount of top individuals to keep (percentage value: 0-100).
	 */
	public Substitution_Elitism(int keep) {
		if(keep<0||keep>100) throw new IllegalArgumentException("Configuration error: Amount of individuals to keep exceeds limits!");
		_keep = keep;
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.substitution.SubstitutionAlgorithm#apply(java.util.ArrayList, java.util.ArrayList, genetic_algorithm.Generation)
	 */
	@Override
	public Generation apply(ArrayList<Individual> children, ArrayList<Pair> parents, Generation currentGeneration) {
		
		int generationSize = currentGeneration.size();
		int numberOfIndividualsToKeep = generationSize*_keep/100;
		if(numberOfIndividualsToKeep+children.size()>generationSize) throw new IllegalArgumentException();
		
		Generation newGeneration = new Generation(children);
		
		// Create a set sorted in ascending order by the individuals rating.
		//ArrayList<Individual> pool = new ArrayList<Individual>();
		Individual top;
		//for(Individual i: currentGeneration) pool.add(i);
		currentGeneration.sort();
//		Collections.sort(currentGeneration, new IndividualComparator());
		for(int c=currentGeneration.size()-1; c>=currentGeneration.size()-numberOfIndividualsToKeep; c--) {
			top = currentGeneration.getIndividual(c);
			newGeneration.addIndividual(top);
		}

		assert(newGeneration.size()==currentGeneration.size());
		
		return newGeneration;
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.component.Substitution_Algorithm#computeNumberOfChildren(int)
	 */
	@Override
	public int computeNumberOfChildren(int generationSize) {
		return generationSize - (generationSize*_keep/100);
	}

}
