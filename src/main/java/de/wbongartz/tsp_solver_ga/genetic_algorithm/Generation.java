package de.wbongartz.tsp_solver_ga.genetic_algorithm;

import java.util.*;


/**
 * @author Wolfgang Bongartz
 *
 */
public class Generation implements Iterable<Individual> {

	private int _generationNumber;
	private ArrayList<Individual> _individuals;
	private Individual _bestInClass, _worstInClass;
	
	/**
	 * 
	 */
	public Generation() {
		_generationNumber=-1;
		_individuals=new ArrayList<Individual>();
		set_bestInClass(null);
	}

	public Generation(ArrayList<Individual> individuals) {
		_generationNumber=-1;
		_individuals = individuals;
		set_bestInClass(null);
	}

	/**
	 * 
	 * @param newIndividual
	 */
	public void addIndividual(Individual newIndividual) {
		_individuals.add(newIndividual);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Individual getIndividual(int index) {
		return _individuals.get(index);
	}
	
	/**
	 * 
	 * @param index
	 */
	public void removeIndividual(int index) {
		_individuals.remove(index);
	}

	/**
	 * 
	 * @param toBeRemoved
	 */
	public boolean removeIndividual(Individual toBeRemoved) {
		return _individuals.remove(toBeRemoved);
	}

	/**
	 * 
	 * @return
	 */
	public int getGenerationNumber() {
		return _generationNumber;
	}

	public void setGenerationNumber(int newGenerationNumber) {
		assert(newGenerationNumber>=0);
		_generationNumber = newGenerationNumber;
	}

	/**
	 * Actual number of individuals in the generation.
	 * @return
	 */
	public int size() {
		return _individuals.size();
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Individual> iterator() {
		return _individuals.iterator();
	}

	/**
	 * @return the bestInClass
	 */
	public Individual get_bestInClass() {
		return _bestInClass;
	}

	/**
	 * @param bestInClass the bestInClass to set
	 */
	public void set_bestInClass(Individual bestInClass) {
		this._bestInClass = bestInClass;
	}

	/**
	 * @return the worstInClass
	 */
	public Individual get_worstInClass() {
		return _worstInClass;
	}

	/**
	 * @param worstInClass
	 */
	public void set_worstInClass(Individual worstInClass) {
		this._worstInClass = worstInClass; 
	}

	/**
	 * 
	 */
	public void sort() {
		Collections.sort(_individuals, new IndividualComparator());
	}

	
}
