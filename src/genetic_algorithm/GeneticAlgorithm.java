package genetic_algorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import genetic_algorithm.component.*;

/**
 * @author Wolfgang Bongartz
 *
 */
public class GeneticAlgorithm {
	
	private int       _generationSize;
	private int       _maxNumberOfGenerations;
	private String[]  _locations2visit;
	private Random    _randomNumberSource;
	private Crossover_Algorithm _crossoverAlgorithm;
	private Mutation_Algorithm _mutationAlgorithm;
	private Substitution_Algorithm _substitutionAlgorithm;
	private Evaluation_Algorithm _evaluationAlgorithm;
	private Selection_Algorithm _selectionAlgorithm;
	private boolean _showProgress=true;
	private boolean _showIntermediateResults=true;
	
	
	/**
	 * @param evaluation_Algorithm the evaluationAlgorithm to set
	 */
	public void set_evaluationAlgorithm(Evaluation_Algorithm evaluation_Algorithm) {
		this._evaluationAlgorithm = evaluation_Algorithm;
	}

	/**
	 * @param substitution_Algorithm the substitutionAlgorithm to set
	 */
	public void set_substitutionAlgorithm(Substitution_Algorithm substitution_Algorithm) {
		this._substitutionAlgorithm = substitution_Algorithm;
	}

	/**
	 * @param mutation_Algorithm the mutationAlgorithm to set
	 */
	public void set_mutationAlgorithm(Mutation_Algorithm mutation_Algorithm) {
		this._mutationAlgorithm = mutation_Algorithm;
	}

	private Individual _theBestOfTimes;

	/**
	 * @return the bestOfAllTimes
	 */
	public Individual get_theBestOfTimes() {
		return _theBestOfTimes;
	}

	/**
	 * @param bestOfAllTimes the bestOfAllTimes to set
	 */
	private void set_theBestOfTimes(Individual theBestOfTimes) {
		assert(theBestOfTimes==null || _theBestOfTimes==null || theBestOfTimes.get_rating()<_theBestOfTimes.get_rating());
		this._theBestOfTimes = theBestOfTimes;
	}

