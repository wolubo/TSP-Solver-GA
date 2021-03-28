/**
 * 
 */
package genetic_algorithm.component;

import genetic_algorithm.*;

import java.util.ArrayList;

/**
 * @author Wolfgang Bongartz
 *
 */
public class Substitution_GenerationalReplacement implements Substitution_Algorithm {

	public Generation apply(ArrayList<Individual> children, ArrayList<Pair> parents, Generation currentGeneration) {
		if(children.size()!=currentGeneration.size()) throw new IllegalArgumentException();

		Generation retVal = new Generation(children);
//		for(Individual i: children) retVal.addIndividual(i);

		return retVal;
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.component.Substitution_Algorithm#computeNumberOfChildren(int)
	 */
	@Override
	public int computeNumberOfChildren(int generationSize) {
		return generationSize;
	}
	
}
