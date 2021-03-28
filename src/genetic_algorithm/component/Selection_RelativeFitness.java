package genetic_algorithm.component;

import genetic_algorithm.*;

import java.util.*;

/**
 * @author Wolfgang Bongartz
 *
 */
public class Selection_RelativeFitness implements Selection_Algorithm {
	
	private Random _randomNumberSource;
	
	public Selection_RelativeFitness(Random randomNumberSource) {
		_randomNumberSource = randomNumberSource;
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.component.Selection_Algorithm#apply(genetic_algorithm.Generation)
	 */
	@Override
	public ArrayList<Pair> apply(Generation generation, int numberOfPairs) {
		float worst_rating, best_rating, range;
		ArrayList<Individual> parents = new ArrayList<Individual>();

		// Compute fitness for all individuals.
		worst_rating = generation.get_worstInClass().get_rating();
		best_rating  = generation.get_bestInClass().get_rating();
		range        = worst_rating - best_rating;
		for(Individual individual: generation) individual.set_fitness( ( worst_rating - individual.get_rating() ) / range );

		// Go through the whole generation to select individuals for reproduction.
		Iterator<Individual> iter = generation.iterator();
		while( (parents.size() < 2*numberOfPairs) && iter.hasNext() ) {
			doSelection(iter.next(), parents);
		}
		
		// If necessary: Fill up the pool with randomly chosen individuals.
		while(parents.size()<2*numberOfPairs) {
			int r = _randomNumberSource.nextInt(generation.size());
			doSelection(generation.getIndividual(r), parents);
		}

		assert(parents.size()==2*numberOfPairs);

		// Build a list of pairs. Best with second best and so on.
		Individual left=null, right=null;
		ArrayList<Pair> retVal = new ArrayList<Pair>(); 
//		Collections.sort(parents, new IndividualComparator());
		boolean leftIsSet=false;
		for(int c=parents.size()-1; c>=0; c--) {
			if( ! leftIsSet ) {
				left = parents.get(c);
				leftIsSet=true;
			} else {
				right = parents.get(c);
				Pair p = new Pair(left, right);
				retVal.add(p);
				leftIsSet=false;
			}
		}
		
		assert(retVal.size()==numberOfPairs);

		return retVal;		
	}
	
	/**
	 * Decides whether the individual should be pooled for reproduction or not.
	 * @param i
	 * @param pool
	 */
	private void doSelection(Individual i, ArrayList<Individual> pool) {
		int r = _randomNumberSource.nextInt(Integer.MAX_VALUE);	// Create a random number.
		float threshold_value = r / (float) Integer.MAX_VALUE;	// Compute a (random) threshold value between 0 and 1.
		if(i.get_fitness()>threshold_value) {
			pool.add(i);		// If the individuals fitness is greater then the threshold value: Pool it for reproduction choice. Double entries will be ignored!
		}
	}

}
