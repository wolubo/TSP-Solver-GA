package de.wbongartz.tsp_solver_ga;

import de.wbongartz.tsp_solver_ga.distances.Distances;
import de.wbongartz.tsp_solver_ga.distances.LocationUnknownException;
import de.wbongartz.tsp_solver_ga.genetic_algorithm.GeneticAlgorithm;
import de.wbongartz.tsp_solver_ga.genetic_algorithm.Individual;
import de.wbongartz.tsp_solver_ga.genetic_algorithm.component.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Wolfgang Bongartz
 *
 */
public class MainProgram {

	private static String[] _locations2visit;
	private static int      _populationSize;
	private static int      _numberOfGenerations;
	private static int      _numberOfChildren;
	private static int      _mutationRate;
	private static int      _elitismRate;
	private static boolean  _showProgress;
	private static boolean  _showIntermediateResults;

	private static Crossover_Algorithm    _crossoverAlgorithm = null;
	private static Mutation_Algorithm     _mutationAlgorithm = null;
	private static Substitution_Algorithm _substitutionAlgorithm = null;
	private static Evaluation_Algorithm   _evaluationAlgorithm = null;
	private static Selection_Algorithm    _selectionAlgorithm = null;

	private static Distances _distances       = null;
	private static Random _randomNumberSource = new Random();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			_distances = new Distances("distances.csv");
			
			if(args.length==0) {
				System.out.println("No parameter given. Will use standard values.");
				loadDefaults();
			} else if(args.length==1) {
				loadFile(args[0]); // One parameter. Must be the name of a text-file that contains the locations to visit.
			} else {
				printHelpTextAndExit(null);
			}