	/**
	 * 
	 * @param locations2visit
	 */
	public GeneticAlgorithm(String[] locations2visit) {
		
		if(locations2visit==null || locations2visit.length<3) throw new IllegalArgumentException("No locations to visit!");

		// Check for double entries.
		for(int outer=0; outer<locations2visit.length; outer++) {
			String left = locations2visit[outer];
			for(int inner=outer+1; inner<locations2visit.length; inner++) {
				String right = locations2visit[inner];
				if(left.compareTo(right)==0) throw new IllegalArgumentException("The location " + right + " would have to be visited twice. That's not possible.");
			}
		}

		set_locations2visit(locations2visit);

		set_generationSize(0);
		set_maxNumberOfGenerations(0);
		set_randomNumberSource(null);
		set_crossoverAlgorithm(null);
		
		set_theBestOfTimes(null);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Individual run() throws Exception {
		
		// Check components.
		if(_randomNumberSource==null) throw new IllegalStateException("No source of random numbers!");
		if(_crossoverAlgorithm==null) throw new IllegalStateException("No crossover algorithm!");
		if(_mutationAlgorithm==null) throw new IllegalStateException("No mutation algorithm!");
		if(_substitutionAlgorithm==null) throw new IllegalStateException("No substitution algorithm!");
		
		int numberOfChildren        = _substitutionAlgorithm.computeNumberOfChildren(this.get_generationSize());
		int numberOfChildrenPerPair = _crossoverAlgorithm.computeNumberOfChildrenPerPair();
		int numberOfPairs           = numberOfChildren / numberOfChildrenPerPair;
		
		// Check configuration.
		if(_maxNumberOfGenerations<1) throw new IllegalStateException("To termination condition!");
		if(_generationSize<=0) throw new IllegalStateException("Generation size unknown!");
//		if(_numberOfParentPairs*2>_generationSize) throw new IllegalStateException("Configuration error: Number of pairs exceeds generation size!");

		Individual result;
		ArrayList<Individual> children;
		ArrayList<Pair> parents;
		
//		setUp();
		
		Generation currentGeneration = createFirstGeneration();
		currentGeneration.setGenerationNumber(0);
		Generation nextGeneration    = null;
		int generationCounter = 0;
		boolean cr=false;
		
		DecimalFormat df = new DecimalFormat("#");
		df.setRoundingMode(RoundingMode.DOWN);

		while( generationCounter < _maxNumberOfGenerations ) {
			
			 _evaluationAlgorithm.apply(currentGeneration);
			 
			Individual bestInClass = currentGeneration.get_bestInClass();
			Individual theBestOfTimes = this.get_theBestOfTimes(); 
			if(theBestOfTimes==null || bestInClass.get_rating()<theBestOfTimes.get_rating()) {
				if(cr)System.out.println("");
				set_theBestOfTimes(bestInClass);
				if(_showIntermediateResults) System.out.println("Generation: " + currentGeneration.getGenerationNumber() + ", distance " + df.format(this.get_theBestOfTimes().get_rating()) + ": " + this.get_theBestOfTimes().toString());
				cr=false;
			} else if(_showProgress) {
				System.out.print(".");
				cr=true;
			}
			
			// Check invariant.
			assert(currentGeneration.size()==get_generationSize());
			
			parents  = _selectionAlgorithm.apply(currentGeneration, numberOfPairs);
			
			children = _crossoverAlgorithm.apply(parents);
			
			children = _mutationAlgorithm.apply(children);
			
			nextGeneration = _substitutionAlgorithm.apply(children, parents, currentGeneration);
			
			currentGeneration = nextGeneration;
			currentGeneration.setGenerationNumber(++generationCounter);
		}
		
		result = this.get_theBestOfTimes();
		
//		tearDown();
		
		return result;
	}

	/**
	 * @return the _generationSize
	 */
	public int get_generationSize() {
		return _generationSize;
	}

	/**
	 * Set's the number of individuals per generation. Should be an equal number.
	 * @param generationSize the generationSize to set
	 */
	public void set_generationSize(int generationSize) {
		this._generationSize = generationSize*2/2; // Make it even...
	}

	/**
	 * @return the maxNumberOfGenerations
	 */
	public int get_maxNumberOfGenerations() {
		return _maxNumberOfGenerations;
	}

	/**
	 * @param maxNumberOfGenerations the maxNumberOfGenerations to set
	 */
	public void set_maxNumberOfGenerations(int maxNumberOfGenerations) {
		this._maxNumberOfGenerations = maxNumberOfGenerations;
	}

	/**
	 * @return the locations2visit
	 */
	public String[] get_locations2visit() {
		return _locations2visit;
	}

	/**
	 * @param locations2visit the locations2visit to set
	 */
	public void set_locations2visit(String[] locations2visit) {
		this._locations2visit = locations2visit;
	}

	/**
	 * @param randomNumberSource the randomNumberSource to set
	 */
	public void set_randomNumberSource(Random randomNumberSource) {
		this._randomNumberSource = randomNumberSource;
	}

	/**
	 * @param crossover_Algorithm the crossoverAlgorithm to set
	 */
	public void set_crossoverAlgorithm(Crossover_Algorithm crossover_Algorithm) {
		this._crossoverAlgorithm = crossover_Algorithm;
	}

	/**
	 * Create a first generation.
	 * @return
	 */
	protected Generation createFirstGeneration() {
		Individual newIndividual;
		Generation retVal = new Generation();
		for(int i=0; i<_generationSize; i++) {
			newIndividual = new Individual(_locations2visit, _randomNumberSource);
			retVal.addIndividual(newIndividual);
		}
		return retVal;
	}

	/**
	 * @param selection_Algorithm the selectionAlgorithm to set
	 */
	public void set_selectionAlgorithm(Selection_Algorithm selection_Algorithm) {
		this._selectionAlgorithm = selection_Algorithm;
	}

	/**
	 * @param showProgress
	 */
	public void set_showProgress(boolean showProgress) {
		_showProgress = showProgress;
	}

	/**
	 * @param showIntermediateResults
	 */
	public void set_showIntermediateResults(boolean showIntermediateResults) {
		_showIntermediateResults = showIntermediateResults;
	}
	
}
