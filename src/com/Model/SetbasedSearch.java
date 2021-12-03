package com.Model;

import java.util.ArrayList;
import java.util.Random;

import com.DataStructures.CourseLab;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.Triplet;
import com.Main.Eval;
import com.OrModel.Crossover;
import com.OrModel.Populate;

public class SetbasedSearch {

	// PREDIFINED VARIABLES
	private final int MAX_EVAL = Integer.MAX_VALUE;
	private final int MAX_ITERATIONS_NO_IMPROVEMENT = 15;
	private final long TIME_LIMIT_SECONDS = 3;
	private final boolean USE_TIME_LIMIT = true;
	
	private final int MAX_POP_SIZE = 30;
	private final int INITIAL_POP_SIZE = 20;
	
	private Random rand;
	
	private Eval eval;
	private ArrayList<ArrayList<Slot>> facts;
	private ArrayList<Integer> currentEvals;
	private ArrayList<Slot> slotsArray;
	private ArrayList<CourseLab> courseLabArray;
	private ArrayList<Pair<CourseLab, CourseLab>> notCompatibleArray;
	private ArrayList<Pair<CourseLab, Slot>> unwantedArray;
	private ArrayList<Triplet<Slot, CourseLab, Integer>> prefArray;
	private ArrayList<Pair<CourseLab, Slot>> partialAssignList;
	private ArrayList<Pair<CourseLab, CourseLab>> pairArray;
    
	public SetbasedSearch(ArrayList<Slot> slotsArray, ArrayList<CourseLab> courseLabArray,
			ArrayList<Pair<CourseLab, CourseLab>> notCompatibleArray,
			ArrayList<Pair<CourseLab, Slot>> unwantedArray,
            ArrayList<Triplet<Slot, CourseLab, Integer>> prefArray,
            ArrayList<Pair<CourseLab, CourseLab>> pairArray,
            ArrayList<Pair<CourseLab, Slot>> partialAssignArray) {
		
		facts = new ArrayList<ArrayList<Slot>>();
		currentEvals = new ArrayList<Integer>();
		this.slotsArray = slotsArray;
		this.courseLabArray = courseLabArray;
		this.notCompatibleArray = notCompatibleArray;
		this.unwantedArray = unwantedArray;
		this.prefArray = prefArray;
		this.partialAssignList = partialAssignArray;
		this.pairArray = pairArray;
		
		this.eval = new Eval(1,1,1,1);	// Set weights for Eval
		this.rand = new Random();
		
	}
	
	/**
	 * Performs Setbased search
	 * @return a good enough fact
	 */
	public ArrayList<Slot> search() {
		long startTime = System.nanoTime();
		long currentTime = startTime;
		
		int noImprovementCounter = 0;
		int lowestEval, highestEval, currentEval;
		
		// Populate
		for (int i = 0; i < INITIAL_POP_SIZE; i ++) {
			facts.add(Populate.populate(courseLabArray, slotsArray, notCompatibleArray, unwantedArray, partialAssignList));
		}
		
		if (facts.isEmpty()) {
			System.out.println("Error! Empty list of facts");
			return null;
		}
		
		initCurrentEvals();
		lowestEval = this.getLowestEval(facts);
		currentEval = lowestEval;
		
		ArrayList<Slot> fact1 = new ArrayList<>();
		ArrayList<Slot> fact2 = new ArrayList<>();
		ArrayList<Slot> newFact = new ArrayList<>();
		
		//Loop (time based and/or based on number of iterations passed without improvement)
		while((noImprovementCounter < MAX_ITERATIONS_NO_IMPROVEMENT) && withinTimeLimit(startTime, currentTime)) {
			//System.out.println("Looping");
			// Check if decay is necessary
			if (facts.size() > MAX_POP_SIZE) {
				decay();
			}
			
			// Choose fact1, and fact2 to pass to Crossover
			highestEval = this.getHighestEval(facts);
			fact1 = this.getFactWithLowestEval(facts);
			//System.out.println("Lowest Eval: " + evalFact(fact1));
			
			//System.out.println("Cross");
			fact2 = this.getFactViaStochasticAcceptance(highestEval, fact1);
			//System.out.println("After Cross");
			
			newFact = Crossover.crossover(courseLabArray, slotsArray, notCompatibleArray, unwantedArray, fact1, fact2);
			/*int i = 0;
			for (Slot s : newFact) {
				//System.out.println(courseLabArray.get(i).getName());
				s.printSlot();
				i++;
			}
			System.out.println("---------------------------------");
			System.out.println("---------------------------------");
			System.out.println("---------------------------------");*/

			if(!fact1.equals(newFact) && !fact2.equals(newFact))
			{
				facts.add(newFact); // Add the new fact generated by crossover()
				currentEvals.add(evalFact(newFact));	// Add evaluation of fact to cached evals

				currentEval = this.evalFact(newFact); // Update currentEval
			}

			// Updates the lowest evaluation and the counter for no. iterations without improvement
			if (currentEval < lowestEval) {
				noImprovementCounter = 0;	// Reset the counter if a better evaluation is found
				lowestEval = currentEval;	// Update the lowest evaluation
			} else noImprovementCounter ++;	
			
			currentTime = System.nanoTime();	// Update current time
		}
		
		// Return best fact based on Eval.eval(...)
		return getFactWithLowestEval(facts);
	}
	
	
	/** 
	 * Chooses a fact based on a roulette-wheel selection via stochastic acceptance, which causes solutions with a lower 
	 * Eval value to be selected more often.
	 * @param highestEval the highest evaluation in the ArrayList of facts
	 * @return a fact chosen via stochastic acceptance
	 */
	private ArrayList<Slot> getFactViaStochasticAcceptance(int highestEval, ArrayList<Slot> fact1) {
		ArrayList<Slot> fact2;
		int indexOfFact1 = facts.indexOf(fact1);
		int tempEval, pAccept, indexOfFact2;
		int max_iterations = facts.size();
		
		int counter = 0;
		while (true) {	// Generates random facts until one is accepted
			// Gets a random fact
			indexOfFact2 = this.generateRandomInt(0, facts.size());
			fact2 = facts.get(indexOfFact2);	
			
			while(indexOfFact1 == indexOfFact2) {	// If fact1 and fact2 are the same
				// Gets a random fact
				indexOfFact2 = this.generateRandomInt(0, facts.size());
				fact2 = facts.get(indexOfFact2);	
			}
			
			tempEval = eval.eval(fact2, slotsArray, courseLabArray, prefArray, pairArray);	// Evaluation of fact2;
			pAccept = ((highestEval - tempEval) * 100) / highestEval;	// Percentage chance that fact2 is accepted
			if (this.generateRandomInt(0, 100) < pAccept) break;
			if (counter > max_iterations) break;	// If pAccept is too low, max iterations will stop from looping too many times
			counter ++;
		}
		return fact2;
	}


	
	/**
	 * Initialize cached evals for current facts
	 */
	void initCurrentEvals() {
		ArrayList<Integer> newEvals = new ArrayList<>();
		for (var fact : facts) {
			newEvals.add(evalFact(fact));
		}
		currentEvals = newEvals;
	}

