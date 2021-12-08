package com.OrModel;
import java.util.*;

import com.DataStructures.CourseLab;
import com.DataStructures.DaySeries;
import com.DataStructures.EmptySlot;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.SlotType;
import com.Main.Constr;
import com.Model.SetbasedSearch;

/*
 * Creates a new Fact from nothing using an Or-Tree.
 * @param courseLabs List of all courses and labs.
 * @param slotList List of all slots.
 * @param noncompatibleArray List of noncompatible courses and labs.
 * @param unwantedArray List of unwanted course slot combinations.
 * @param partialAssign List of initial slots that we must start with.
 * @return a random fact created by the Or-Tree.
 */
public class Populate {

	public static ArrayList<Slot> populate (ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
											ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
											ArrayList<Pair<CourseLab, Slot>> unwantedArray,
											ArrayList<Pair<CourseLab, Slot>> partialAssign, int numIterations) {

		//If max iterations pass and no solution has been found, we assume there is no solution.
		if (numIterations > SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
		{
			System.out.println("Impossible to populate");
			System.exit(0);
		}

		Constr constraints = new Constr();

		//Creating base fact template using partassign.

		//Initializing fact to expected size with empty slots.
		ArrayList<Slot> partAssignFact = new ArrayList<>();
		while(partAssignFact.size() < courseLabs.size())
		{
			partAssignFact.add(new EmptySlot());
		}

		//Replacing slots according to partassign.
		if(partialAssign.size() > 0)
		{
			Pair<CourseLab, Slot> pAssign;
			for (Pair<CourseLab, Slot> courseLabSlotPair : partialAssign)
			{
				pAssign = courseLabSlotPair;
				//Catches impossible partassignments.
				if (!slotList.contains(pAssign.getValue()))
				{
					System.out.println("Invalid Partial assignment");
					System.exit(0);
				}

				Slot correctSlot = slotList.get(slotList.indexOf(pAssign.getValue()));
				Slot s = new Slot(pAssign.getValue());
				s.setCoursemin(correctSlot.getCoursemin());
				s.setCourseMax(correctSlot.getCoursemax());
				s.setLabmin(correctSlot.getLabmin());
				s.setLabMax(correctSlot.getLabmax());

				//Catches problems related to 813/913
				if (pAssign.getKey().getName().equals("CPSC813") || pAssign.getKey().getName().equals("CPSC913"))
				{
					if (!(pAssign.getValue().getDaySeries().equals(DaySeries.TU) && pAssign.getValue().getTime().getHours() == 18))
					{
						System.out.println("Partial assignment contradicts hard constraint");
						System.exit(0);
					}
				}
				else
				{
					partAssignFact.set(courseLabs.indexOf(pAssign.getKey()), s);
				}
			}
		}

		//Final fact to be returned.
		ArrayList<Slot> fact = new ArrayList<>(partAssignFact);

		//Second list of all slots; is shuffled to simulate randomness without repeats.
		ArrayList<Integer> slotsToTry = new ArrayList<>(slotList.size());
		for(int i = 0; i < slotList.size(); i++)
		{
			slotsToTry.add(i);
		}
		Collections.shuffle(slotsToTry);
		int randIndex = 0;

		//Used for tracking populate progress.
		//int furthest = 0;
		//int backtracks = 0;

		//A mirror of fact used to test slots.
		ArrayList<Slot> constrFact = new ArrayList<>(fact);

		//Works on each courselab in order.
		for(int i = 0; i < fact.size(); i++)
		{
			//If fact is already filled, then partassignment must exist for this slot.
			if(partAssignFact.get(i).getSlotType() == SlotType.EMPTY)
			{
				while(true)
				{
					//If all slots have been tried, backtrack.
					if(randIndex == slotsToTry.size())
					{
						//Used for tracking population progress.
						//if(i > furthest)
						//{
						//	System.out.println("Reached " + i + "/" + courseLabs.size());
						//	System.out.println("Tried " + backtracks + " times.");
						//	furthest = i;
						//}

						//Performs backtracking by setting last assigned courselab to partassign default
						//and reassigning some valid slot to that courselab.
						//If partassign for that courselab is not empty, then this backtrack will
						//not actually change anything since there is only one option.
						//So we continue backtracking until we find an empty slot.
						Slot previous;
						do {
							previous = partAssignFact.get(i);
							fact.set(i, previous);
							constrFact.set(i, previous);
							i--;
						} while (previous.getSlotType() != SlotType.EMPTY);

						//Used for tracking populate progress.
						//backtracks++;
						//if(backtracks % 50 == 0)
						//{
						//	System.out.println("Tried " + backtracks + " times.");
						//}

						//Reshuffles and resets to start slots being tried for randomness.
						Collections.shuffle(slotsToTry);
						randIndex = 0;
						break;
					}

					//The next random slot to try is simply the slot at randIndex.
					//We then increment randIndex and thus will not repeat random numbers.
					int randSlot = slotsToTry.get(randIndex);
					randIndex++;

					//Tests the slot randomly chosen.
					constrFact.set(i, slotList.get(randSlot));
					if(constraints.constr(constrFact, slotList, courseLabs, noncompatibleArray, unwantedArray, partialAssign, numIterations))
					{
						//If it works, then fact is updated and fact once again matches constrFact.
						fact.set(i, constrFact.get(i));
						break;
					}
					else
					{
						//If constraints are broken, then constrFact is rolled back and once again matches fact.
						constrFact.set(i, fact.get(i));
					}
				}

			}
		}
		return fact;
	}
}