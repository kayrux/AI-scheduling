package com.OrModel;
import java.util.ArrayList;
import com.DataStructures.*;
import com.Main.Constr;

class Populate {
	
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

	public static ArrayList<Slot> PopulateOrTree (ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
											ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
											ArrayList<Pair<CourseLab, Slot>> unwantedArray
											ArrayList<Pai<CourseLab, Slot>> initialSlot) {
		if(initialSlot.size() == 0) {
			ArrayList<Slot> fact = new ArrayList<Slot>();
		} else {
			ArrayList<Slot> fact = new ArrayList<Slot>(initialSlot);
		}
		
		int randSlot = (int)(Math.random() * slotList.size());
		NodeTemp curNode = new NodeTemp(slotList.get(randSlot), null);
		NodeTemp root = new NodeTemp(curNode.value, null);
		int child = 0;
		while(fact.size() < courseLab.size()) {
			randSlot = (int)(Math.random() * slotList.size());
			curNode.addChild(slotList.get(randSlot));
			child++;
			if(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray)) {
				fact.add(slotList.get(randSlot));
				curNode = new NodeTemp(curNode.children.get(child).value, curNode.value);
			} else {
				curNode.backTrack();
				if(child > curNode.children.size()) {
					curNode.backTrack();
					child = curNode.children.size();
				}
			}
			
		}
		return fact;
	}
}
