package com.Main;

import java.util.ArrayList;

import com.DataStructures.CourseLab;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.Triplet;
import com.Model.SetbasedSearch;
import com.OrModel.Populate;

public class Main {
	public static void main(String[] args) {
		Parser parser = new Parser();
		SetbasedSearch setBasedSearch;

		
		String txtfile = "res/test29.txt"; // Example file

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

		//sol = Populate.populate(courseLabArray, slotArray, notCompatibleArray, unwantedArray, partialAssignArray);
		
		int i = 0;
		Eval eval = new Eval(1,1,1,1);
		System.out.println("Eval: " + eval.eval(sol, slotArray, courseLabArray, preferencesArray, pairArray));
		System.out.println("----------");
		for (Slot s : sol) {
			courseLabArray.get(i).printCourse();
			s.printSlot();
			i++;
		}
	}
	

	
}
