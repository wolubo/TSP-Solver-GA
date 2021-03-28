/**
 * 
 */
package genetic_algorithm.component;

import genetic_algorithm.Individual;

import java.util.ArrayList;
import java.util.Random;

/**
 * Exchanges two randomly chosen genes.
 * @author Wolfgang Bongartz
 *
 */
public class Mutation_RandomExchange implements Mutation_Algorithm {

	private Random _randomNumberSource;
	private int _mutationRate;

	/**
	 * 
	 * @param randomNumberSource
	 * @param mutationRate Mutation likelihood in percent (0-100).
	 */
	public Mutation_RandomExchange(Random randomNumberSource, int mutationRate) {
		assert(mutationRate>=0 && mutationRate<=100);
		assert(randomNumberSource!=null);
		_randomNumberSource = randomNumberSource;
		_mutationRate = mutationRate;
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.mutation.MutationAlgorithm#apply(java.util.ArrayList)
	 */
	@Override
	public ArrayList<Individual> apply(ArrayList<Individual> children) {
		ArrayList<Individual> retVal = new ArrayList<Individual>();

		int i, j;

		for(Individual child: children) {

			Individual newChild = new Individual(child);

			boolean mutate = (_mutationRate==100);
			if( ! mutate && _mutationRate!=0 ) {
				mutate = _mutationRate > _randomNumberSource.nextInt(100);
			}

			if(mutate) {
				i = _randomNumberSource.nextInt(newChild.size()); 
				j = _randomNumberSource.nextInt(newChild.size());
				if(i!=j) {
					String temp = newChild.getGene(i);
					newChild.setGene(i, newChild.getGene(j));
					newChild.setGene(j, temp);
				}
			}
			retVal.add(newChild);
		}

		// Check invariant.
		assert(retVal.size()==children.size());

		return retVal;
	}

}
