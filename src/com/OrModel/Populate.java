package com.OrModel;
import java.util.ArrayList;
import com.DataStructures.*;
import com.Main.*;

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

	public static ArrayList<Slot> PopulateOrTree (ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
											ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
											ArrayList<Pair<CourseLab, Slot>> unwantedArray,
											ArrayList<Pair<CourseLab, Slot>> initialSlot) {
		ArrayList<Slot> fact;
		Constr constraints = new Constr();
		if(initialSlot.size() == 0) {
			fact = new ArrayList<Slot>();
		} else {
			ArrayList<Slot> temp;
			fact = new ArrayList<Slot>();
			for(int i = 0; i < initialSlot.size(); i++) {
				fact.add(initialSlot.get(i).getValue());
			}
		}
		int randSlot = (int)(Math.random() * slotList.size());
		NodeTemp curNode = new NodeTemp(slotList.get(randSlot), null);
		NodeTemp root = new NodeTemp(curNode.value, null);
		int child = 0;
		while(fact.size() < courseLabs.size()) {
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
