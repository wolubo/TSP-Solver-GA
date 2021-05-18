package de.wbongartz.tsp_solver_ga.distances;

import java.io.*;
import java.util.*;

/**
 * Implements a distance table.
 * @author Wolfgang Bongartz
 *
 */
public class Distances {
	
	/**
	 * Creates a distance table from a file.
	 * @param filename Name and path of the file.
	 * @throws IOException 
	 * @throws IllegalStateException
	 */
	public Distances(String filename) throws IOException, IllegalStateException {
		loadFromFile(filename);
	}
	
	/**
	 * Delivers the distance between two locations.
	 * @param from_location
	 * @param to_location
	 * @return
	 */
	public Integer getDistance(String from_location, String to_location) throws LocationUnknownException {
		
		HashMap<String,Integer> from = _distances.get(from_location);
		if(from==null) {
			throw new LocationUnknownException("The location " + from_location + " is unknown!");
		}
	
		Integer distance = from.get(to_location);
		if(distance==null) {
			throw new LocationUnknownException("The location " + to_location + " is unknown!");
		}

		return distance;
	}
	
	/**
	 * Delivers a set of all known locations.
	 * @return
	 */
	public Set<String> getLocations() {
		return _distances.keySet();
	}
	
	/**
	 * Checks whether a location is known or not.
	 * @param location
	 * @param throwException TRUE: Throw an exception if the location is unknown. FALSE: Just return a boolean indication whether the location is known or not.
	 * @throws LocationUnknownException
	 */
	public boolean checkLocation(String location, boolean throwException) throws LocationUnknownException {
		Set<String> knownLocations = getLocations();
		if( ! knownLocations.contains(location) ) {
			if(throwException) throw new LocationUnknownException("The location " + location + " is unknown!");
			return false;
		}
		return true;
	}
	
	/**
	 * Internal data structure that contains all distances.
	 * Basically, a two-dimensional array with the location names used as keys.
	 */
	private HashMap<String,HashMap<String,Integer>> _distances;

	/**
	 * Internal method to load the distance table from a file.
	 * @param filename
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	private void loadFromFile(String filename) throws IOException, IllegalStateException {
		_distances = new HashMap<String,HashMap<String,Integer>>();

		int lineNumber = 1;
		FileReader fileReader = null;
		BufferedReader buffer = null;
        Scanner scanner=null;
        
		try {
	        String line;
	        ArrayList<String> location_list = new ArrayList<String>();
	        HashMap<String,Integer> distance_list;

	        fileReader = new FileReader(filename);
	        buffer = new BufferedReader(fileReader);

	        // The first line must contain all locations as a semicolon-separated list.
	        line = buffer.readLine();
	        if(line==null) throw new IllegalStateException("The file " + filename + " is empty!");
	        scanner=new Scanner(line);
	        scanner.useDelimiter(";");
	        while(scanner.hasNext()) {
	        	location_list.add(scanner.next());
	        }
	        
	        // All other lines contain a list of distances separated by semicolon. The number of lines must be equal to the number of locations. 
	        for(String location_from: location_list) {
	        	distance_list = new HashMap<String,Integer>();
	        	_distances.put(location_from, distance_list);
	        	
		        line = buffer.readLine();
		        lineNumber++;
		        if(line==null) {
		        	// Not enough lines.
		        	throw new IllegalStateException("The file " + filename + " does not contain any distances for the location " + location_from + " (line: " + lineNumber + ")!");
		        }

		        scanner=new Scanner(line);
		        scanner.useDelimiter(";");

		        // The number of distances in a line must be equal to the number of locations.
		        for(String location_to: location_list) {
			        if(scanner.hasNext()) {
			        	String distance_str = scanner.next();
			        	int distance_int = Integer.parseInt(distance_str);
			        	Integer distance = new Integer(distance_int);
			        	distance_list.put(location_to, distance);
			        } else {
			        	// Not enough distances.
			        	throw new IllegalStateException("The file " + filename + " does not contain the distance between " + location_from + " and " + location_to + " (line: " + lineNumber + ")!");
			        }
		        }
		        if(scanner.hasNext()) {
		        	// To many distances.
		        	throw new IllegalStateException("The file " + filename + " contains to many distances for the location " + location_from + " (line: " + lineNumber + ", distance: " + scanner.next() + ")!");
		        }
	        }

	        line = buffer.readLine();
	        if(line!=null) {
	        	// Not many lines.
	        	throw new IllegalStateException("The file " + filename + " contains to many lines (line: " + lineNumber + ", extra line: " + line + ")!");
	        }

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
}
