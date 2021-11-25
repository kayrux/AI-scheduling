package com.Main;

import java.util.ArrayList;

import com.Model.CourseLab;
import com.Model.Pair;
import com.Model.SetbasedSearch;
import com.Model.Slot;
import com.Model.Triplet;

public class Main {
	
	public static void main(String[] args) {
		Parser parser = new Parser();
		SetbasedSearch search;
		
		String txtfile = "./com/Main/ShortExample.txt"; // Example file

        ArrayList<Slot> slotArray = parser.parseCourseLabSlots(txtfile);
        ArrayList<CourseLab> courseLabArray = parser.parseCourseLab(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> notCompatibleArray = parser.parseNotCompatible(txtfile);
        ArrayList<Pair<CourseLab, Slot>> unwantedArray = parser.parseUnwanted(txtfile);
        ArrayList<Triplet<Slot, CourseLab, Integer>> preferencesArray = parser.parsePreferences(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> pairArray = parser.parsePair(txtfile);
        ArrayList<Pair<CourseLab, Slot>> partialAssignArray = parser.parsePartialAssignments(txtfile);
        
        search = new SetbasedSearch(slotArray, courseLabArray, notCompatibleArray, unwantedArray, preferencesArray, pairArray,
        		partialAssignArray);
	}
	

	
}
