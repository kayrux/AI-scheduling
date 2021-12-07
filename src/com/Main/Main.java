package com.Main;

import java.util.ArrayList;

import com.DataStructures.CourseLab;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.Triplet;
import com.Model.SetbasedSearch;

public class Main {
	public static void main(String[] args) {
		Parser parser = new Parser();
		SetbasedSearch setBasedSearch;


		String txtfile = "res/test6.txt"; // Example file





        ArrayList<Slot> slotArray = parser.parseCourseLabSlots(txtfile);
        ArrayList<CourseLab> courseLabArray = parser.parseCourseLab(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> notCompatibleArray = parser.parseNotCompatible(txtfile);
        ArrayList<Pair<CourseLab, Slot>> unwantedArray = parser.parseUnwanted(txtfile);
        ArrayList<Triplet<Slot, CourseLab, Integer>> preferencesArray = parser.parsePreferences(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> pairArray = parser.parsePair(txtfile);
        ArrayList<Pair<CourseLab, Slot>> partialAssignArray = parser.parsePartialAssignments(txtfile);
        
        Eval eval = new Eval(1,1,1,1);
        
        setBasedSearch = new SetbasedSearch(slotArray, courseLabArray, notCompatibleArray, unwantedArray, preferencesArray, pairArray,
        		partialAssignArray, eval);
        

        ArrayList<Slot> sol = setBasedSearch.search();

        int i = 0;
		String format = "%-25s%s%n";
		
		System.out.println("Eval: " + eval.eval(sol, slotArray, courseLabArray, preferencesArray, pairArray));
		System.out.println("----------");
		for (Slot s : sol) {
			System.out.printf(format, courseLabArray.get(i).getStringFormatted(), s.getStringFormatted());
			i++;
		}
        
		/*
		int i = 0;
		
		System.out.println("Eval: " + eval.eval(sol, slotArray, courseLabArray, preferencesArray, pairArray));
		System.out.println("----------");
		for (Slot s : sol) {
			courseLabArray.get(i).printCourse();
			s.printSlot();
			i++;
		}
		*/
	}
	

	
}
