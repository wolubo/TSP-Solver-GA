package genetic_algorithm;

public class GeneticAlgorithm {
	
	public GeneticAlgorithm() {
		
	}
	
	public Result run() {
		Result result;
		
//		setUp();
		
		currentGeneration = createFirstGeneration();
		
		while(evaluateGeneration(currentGeneration, generationCounter)) {
			parents = selectParents(currentGeneration);
			children = createChildren(parents, currentGeneration);
			children = mutateChildren(children, parents, currentGeneration);
			nextGeneration = applySelectionProcess(children, parents, currentGeneration);
			currentGeneration = nextGeneration;
			generationCounter++;
		}
		result = createResult();
//		tearDown();
		return result;
	}

}
