package com.OrModel;

import com.DataStructures.CourseLab;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.SlotType;
import com.Main.Constr;

import java.util.ArrayList;
import java.util.Random;

/**
 * New fact creator using crossover method.
 *
 * @author Jhy-An Chen (30071972)
 */
public class Crossover {

	/**
	 * Creates a new fact from a crossover of two facts.
	 * @param courseLabs List of all courses and labs.
	 * @param slots List of all slots.
	 * @param noncompatibleArray List of noncompatible courses and labs.
	 * @param unwantedArray List of unwanted course slot combinations.
	 * @param A The first fact to be used.
	 * @param B The second fact to be used.
	 * @return A fact created by the crossover of A and B.
	 */
	public static ArrayList<Slot> crossover(ArrayList<CourseLab> courseLabs, ArrayList<Slot> slots,
											ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
											ArrayList<Pair<CourseLab, Slot>> unwantedArray,
											ArrayList<Slot> A, ArrayList<Slot> B) {
		Constr constraints = new Constr();
		Random rand = new Random();
		ArrayList<Slot> C = new ArrayList<>();

		//Runs until the new fact is the proper size.
		while(C.size() < courseLabs.size())
		{
			ArrayList<Integer> possibleSlots = new ArrayList<>();
			//Checks each fact to see if C would pass the constraints if the next courselab in order uses it.
			//If so, it is added as a possibility.
			for(int i = 0; i < slots.size(); i++)
			{
				ArrayList<Slot> constrC = new ArrayList<>(C);
				
				// Fails test15.txt (courseLabs.size() > slots.size(), so index out of bound error)
				if (slots.get(C.size()).getSlotType() == slots.get(i).getSlotType()) {
					if (slots.get(i).getSlotType() == SlotType.LAB) System.out.println("---------LAB---------");
					constrC.add(slots.get(i));
					if(constraints.constr(constrC, slots, courseLabs, noncompatibleArray, unwantedArray))
					{
						 
						possibleSlots.add(i);
					}
				}
						
			}

			//Pre-generates information.
			//Note that C.size() would naturally be the index we are considering since
			//we are adding a new object to the end.
			Integer ASlot = slots.indexOf(A.get(C.size()));
			Integer BSlot = slots.indexOf(B.get(C.size()));
			Boolean containsASlot = possibleSlots.contains(ASlot);
			Boolean containsBSlot = possibleSlots.contains(BSlot);

			//Changes possible slots to only consider slots used by parents at the same index if possible.
			if(rand.nextInt(10) > 1)
			{
				if(containsASlot && containsBSlot)
				{
					possibleSlots.clear();
					possibleSlots.add(ASlot);
					possibleSlots.add(BSlot);
				}
				else if(containsASlot)
				{
					possibleSlots.clear();
					possibleSlots.add(ASlot);
				}
				else if(containsBSlot)
				{
					possibleSlots.clear();
					possibleSlots.add(BSlot);
				}
			}

			//If there are no possible slots, backtrack.
			//Note in worst case scenario, this will eventually return a copy of one of
			//the parents and thus will not infinitely loop.
			if(possibleSlots.size() != 0)
			{
				C.add(slots.get(possibleSlots.get(rand.nextInt(possibleSlots.size()))));
			}
			else
			{
				C.clear();
			}
		}

		return C;
	}
	
}
