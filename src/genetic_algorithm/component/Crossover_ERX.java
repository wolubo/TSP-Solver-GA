/**
 * 
 */
package genetic_algorithm.component;

import java.util.*;

import genetic_algorithm.Individual;
import genetic_algorithm.Pair;


/**
 * Implements "edge recombination crossover".
 * For details refer to: "Genetische Algorithmen und Evolutionsstrategien", Eberhard Schöneburg et al, Addison-Wesley, 1994, ISBN: 3-89319-493-2.
 * @author Wolfgang Bongartz
 */
public class Crossover_ERX implements Crossover_Algorithm {
	
	private Random _randomNumberSource;
	private int    _numberOfChildren;

	public Crossover_ERX(Random randomNumberSource, int numberOfChildren) {
		if(randomNumberSource==null) throw new IllegalArgumentException();
		if(numberOfChildren<1) throw new IllegalArgumentException();
		_randomNumberSource = randomNumberSource;
		_numberOfChildren = numberOfChildren;
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.component.Crossover_Algorithm#apply(java.util.ArrayList)
	 */
	@Override
	public ArrayList<Individual> apply(ArrayList<Pair> parents) {
		ArrayList<Individual> retVal = new ArrayList<Individual>();

		for(Pair p: parents) {
			// Generate list of edges. 
			Edges e, edges = new Edges(p.get_p1(), p.get_p2());

			for(int counter=0; counter<_numberOfChildren; counter++) {
				e = (Edges) edges.clone();
				// Choose parent to start with.
				String current_location;
				int start = _randomNumberSource.nextInt(2);
				assert(start==0||start==1);
				if(start==0)
					current_location=p.get_p1().getGene(0);
				else
					current_location=p.get_p2().getGene(0);

				Individual child = new Individual(p.get_p1().size());
				for(int i=0; i<child.size(); i++) {
					assert(current_location!=null);
					child.setGene(i, current_location);
					e.removeLocation(current_location);
					current_location=e.chooseNextLocation();
				}
				
				retVal.add(child);
			}
		}
		
		assert(retVal.size()==_numberOfChildren*parents.size());
		return retVal;
	}
	
	private class Edges {
		HashMap<String, HashSet<String>> _edges;
		
		public Edges(Individual p1, Individual p2) {
			_edges = new HashMap<String, HashSet<String>>();
			HashSet<String> current_edges;
			int p1_current, p2_current;
			for(int i=0; i<p1.size(); i++) {
				current_edges = new HashSet<String>(); 
				String current_location = p1.getGene(i);
				p1_current = i; 
				p2_current = p2.find(current_location);
				current_edges.add(p1.getPreviousGene(p1_current));
				current_edges.add(p1.getNextGene(p1_current));
				current_edges.add(p2.getPreviousGene(p2_current));
				current_edges.add(p2.getNextGene(p2_current));
				_edges.put(current_location, current_edges);
			}
		}

		public Edges(Edges e) {
			_edges = new HashMap<String, HashSet<String>>();
			for(HashMap.Entry<String, HashSet<String>> c: e._edges.entrySet()) {
				HashSet<String> entry = new HashSet<String>();
				for(String s: c.getValue()) {
					entry.add(s);
				}
				_edges.put(c.getKey(), entry);
			}
		}

		/**
		 * @return
		 */
		public String chooseNextLocation() {
			if(_edges.size()==0) return null;
			
			// Search location with least number of edges.
			ArrayList<String> candidates = null;
			int least = -1;
			for(HashMap.Entry<String, HashSet<String>> c: _edges.entrySet()) {
				if( least<0 || least>c.getValue().size() ) {
					candidates = new ArrayList<String>();
					least = c.getValue().size();
					candidates.add(c.getKey());
				} else if(least==c.getValue().size()) {
					candidates.add(c.getKey());
				}
			}
			
			int i;
			if(candidates.size()==1) {
				i=0;
			} else {
				i = _randomNumberSource.nextInt(candidates.size());
				assert( i>=0 && i<candidates.size() );
			}
			return candidates.get(i);
		}

		/**
		 * @param location
		 */
		public void removeLocation(String location) {
			_edges.remove(location);
			for(HashSet<String> c: _edges.values()) {
				c.remove(location);
			}
		}
		
		public Object clone() {
			return new Edges(this);
		}
	}

	/* (non-Javadoc)
	 * @see genetic_algorithm.component.Crossover_Algorithm#computeNumberOfChildrenPerPair()
	 */
	@Override
	public int computeNumberOfChildrenPerPair() {
		return _numberOfChildren;
	}

}
