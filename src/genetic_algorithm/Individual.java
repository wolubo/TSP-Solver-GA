package genetic_algorithm;

import java.util.*;

/**
 * Represents an individual set of chromosome to be used in an genetic algorithm.
 * @author Wolfgang Bongartz
 */
public class Individual implements Iterable<String> {

	private String[] _chromosome;
	private float _fitness;
	private float _rating;

	/**
	 * 
	 * @param chromosome
	 */
	public Individual(String[] chromosome) {
		if(chromosome==null || chromosome.length==0) throw new IllegalArgumentException();
		_chromosome = Arrays.copyOf(chromosome, chromosome.length);
		set_fitness(0);
		set_rating(0);
	}

	/**
	 * 
	 * @param chromosome
	 * @param randomNumberSource
	 */
	public Individual(String[] chromosome, Random randomNumberSource) {
		this(randomizer(chromosome, randomNumberSource));
	}
	
	/**
	 * 
	 * @param chromosome
	 * @param randomNumberSource
	 * @return
	 */
	private static String[] randomizer(String[] chromosome, Random randomNumberSource) {
		// TODO: Optimieren!
		String[] retVal = new String[chromosome.length];
		for(int i=0; i<retVal.length; i++) retVal[i]=null;
		int x;
		for(String gene: chromosome) {
			do {
				x = randomNumberSource.nextInt(retVal.length);
			} while(retVal[x]!=null);
			retVal[x]=gene;
		}
		return retVal;
	}

	/**
	 * 
	 * @param other
	 */
	public Individual(Individual other) {
		this(other._chromosome);
		set_fitness(other.get_fitness());
		set_rating(other.get_rating());
	}

	/**
	 * Creates a new individual with an empty chromosome.
	 * @param size
	 */
	public Individual(int size) {
		_chromosome = new String[size];
	}

	/**
	 * Returns the current size of the chromosome.
	 * @return
	 */
	public int size() {
		return _chromosome.length;
	}
	
	/**
	 * "Bewertung"
	 * @return the _rating
	 */
	public float get_rating() {
		return _rating;
	}

	/**
	 * "Bewertung"
	 * @param rating the _rating to set
	 */
	public void set_rating(float rating) {
		this._rating = rating;
	}

	/**
	 * "Fitness" 
	 * @return
	 */
	public float get_fitness() {
		return _fitness;
	}
	
	/**
	 * "Fitness" 
	 * @param newFitness
	 */
	public void set_fitness(float newFitness) {
		_fitness = newFitness;
	}

	@Override
	public Iterator<String> iterator() {
		return new IndividualIterator<String>(_chromosome);
	}

	/**
	 * Set an item in the chromosome.
	 * @param index
	 * @param value
	 */
	public void setGene(int index, String value) {
		if(index<0 || index>=size()) throw new IllegalArgumentException();
		_chromosome[index] = value;
	}

	/**
	 * Get an item from the chromosome.
	 * @param index
	 * @return
	 */
	public String getGene(int index) {
		if(index<0 || index>=size()) throw new IllegalArgumentException();
		return _chromosome[index];
	}
	
	/**
	 * Exchange a part of the chromosome with a sequence of genes starting from 'startIndex'.
	 * Note: Original object will remain unchanged (class is immutable)!
	 * @param startIndex
	 * @param sequence
	 */
	public void exchangeGeneSequence(int startIndex, String[] sequence) {
		if( startIndex<0 || (startIndex+sequence.length>size()) ) throw new IllegalArgumentException();
		if( sequence==null || sequence.length==0 ) throw new IllegalArgumentException();
		for(int i=0; i<sequence.length; i++) {
			_chromosome[startIndex+i] = sequence[i];
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(!(obj instanceof Individual)) return false;
		Individual other = (Individual) obj;
		if(this.size()!=other.size()) return false;
		for(int i=0; i<_chromosome.length; i++) {
			if( ! _chromosome[i].equals(other.getGene(i)) ) return false;
		}
		return true;
	}
	
//	@Override
//	public String toString() {
//		String str = "(";
//		for(int i=0; i<_chromosome.length; i++) {
//			if(i!=0) str += ",";
//			str += _chromosome[i];
//		}
//		return str + ")";
//	}
	
	@Override
	public String toString() {
		String str = "";
		int i=0;
		for(String s: this) {
			if(i++!=0) str += " --> ";
			str += s;
		}
		return str;
	}
	

	@Override
	public int hashCode() {
		return _chromosome.hashCode();
	}

	/**
	 * Find a location within the chromosome and return it's index.
	 * @param location Location to look for.
	 * @return Index of the location. 
	 */
	public int find(String location) {
		for(int i=0; i<_chromosome.length; i++) {
			if(_chromosome[i].compareTo(location)==0) return i;
		}
		throw new IllegalArgumentException("Location " + location + " unknown!");
	}

	/**
	 * Delivers the gene "left" of the one indicated by 'current'
	 * @param current
	 * @return
	 */
	public String getPreviousGene(int current) {
		if(current==0) {
			return this.getGene(_chromosome.length-1);
		} else {
			return this.getGene(current-1);
		}
	}

	/**
	 * Delivers the gene "right" of the one indicated by 'current'
	 * @param current
	 */
	public String getNextGene(int current) {
		if(current==_chromosome.length-1) {
			return this.getGene(0);
		} else {
			return this.getGene(current+1);
		}
	}
	
}
