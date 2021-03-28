/**
 * 
 */
package distances.unittests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import distances.Distances;
import distances.LocationUnknownException;

/**
 * @author Wolfgang Bongartz
 *
 */
public class DistancesTest {
	
	private Distances _distances=null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		_distances = new Distances("src/distances/unittests/distances4unittests.csv");
	}

	/**
	 * Test method for {@link distances.Distances#getDistance(java.lang.String, java.lang.String)}.
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
	 * Test method for {@link distances.Distances#getLocations()}.
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
