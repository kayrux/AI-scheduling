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
import com.OrModel.*;

public class Main {
	public static void main(String[] args) {
		Parser parser = new Parser();
<<<<<<< HEAD
		SetbasedSearch setBasedSearch;
		
		String txtfile = "res/ShortExample.txt"; // Example file

        ArrayList<Slot> slotArray = parser.parseCourseLabSlots(txtfile);
        ArrayList<CourseLab> courseLabArray = parser.parseCourseLab(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> notCompatibleArray = parser.parseNotCompatible(txtfile);
        ArrayList<Pair<CourseLab, Slot>> unwantedArray = parser.parseUnwanted(txtfile);
        ArrayList<Triplet<Slot, CourseLab, Integer>> preferencesArray = parser.parsePreferences(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> pairArray = parser.parsePair(txtfile);
        ArrayList<Pair<CourseLab, Slot>> partialAssignArray = parser.parsePartialAssignments(txtfile);
        
        setBasedSearch = new SetbasedSearch(slotArray, courseLabArray, notCompatibleArray, unwantedArray, preferencesArray, pairArray,
        		partialAssignArray);
        
        ArrayList<Slot> sol = setBasedSearch.search();
        
=======
		SetbasedSearch search;
		ArrayList<Slot> temp;
		
		String txtfile = "res/ShortExample.txt"; // Example file

        ArrayList<Slot> slotArray = Parser.parseCourseLabSlots(txtfile);
        ArrayList<CourseLab> courseLabArray = Parser.parseCourseLab(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> notCompatibleArray = Parser.parseNotCompatible(txtfile);
        ArrayList<Pair<CourseLab, Slot>> unwantedArray = Parser.parseUnwanted(txtfile);
        ArrayList<Triplet<Slot, CourseLab, Integer>> preferencesArray = Parser.parsePreferences(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> pairArray = Parser.parsePair(txtfile);
        ArrayList<Pair<CourseLab, Slot>> partialAssignArray = Parser.parsePartialAssignments(txtfile);
        
        search = new SetbasedSearch(slotArray, courseLabArray, notCompatibleArray, unwantedArray, preferencesArray, pairArray, partialAssignArray);
		temp = Populate.PopulateOrTree(courseLabArray, slotArray, notCompatibleArray, unwantedArray, partialAssignArray);
		//for(int i = 0; i < temp.size(); i++) {
		//	System.out.print();
		//}
>>>>>>> f3fca527a8750e11f572f85cf1ff8345119e92ae
	}
	

	
}
