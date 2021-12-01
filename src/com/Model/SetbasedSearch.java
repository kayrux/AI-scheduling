package com.Model;

import java.util.ArrayList;
import java.util.Random;

import com.DataStructures.CourseLab;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.Triplet;
import com.Main.Eval;

public class SetbasedSearch {

	// PREDIFINED VARIABLES
	private final int MAX_EVAL = Integer.MAX_VALUE;
	private final int MAX_ITERATIONS_NO_IMPROVEMENT = 15;
	private final long TIME_LIMIT_SECONDS = 2;
	private final boolean USE_TIME_LIMIT = true;
	private final int MAX_POP_SIZE = 30;
	
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
            ArrayList<Pair<CourseLab, Slot>> partialAssignList) {
		
		facts = new ArrayList<ArrayList<Slot>>();
		currentEvals = new ArrayList<Integer>();
		this.slotsArray = slotsArray;
		this.courseLabArray = courseLabArray;
		this.notCompatibleArray = notCompatibleArray;
		this.unwantedArray = unwantedArray;
		this.prefArray = prefArray;
		this.partialAssignList = partialAssignList;
		this.pairArray = pairArray;
		
		this.eval = new Eval(1,1,1,1);	// Set weights for Eval
		this.rand = new Random();
		
	}
	
	
	public ArrayList<Slot> search() {
		long startTime = System.nanoTime();
		long currentTime = startTime;
		
		int noImprovementCounter = 0;
		int lowestEval = MAX_EVAL;
		int currentEval = lowestEval;
		
		// Populate
		// facts = populate()
		
		//Loop (time based and/or based on number of iterations passed without improvement)
		
		while((noImprovementCounter < MAX_ITERATIONS_NO_IMPROVEMENT) && withinTimeLimit(startTime, currentTime)) {
			// update
			updateCurrentEvals();
			decay();
			
			// Choose A = {fact1, fact2} to pass to Crossover
			ArrayList<Slot> fact1 = this.getFactWithLowestEval(facts);
			
			
			// Crossover(A)
			
			
			
			
			//TESTING
			currentEval --;
			//END TESTING
			
			// Updates the lowest evaluation and the counter for no. iterations without improvement
			if (currentEval < lowestEval) {
				noImprovementCounter = 0;	// Reset the counter if a better Eval is found
				lowestEval = currentEval;	// Update the lowest evaluation
			} else noImprovementCounter ++;	
			
			currentTime = System.nanoTime();	// Update current time
		}
		
			
		
		
		// Pick best fact based on Eval
		
		return getFactWithLowestEval(facts);
	}

	/**
	 * Update cached evals for current facts
	 */
	void updateCurrentEvals() {
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
			for (int i = 0; i < currentEvals.size(); i += 1) {
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
	private int generateRandomNumber(int min, int max) {
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
