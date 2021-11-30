package com.Model;

import java.util.ArrayList;

import com.DataStructures.CourseLab;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.Triplet;
import com.Main.Eval;

public class SetbasedSearch {

	private final int MAX_ITERATIONS_NO_IMPROVEMENT = 10;
	private final int TIME_LIMIT_SECONDS = 300;
	private final boolean USE_TIME_LIMIT = false;
	
	private Eval eval;
	private ArrayList<ArrayList<Slot>> facts;
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
		this.slotsArray = slotsArray;
		this.courseLabArray = courseLabArray;
		this.notCompatibleArray = notCompatibleArray;
		this.unwantedArray = unwantedArray;
		this.prefArray = prefArray;
		this.partialAssignList = partialAssignList;
		this.pairArray = pairArray;
		
		this.eval = new Eval(1,1,1,1);	// Set weights for Eval
	}
	
	
	public ArrayList<Slot> search() {
		long startTime = System.nanoTime();
		long currentTime = startTime;
		
		int noImprovementCounter = 0;
		int lowestEval = 1000000000;
		int currentEval = lowestEval;
		
		// Populate
		
		//Loop (time based and/or based on number of iterations passed without improvement)
		/*
		while((noImprovementCounter < MAX_ITERATIONS_NO_IMPROVEMENT) && withinTimeLimit(startTime, currentTime)) {
			 
			// Check if Decay
			
			// Choose A = {fact1, fact2} to pass to Crossover
			// Crossover(A)
			
			
			
			
			// Updates the lowest evaluation and the counter for no. iterations without improvement
			if (currentEval < lowestEval) {
				noImprovementCounter = 0;	// Reset the counter if a better Eval is found
				lowestEval = currentEval;	// Update the lowest evaluation
			} else noImprovementCounter ++;	
			
			currentTime = System.nanoTime();	// Update current time
		}*/
		
			
		
		
		// Pick best fact based on Eval
		
		return null;
	}
	
	private ArrayList<Slot> getFactWithLowestEval(ArrayList<ArrayList<Slot>> facts) {
		for (ArrayList<Slot> fact : facts) {
			
		}
		return null;
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
			if ((timeElapsed / 1000000000) > TIME_LIMIT_SECONDS) return false;
		}
		return true;
	}
}
