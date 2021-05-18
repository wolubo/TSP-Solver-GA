package de.wbongartz.tsp_solver_ga.genetic_algorithm;

import java.util.Iterator;

/**
 * Iterator for class Individual.
 * @author Wolfgang Bongartz
 * @param <T>
 */
public class IndividualIterator<T> implements Iterator<T> {
	
	public IndividualIterator(T[] chromosome) {
		_chromosome = chromosome;
		_current = 0;
	}
	
	private int _current;
	private T[] _chromosome;

	@Override
	public boolean hasNext() {
		return _current < _chromosome.length;
	}

	@Override
	public T next() {
		return _chromosome[_current++];
	}
}
