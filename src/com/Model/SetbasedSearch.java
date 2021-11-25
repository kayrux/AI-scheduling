package com.Model;

import java.util.ArrayList;

public class SetbasedSearch {

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
		
		startSearch();
	}
	
	private void startSearch() {
		// Populate
	}
}
