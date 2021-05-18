/**
 * 
 */
package de.wbongartz.tsp_solver_ga.genetic_algorithm;

import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Wolfgang Bongartz
 *
 */
public class IndividualComparator implements Comparator<Individual> {
	
    @Override
    public int compare(Individual a, Individual b) {
    	if(a==b) return 0;
    	
    	if( a.get_fitness() < b.get_fitness() ) {
    		return -1;
    	} else if( a.get_fitness() > b.get_fitness() ) {
    		return 1;
    	}
    	
    	Iterator<String> iter_a, iter_b;
    	iter_a = a.iterator();
    	iter_b = b.iterator();
    	
    	while( iter_a.hasNext() && iter_b.hasNext() ) {
    		int c = iter_a.next().compareTo(iter_b.next());
    		if(c!=0) return c;
    	}
    	
    	if( iter_a.hasNext() ) {
    		return 1;
    	} else if( iter_b.hasNext() ) {
    		return -1;
    	}
    	
    	return 0;
    }
}
