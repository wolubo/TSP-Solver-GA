/**
 * 
 */
package genetic_algorithm.component;

import genetic_algorithm.Individual;
import genetic_algorithm.Pair;

import java.util.ArrayList;

/**
 * @author Wolfgang Bongartz
 *
 */
public class Crossover_NoCrossover implements Crossover_Algorithm {

	public ArrayList<Individual> apply(ArrayList<Pair> parents) {
		ArrayList<Individual> retVal = new ArrayList<Individual>();
		for(Pair p: parents) {
			retVal.add(p.get_p1());
			retVal.add(p.get_p2());
		}
		return retVal;
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.component.Crossover_Algorithm#computeNumberOfChildrenPerPair()
	 */
	@Override
	public int computeNumberOfChildrenPerPair() {
		return 2;
	}

}
