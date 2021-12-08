package com.OrModel;
import java.util.ArrayList;

import com.DataStructures.CourseLab;
import com.DataStructures.DaySeries;
import com.DataStructures.EmptySlot;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.SlotType;
import com.Main.Constr;
import com.Model.SetbasedSearch;

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

	public static ArrayList<Slot> populate(ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
			ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
			ArrayList<Pair<CourseLab, Slot>> unwantedArray,
			ArrayList<Pair<CourseLab, Slot>> partialAssign, int numIterations){
		
		Constr constraints = new Constr();

		ArrayList<Slot> fact = populate2(courseLabs, slotList, noncompatibleArray, unwantedArray, partialAssign, numIterations);
		while(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray, partialAssign, numIterations) == false) {
			numIterations++;
			System.out.println("Still here");
			fact = new ArrayList<Slot>(populate2(courseLabs, slotList, noncompatibleArray, unwantedArray, partialAssign, numIterations));
		}
		
		return fact;
	}
	
	public static ArrayList<Slot> populate2 (ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
											ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
											ArrayList<Pair<CourseLab, Slot>> unwantedArray,
											ArrayList<Pair<CourseLab, Slot>> partialAssign, int numIterations) {
		
		if (numIterations > SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT) 
		{
			//System.out.println("Impossible to populate");
			System.exit(0);
			
		}
		
		ArrayList<Slot> fact = new ArrayList<Slot>();
		ArrayList<Slot> factTest = new ArrayList<Slot>();
		while(fact.size() < courseLabs.size()) {
			fact.add(new EmptySlot());
		}
		
		Constr constraints = new Constr();
		
		
		if(partialAssign.size() > 0) {
			Pair<CourseLab, Slot> pAssign;
			for(int i = 0; i < partialAssign.size(); i++) {
				
				pAssign = partialAssign.get(i);
				if (slotList.indexOf(pAssign.getValue()) == -1) {
					System.out.println("Invalid Partial assignment");
					System.exit(0);
				}
				
				Slot correctSlot = slotList.get(slotList.indexOf(pAssign.getValue()));
				Slot s = new Slot(pAssign.getValue());
				s.setCoursemin(correctSlot.getCoursemin());
				s.setCourseMax(correctSlot.getCoursemax());
				s.setLabmin(correctSlot.getLabmin());
				s.setLabMax(correctSlot.getLabmax());
				
				if (pAssign.getKey().getName().equals("CPSC813") || pAssign.getKey().getName().equals("CPSC913"))
				{
					if (!(pAssign.getValue().getDaySeries().equals(DaySeries.TU) && pAssign.getValue().getTime().getHours() == 18) )
					{
						System.out.println("Partial assignment contradicts hard constraint");
						System.exit(0);
					}  
				}  else 
				{
				
				fact.set(courseLabs.indexOf(pAssign.getKey()), s);
				}
				
			}
		}
		
		for(int i = 0; i < fact.size(); i++) {
			int repeat = 1;

			ArrayList<Slot> possibleTimes= new ArrayList<Slot>();

			if(fact.get(i).getSlotType() == SlotType.EMPTY){
				
				for (Slot s : slotList){

					factTest.add(s);

					if (constraints.constr(factTest, slotList, courseLabs, noncompatibleArray, 
						unwantedArray, partialAssign, numIterations)){
						possibleTimes.add(s);
					}

					factTest.remove(s);
				}

				int randSlot = (int) (Math.random() * possibleTimes.size());

				if (possibleTimes.size() == repeat-1){
					if (i == 0){
						System.exit(0);
					}
					i--;
					repeat++;
					factTest.remove(factTest.size()-1);
					System.out.println("Currently: " + i + "/" + courseLabs.size());
				} else {
					repeat = 1;
					System.out.println("Fake Test: " + constraints.constr(factTest, slotList, courseLabs, 
						noncompatibleArray, unwantedArray, partialAssign, numIterations));
					System.out.println("Reached: " + i + "/" + courseLabs.size());
					factTest.add(possibleTimes.get(randSlot));
				}
			} else {
				factTest.add(fact.get(i));
			}
		}

		// for (int i = 0; i < factTest.size(); i ++){
		// 	fact.set(i, factTest.get(i));
		// 	System.out.println("Fake Test: " + constraints.constr(factTest, slotList, courseLabs, noncompatibleArray, unwantedArray, partialAssign, numIterations));
		// 	System.out.println("Real Test: " + constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray, partialAssign, numIterations));

		// }
		
		// Testing
		/*for (Slot s : fact) {
			System.out.println(s.getSlotType());
			System.out.println(s.getDayAndTime());
		}*/
		// if(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray, partialAssign, numIterations) == false) {
		// 	numIterations++;
		// 	fact = new ArrayList<Slot>(populate(courseLabs, slotList, noncompatibleArray, unwantedArray, partialAssign, numIterations));
		// }   
		
		/*else {
		}
			System.out.println("Fact Size: " + fact.size());
			System.out.println("courseLabs Size: " + courseLabs.size());
			//System.out.println(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray));
			for(int i = 0; i < fact.size(); i++) {
				System.out.println(courseLabs.get(i).getName());
				s.printSlot();
			}
		}*/
		
		return factTest;
	}
}