			MainProgram solver = new MainProgram();
			solver.run();

		} catch (Exception e) {
			printHelpTextAndExit(e.toString());
		}
	}

	/**
	 * 
	 */
	private static void loadDefaults() {
		String[] d = { "Aachen", "Basel", "Bremen", "Dortmund", "Dresden", "Duesseldorf", "Emden", "Erfurt", "Flensburg", "Koeln", "Muenchen", "Berlin", "Hamburg", "Frankfurt Main", "Frankfurt Oder" };
		_locations2visit = d;
		_populationSize = 20000;
		_numberOfGenerations = 250;
		_numberOfChildren = 5;
		_mutationRate = 5;
		_elitismRate = 10;
		_showProgress = true;
		_showIntermediateResults = true;

		_crossoverAlgorithm    = new Crossover_ERX(_randomNumberSource, _numberOfChildren);
		_mutationAlgorithm     = new Mutation_RandomExchange(_randomNumberSource, _mutationRate);
		_substitutionAlgorithm = new Substitution_Elitism(_elitismRate);
		_evaluationAlgorithm   = new Evaluation_RoundTrip(_distances);
		_selectionAlgorithm    = new Selection_RelativeFitness(_randomNumberSource);
	}

	private GeneticAlgorithm _ga;

	/**
	 * @param distances
	 * @param roundTrip
	 * @throws LocationUnknownException 
	 */
	public MainProgram() throws LocationUnknownException {
		// Check consistency of the list of locations to visit.
		if(_locations2visit.length<3) throw new IllegalArgumentException("Not enough locations to create a round-trip (at least 3 locations needed)!");

		// Check if all locations are known.
		if(_distances==null) throw new IllegalArgumentException("No distance table!");
		for(String location: _locations2visit) _distances.checkLocation(location, true);

		// Create an instance of the genetic algorithm.
		_ga = new GeneticAlgorithm(_locations2visit);

		// Configure the genetic algorithm.
		_ga.set_randomNumberSource(_randomNumberSource);
		_ga.set_generationSize(_populationSize);
		_ga.set_maxNumberOfGenerations(_numberOfGenerations);
		_ga.set_crossoverAlgorithm(_crossoverAlgorithm);
		_ga.set_mutationAlgorithm(_mutationAlgorithm);
		_ga.set_substitutionAlgorithm(_substitutionAlgorithm);
		_ga.set_evaluationAlgorithm(_evaluationAlgorithm);
		_ga.set_selectionAlgorithm(_selectionAlgorithm);
		_ga.set_showProgress(_showProgress);
		_ga.set_showIntermediateResults(_showIntermediateResults);
	}

	/**
	 * Run the genetic algorithm and present the result.
	 * @throws Exception 
	 */
	private void run() throws Exception {
		Individual i = _ga.run();

		DecimalFormat df = new DecimalFormat("#");
		df.setRoundingMode(RoundingMode.DOWN);
		System.out.println();
		System.out.println();
		System.out.println("BEST ROUTE: " + i.toString());
		System.out.println("DISTANCE:   " + df.format(i.get_rating()));
	}

	/**
	 * Load program data from text-file.
	 * The file must contain a key-value-list.
	 * @param filename
	 * @throws IOException 
	 */
	private static void loadFile(String filename) throws IOException {

		FileReader fileReader = null;
		BufferedReader buffer = null;
		Scanner scanner=null;
		String mutation="none", crossover="PMX", substitution="generational replacement", evaluation="round trip", selection="relative fitness";
		String line=null, command, arguments;
		String line_parts[];
		int lineNo = 0;

		try {
			fileReader = new FileReader(filename);
			buffer = new BufferedReader(fileReader);

			line = buffer.readLine();
			while(line!=null) {
				lineNo++;

				line_parts = line.split("=");
				if(line_parts.length==2) {
					command = line_parts[0].trim();
					arguments = line_parts[1].trim();

					if(command.compareTo("locations2visit")==0) {

						ArrayList<String> roundTrip = new ArrayList<String>();

						scanner=new Scanner(arguments);
						scanner.useDelimiter(",");
						while(scanner.hasNext()) {
							roundTrip.add(scanner.next().trim());
						}

						_locations2visit = new String[roundTrip.size()];
						_locations2visit = (String[]) roundTrip.toArray(_locations2visit);

					} else if(command.compareTo("populationSize")==0) {

						_populationSize = Integer.parseInt(arguments);

					} else if(command.compareTo("numberOfGenerations")==0) {

						_numberOfGenerations = Integer.parseInt(arguments);

					} else if(command.compareTo("numberOfChildren")==0) {

						_numberOfChildren = Integer.parseInt(arguments);

					} else if(command.compareTo("mutationRate")==0) {

						_mutationRate = Integer.parseInt(arguments);

					} else if(command.compareTo("elitismRate")==0) {

						_elitismRate = Integer.parseInt(arguments);

					} else if(command.compareTo("showProgress")==0) {

						_showProgress = (arguments.compareTo("yes")==0); 

					} else if(command.compareTo("showIntermediateResults")==0) {

						_showIntermediateResults = (arguments.compareTo("yes")==0);

					} else if(command.compareTo("crossover scheme")==0) {

						crossover = arguments;

					} else if(command.compareTo("mutation scheme")==0) {

						mutation = arguments;

					} else if(command.compareTo("substitution scheme")==0) {

						substitution = arguments;

					} else if(command.compareTo("evaluation scheme")==0) {

						evaluation = arguments;

					} else if(command.compareTo("selection scheme")==0) {

						selection = arguments;
					} else {
						throw new IllegalArgumentException("Syntax error in line " + lineNo + ": unknown command");
					}
				} else if(line.length()==0) {
					// Skip empty lines.
				} else {

					throw new IllegalArgumentException("Syntax error in line " + lineNo + ": <command> = <argument-list>");
				}

				line = buffer.readLine();
			}

			if(crossover.compareTo("ERX")==0) 
				_crossoverAlgorithm    = new Crossover_ERX(_randomNumberSource, _numberOfChildren);
			else if(crossover.compareTo("PMX")==0) 
//				_crossoverAlgorithm    = new Crossover_PMX(_randomNumberSource);
				throw new IllegalArgumentException("PMX is faulty! Please choose ERX instead.");
			else if(crossover.compareTo("none")==0) 
				_crossoverAlgorithm    = new Crossover_NoCrossover();
			else
				throw new IllegalArgumentException("unknown crossover scheme");

			if(mutation.compareTo("random exchange")==0) 
				_mutationAlgorithm     = new Mutation_RandomExchange(_randomNumberSource, _mutationRate);
			else if(mutation.compareTo("none")==0) 
				_mutationAlgorithm     = new Mutation_NoMutation();
			else
				throw new IllegalArgumentException("unknown crossover scheme");

			if(substitution.compareTo("elitism")==0) 
				_substitutionAlgorithm = new Substitution_Elitism(_elitismRate);
			else if(substitution.compareTo("generational replacement")==0) 
				_substitutionAlgorithm = new Substitution_GenerationalReplacement();
			else
				throw new IllegalArgumentException("unknown substitution scheme");

			if(evaluation.compareTo("round trip")==0) 
				_evaluationAlgorithm   = new Evaluation_RoundTrip(_distances);
			else
				throw new IllegalArgumentException("unknown evaluation scheme");

			if(selection.compareTo("relative fitness")==0) 
				_selectionAlgorithm    = new Selection_RelativeFitness(_randomNumberSource);
			else
				throw new IllegalArgumentException("unknown selection scheme");

		} finally {
			try {
				if(scanner!=null) scanner.close();
				if(buffer!=null) 
					buffer.close();
				else
					if(fileReader!=null) fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Print some usage-hints to standard-out. 
	 */
	private static void printHelpTextAndExit(String msg) {
		if(msg!=null) {
			System.out.println("ERROR:");
			System.out.println(msg);
		} else {
			System.out.println("TSPSolver");
			System.out.println("Generates solutions for the traveling salesman problem.");
			System.out.println();
			System.out.println("Usage:");
			System.out.println("	java -jar MainProgram.jar");
			System.out.println("		Uses default values.");
			System.out.println("		Example: java -jar TpsSolver.jar");
			System.out.println("	java -jar MainProgram.jar <textfile>");
			System.out.println("		The text file contains configuration date (locations to visit etc.)");
			System.out.println("		Example: java -jar TpsSolver.jar tsp_test.txt");
		}
		System.exit(-1);
	}


}
