package com.OrModel;
import java.util.ArrayList;

import com.DataStructures.CourseLab;
import com.DataStructures.EmptySlot;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.SlotType;
import com.Main.Constr;

public class Populate {
	/*
	* Creates a new Fact from nothing using an Or-Tree.
	* @param courseLabs List of all courses and labs.
	* @param slotList List of all slots.
	* @param noncompatibleArray List of noncompatible courses and labs.
	* @param unwantedArray List of unwanted course slot combinations.
	* @param partialAssign List of initial slots that we must start with.
	* @return a random fact created by the Or-Tree.
	*/
	
	public static Boolean constraintStart() {
		return true;
	}
	
	public static ArrayList<Slot> populate (ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
											ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
											ArrayList<Pair<CourseLab, Slot>> unwantedArray,
											ArrayList<Pair<CourseLab, Slot>> partialAssign) {
		
		ArrayList<Slot> fact = new ArrayList<Slot>();
		while(fact.size() < courseLabs.size()) {
			fact.add(new EmptySlot());
		}
		
		Constr constraints = new Constr();
		
		
		if(partialAssign.size() > 0) {
			Pair<CourseLab, Slot> pAssign;
			for(int i = 0; i < partialAssign.size(); i++) {
				
				pAssign = partialAssign.get(i);
				
				Slot correctSlot = slotList.get(slotList.indexOf(pAssign.getValue()));
				Slot s = new Slot(pAssign.getValue());
				s.setCoursemin(correctSlot.getCoursemin());
				s.setCourseMax(correctSlot.getCoursemax());
				s.setLabmin(correctSlot.getLabmin());
				s.setLabMax(correctSlot.getLabmax());
				
				fact.set(courseLabs.indexOf(pAssign.getKey()), s);
				
			}
		}
		
		
		for(int i = 0; i < fact.size(); i++) {
			int randSlot = (int)(Math.random() * slotList.size());
			if(fact.get(i).getSlotType() == SlotType.EMPTY) {
				fact.set(i, slotList.get(randSlot));
			}
		}
		
		// Testing
		/*for (Slot s : fact) {
			System.out.println(s.getSlotType());
			System.out.println(s.getDayAndTime());
		}*/
		
		if(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray) == false) {
			fact = new ArrayList<Slot>(populate(courseLabs, slotList, noncompatibleArray, unwantedArray, partialAssign));
		} /*else {
			System.out.println("Fact Size: " + fact.size());
			System.out.println("courseLabs Size: " + courseLabs.size());
			//System.out.println(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray));
			for(int i = 0; i < fact.size(); i++) {
				System.out.println(courseLabs.get(i).getName());
				s.printSlot();
			}
		}*/
		
		return fact;
	}
}