/**
 * 
 */
package de.wbongartz.tsp_solver_ga.genetic_algorithm.component;

import de.wbongartz.tsp_solver_ga.distances.Distances;
import de.wbongartz.tsp_solver_ga.genetic_algorithm.Generation;
import de.wbongartz.tsp_solver_ga.genetic_algorithm.Individual;

import java.util.Iterator;

/**
 * @author Wolfgang Bongartz
 *
 */
public class Evaluation_RoundTrip implements Evaluation_Algorithm {
	
	private Distances _distances;
	
	public Evaluation_RoundTrip(Distances distances) {
		_distances = distances;
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.component.Evaluation_Algorithm#evaluateGeneration(genetic_algorithm.Generation)
	 */
	@Override
	public void apply(Generation generation) throws Exception {
		float worst_rating=0f;
		float best_rating=-1f;
		String current_location, first_location, last_location;
		Individual bestInClass=null;
		Individual worstInClass=null;
		
		// Compute ratings for all individuals.
		for(Individual individual: generation) {

			// Compute round-trip-distance for each individual.
			float roundtrip_distance = 0f;
			Iterator<String> location_iter = individual.iterator();
			current_location=null;
			first_location = location_iter.next();
			last_location = first_location;
			
			while(location_iter.hasNext()) {
				current_location=location_iter.next();
				roundtrip_distance += _distances.getDistance(last_location, current_location);
				last_location = current_location;
			}
			
			roundtrip_distance += _distances.getDistance(last_location, first_location); // Last trip back to the start.
			individual.set_rating(roundtrip_distance);
			
			if( worst_rating<roundtrip_distance ) {
				worst_rating=roundtrip_distance;
				worstInClass = individual;
			}
			
			if( best_rating<0 || best_rating>roundtrip_distance ) { 
				best_rating=roundtrip_distance;
				bestInClass = individual;
			}
		}
		
		generation.set_bestInClass(bestInClass);
		generation.set_worstInClass(worstInClass);

	}

}
