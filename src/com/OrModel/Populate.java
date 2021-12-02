package com.OrModel;
import java.util.ArrayList;

import com.DataStructures.CourseLab;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.Main.Constr;

public class Populate {
	/*
	* Creates a new Fact from nothing using an Or-Tree.
	* @param courseLabs List of all courses and labs.
	* @param slotList List of all slots.
	* @param noncompatibleArray List of noncompatible courses and labs.
	* @param unwantedArray List of unwanted course slot combinations.
	* @param initialSlot List of initial slots that we must start with.
	* @return a random fact created by the Or-Tree.
	*/
	
	public static Boolean constraintStart() {
		return true;
	}
	
	public static ArrayList<Slot> populate (ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
											ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
											ArrayList<Pair<CourseLab, Slot>> unwantedArray,
											ArrayList<Pair<CourseLab, Slot>> partialAssign) {
		
		ArrayList<Slot> fact;
		Constr constraints = new Constr();
		if(partialAssign.size() == 0) {
			fact = new ArrayList<Slot>();
		} else {
			fact = new ArrayList<Slot>();
			for(int i = 0; i < partialAssign.size(); i++) {
				fact.add(partialAssign.get(i).getValue());
			}
		}

		while(fact.size() < courseLabs.size()) {
			int randSlot = (int)(Math.random() * slotList.size());
			fact.add(slotList.get(randSlot));
		}
		if(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray)) {
			fact = new ArrayList<Slot>(populate(courseLabs, slotList, noncompatibleArray, unwantedArray, partialAssign));
		}
		
		return fact;
	}
}