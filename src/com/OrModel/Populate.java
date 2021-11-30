package com.OrModel;
import java.util.ArrayList;
import com.DataStructures.*;

class Populate {
	
	public static Boolean constraintStart() {
		return true;
	}

	public static void PopulateOrTree (ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
											ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
											ArrayList<Pair<CourseLab, Slot>> unwantedArray) {
		ArrayList<Slot> fact = new ArrayList<Slot>();
		int randSlot = (int)(Math.random() * slotList.size());
		NodeTemp curNode = new NodeTemp(slotList.get(randSlot), null);
		NodeTemp root = new NodeTemp(curNode.value, null);
		int child = 0;
		while(true) {
			randSlot = (int)(Math.random() * slotList.size());
			curNode.addChild(slotList.get(randSlot));
			child++;
			if(constraintStart() == true) {
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
	}
}
