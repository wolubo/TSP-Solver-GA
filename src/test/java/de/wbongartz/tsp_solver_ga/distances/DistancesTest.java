package de.wbongartz.tsp_solver_ga.distances;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Wolfgang Bongartz
 *
 */
public class DistancesTest {
	
	private Distances _distances=null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		_distances = new Distances("distances4unittests.csv");
	}

	/**
	 * Test method for {@link Distances#getDistance(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetDistance() {
		try {
			Integer i = _distances.getDistance("Koeln", "Flensburg");
			if(i!=570) fail();
		} catch (LocationUnknownException e) {
			fail();
		}

		try {
			Integer i = _distances.getDistance("Muenchen", "Nuernberg");
			if(i!=170) fail();
		} catch (LocationUnknownException e) {
			fail();
		}

		try {
			_distances.getDistance("Wuerzburg", "Nuernberg");
			fail();
		} catch (LocationUnknownException e) {
		}
	}

	/**
	 * Test method for {@link Distances#getLocations()}.
	 */
	@Test
	public void testGetLocations() {
		Set<String> locations = _distances.getLocations();
		if(locations.isEmpty()) fail();
		if(locations.size()!=30) fail();
		if(!locations.contains("Koeln")) fail();
	}

	/**
	 * Test the internal structure of the object: The distance between 'from' and 'to' must be the same as the distance between 'to' and 'from'.
	 */
	@Test
	public void testDistances() {
		Set<String> locations = _distances.getLocations();
		try {
			for(String from : locations) {
				for(String to : locations) {
					Integer d1 = _distances.getDistance(from, to);
					Integer d2 = _distances.getDistance(to,from);
					if( d1.compareTo(d2) != 0 ) fail("The distances between " + from + " and " + to + " differ! from-->to=" + d1 + ", to-->from=" + d2);
				}
			}
		} catch (LocationUnknownException e) {
			fail();
		}
	}
}
