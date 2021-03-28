/**
 * 
 */
package genetic_algorithm.component;

import genetic_algorithm.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * "partially matched crossover"
 * For details refer to: "Genetische Algorithmen und Evolutionsstrategien", Eberhard Schöneburg et al, Addison-Wesley, 1994, ISBN: 3-89319-493-2.
 * DOES NOT WORK PROPERLY! NEEDS FIXING!
 * @author Wolfgang Bongartz
 *
 */
public class Crossover_PMX implements Crossover_Algorithm {
	
	private Random _randomNumberSource;
	public Crossover_PMX(Random randomNumberSource) {
		_randomNumberSource = randomNumberSource;
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.component.Crossover_Algorithm#apply(java.util.ArrayList)
	 */
	@Override
	public ArrayList<Individual> apply(ArrayList<Pair> parents) {
		ArrayList<Individual> retVal = new ArrayList<Individual>();

		for(Pair pair: parents) {

			// Get parents.
			Individual a = pair.get_p1();
			Individual b = pair.get_p2();

			assert(a.size()==b.size());
			
			int size = a.size(); 
			
			// Prepare child chromosome.
			String [] chromo_a = new String [size];
			String [] chromo_b = new String [size];
			

			// Create a random interval [left, right].
			int r1 = _randomNumberSource.nextInt(size);
			int r2 = _randomNumberSource.nextInt(size);
			int left  = Math.min(r1, r2);
			int right = Math.max(r1, r2);

//			if( left==0 ) {			
//				System.out.println("ERROR");
//			}
//			if( left==size-1 ) {			
//				System.out.println("ERROR");
//			}
			
			// Exchange genes within interval.
			for(int i=0; i<size; i++) {
				if(i>=left && i<=right) {
					chromo_b[i] = a.getGene(i);
					chromo_a[i] = b.getGene(i);
				} else {
					chromo_a[i] = a.getGene(i);
					chromo_b[i] = b.getGene(i);
				}
			}

			// Make child-genes unique.
			for(int i=left; i<=right; i++) {
				
				// Look for duplicates.
				for(int j=0; j<size; j++) {
					
					if( j<left || j>right ) { // Leave out the intervall.

						// First: Look for child a1.
						String duplicate_a = chromo_a[j];
						if( duplicate_a.compareTo(chromo_a[i])==0 ) {
							int p = b.find(duplicate_a); 
							String w = a.getGene(p);
							chromo_a[j]=w;
						}

						// Now, do the same for child b1.
						String duplicate_b = chromo_b[j];
						if( duplicate_b.compareTo(chromo_b[i])==0 ) {
							int p = a.find(duplicate_b); 
							String w = b.getGene(p);
							chromo_b[j]=w;
						}

					}
				}
			}
			
//			if( ! check(chromo_a) ) {			
//				System.out.println("ERROR");
//			}
//			if( ! check(chromo_b) ) {			
//				System.out.println("ERROR");
//			}

			Individual child_a = new Individual(chromo_a);
			retVal.add(child_a);
			
			Individual child_b = new Individual(chromo_b);
			retVal.add(child_b);
		}
		
		return retVal;
	}


//private boolean check(String [] c) {
//	for(int i=0; i<c.length-1; i++) {
//		for(int j=i; j<c.length; j++) {
//			if(c[i].compareTo(c[j])==0) return false;
//		}
//	}
//	return true;
//}

	/* (non-Javadoc)
	 * @see genetic_algorithm.component.Crossover_Algorithm#computeNumberOfChildrenPerPair()
	 */
	@Override
	public int computeNumberOfChildrenPerPair() {
		return 2;
	}

}