	/**
	 * Remove excessive facts that have the highest evals
	 */
	private void decay() {
		int nExcessiveFacts = Math.round(facts.size() * 0.4f);
		while (nExcessiveFacts > 0) {
			int maxEval = -1;
			int iMaxEval = 0;
			for (int i = 0; i < currentEvals.size(); i ++) {
				if (currentEvals.get(i) > maxEval) {
					maxEval = currentEvals.get(i);
					iMaxEval = i;
				}
			}
			facts.remove(iMaxEval);
			currentEvals.remove(iMaxEval);
			nExcessiveFacts -= 1;
		}
	}

	/**
	 * Generates a random number between min and max using the Random class
	 * @param min the minimum value (inclusive)
	 * @param max the maximum value (exclusive)
	 * @return the random int generated between min and max
	 */
	private int generateRandomInt(int min, int max) {
		if (max < 0 || max <= min) return 0;
		return rand.nextInt((max - min)) + min;
	}
	
	/**
	 * Evaluates the given slot using Eval.eval(...)
	 * @param fact the fact to evaluate
	 * @return the evaluation of the fact
	 */
	private int evalFact(ArrayList<Slot> fact) {
		return eval.eval(fact, slotsArray, courseLabArray, prefArray, pairArray);
	}
	
	/**
	 * Gets the fact with the lowest evaluation based on eval() from an ArrayList of facts
	 * @param facts the ArrayList of facts
	 * @return the fact with the lowest evaluation
	 */
	private ArrayList<Slot> getFactWithLowestEval(ArrayList<ArrayList<Slot>> facts) {
		int min = MAX_EVAL;
		int indexMin = -1;
		int currentEval;
		for (int i = 0; i < facts.size(); i ++) {
			currentEval = evalFact(facts.get(i));
			if (currentEval < min) {
				min = currentEval;
				indexMin = i;
			}
		}
		if (facts.size() == 0) {
			System.out.println("Error! Empty list of facts");
			return null;
		}
		return facts.get(indexMin);
	}
	
	/**
	 * Gets the highest evaluation of a fact based on Eval.eval(...) from an ArrayList of facts
	 * @param facts the ArrayList of facts
	 * @return the highest evaluation
	 */
	private int getHighestEval(ArrayList<ArrayList<Slot>> facts) {
		int max = -MAX_EVAL;
		int currentEval;
		for (ArrayList<Slot> fact : facts) {
			currentEval = evalFact(fact);
			if (currentEval > max) max = currentEval;
		}
		if (facts.size() == 0) {
			System.out.println("Error! Empty list of facts");
			return -MAX_EVAL;
		}
		return max;
	}
	
	/**
	 * Gets the lowest evaluation of a fact based on Eval.eval(...) from an ArrayList of facts
	 * @param facts the ArrayList of facts
	 * @return the lowest evaluation
	 */
	private int getLowestEval(ArrayList<ArrayList<Slot>> facts) {
		int min = MAX_EVAL;
		int currentEval;
		for (ArrayList<Slot> fact : facts) {
			currentEval = evalFact(fact);
			if (currentEval < min) min = currentEval;
		}
		if (facts.size() == 0) {
			System.out.println("Error! Empty list of facts");
			return MAX_EVAL;
		}
		return min;
	}
	
	/**
	 * Checks if the search is within the time limit
	 * @param startTime the starting time of the search
	 * @param currentTime the current time
	 * @return true if the time elapsed does not exceed the time limit (TIME_LIMIT_SECONDS)
	 */
	private boolean withinTimeLimit(long startTime, long currentTime) {
		if (USE_TIME_LIMIT) {
			long timeElapsed = currentTime - startTime;
			if ((timeElapsed / 1000000000.0) > TIME_LIMIT_SECONDS) {
				System.out.println("Time limit exceeded!");
				System.out.println("Time elapsed: " + (double)(timeElapsed / 1000000000.0) + " seconds");
				return false;
			}
		}
		return true;
	}
}
