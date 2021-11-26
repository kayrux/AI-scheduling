package com.Main;

import java.util.ArrayList;

import com.Constants.ValidTimeSlots;
import com.DataStructures.CourseLab;
import com.DataStructures.EmptySlot;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.SlotArrayListWrapper;
import com.DataStructures.Triplet;
import com.Model.SetbasedSearch;

public class Main {
	
	public static void main(String[] args) {
		Parser parser = new Parser();
		SetbasedSearch search;
		
		String txtfile = "res/ShortExample.txt"; // Example file

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
