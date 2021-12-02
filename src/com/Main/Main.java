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
		SetbasedSearch search;
		ArrayList<Slot> temp = new ArrayList<Slot>();
		
		String txtfile = "res/ShortExample.txt"; // Example file

        ArrayList<Slot> slotArray = Parser.parseCourseLabSlots(txtfile);
        ArrayList<CourseLab> courseLabArray = Parser.parseCourseLab(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> notCompatibleArray = Parser.parseNotCompatible(txtfile);
        ArrayList<Pair<CourseLab, Slot>> unwantedArray = Parser.parseUnwanted(txtfile);
        ArrayList<Triplet<Slot, CourseLab, Integer>> preferencesArray = Parser.parsePreferences(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> pairArray = Parser.parsePair(txtfile);
        ArrayList<Pair<CourseLab, Slot>> partialAssignArray = Parser.parsePartialAssignments(txtfile);
        
        search = new SetbasedSearch(slotArray, courseLabArray, notCompatibleArray, unwantedArray, preferencesArray, pairArray, partialAssignArray);
		temp = Populate.populate(courseLabArray, slotArray, notCompatibleArray, unwantedArray, partialAssignArray);
	}
	

	
}
