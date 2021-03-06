package com.Main;

import com.DataStructures.CourseLab;
import com.DataStructures.DaySeries;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.SlotType;
import com.DataStructures.Time;
import com.Model.*;

import java.util.*;


public class Constr {

	public Constr(){

	}

	public boolean constr(ArrayList<Slot> factsArray, ArrayList<Slot> slotsArray, ArrayList<CourseLab> courseLabs,
			ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray, ArrayList<Pair<CourseLab, Slot>> unwantedArray, ArrayList<Pair<CourseLab, Slot>> partAssign, int iterations)
	{
//		System.out.println("maxTimes: " + maxTimes(factsArray, slotsArray, courseLabs, iterations));
//		System.out.println("diffTimes: " + differentTimes(factsArray,courseLabs, iterations));
//		System.out.println("noncompat: " + noncompatibleClasses(factsArray, courseLabs, noncompatibleArray, iterations));
//		System.out.println("unwanted: " + unwantedTimes(factsArray, courseLabs, unwantedArray, iterations));
//		System.out.println("eveningCourses: " + eveningCourses(factsArray, courseLabs, iterations));
//		System.out.println("Five hundred: " + fiveHunderedLevelCourses(factsArray, courseLabs, iterations));
//		System.out.println(noCourseScheduled(factsArray, iterations));
//		System.out.println(partAssign(factsArray, courseLabs, partAssign, iterations));
		
		




		return maxTimes(factsArray, slotsArray, courseLabs, iterations) && differentTimes(factsArray, courseLabs, iterations)
				&& noncompatibleClasses(factsArray, courseLabs, noncompatibleArray, iterations) 
				&& unwantedTimes(factsArray, courseLabs, unwantedArray, iterations) && eveningCourses(factsArray, courseLabs, iterations)
				&& fiveHunderedLevelCourses(factsArray, courseLabs, iterations) && noCourseScheduled(factsArray, iterations)
				&& partAssign(factsArray, courseLabs, partAssign, iterations) && check813And313DifferentTimes(factsArray, courseLabs, noncompatibleArray, iterations) 
				&& check913And413DifferentTimes(factsArray,courseLabs, noncompatibleArray, iterations);

	}
	

	private static boolean check813And313DifferentTimes(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs,
			ArrayList<Pair<CourseLab, CourseLab>> noncompatible, int iterations){

		for (Slot s : factsArray) {
			if (s.getSlotType() == SlotType.EMPTY) {
				continue;
			}
			if (courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC313")) {

				//if (s.getDayAndTime() == "TU 18:00"  || s.getDayAndTime() == "TU 17:00" || s.getDayAndTime() == "TU 18:30"){
				if (s.getDaySeries().equals(DaySeries.TU) && ((s.getTime().getHours() == 18) || (s.getTime().getHours() == 17 && s.getSlotType() == SlotType.COURSE))) {
					if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
					{
						System.out.println("Impossible because CPSC 313 must be assigned to a Time slot that would overlap with CPSC 813");
						
					}
					return false;
				}


				for (Pair<CourseLab, CourseLab> nc : noncompatible) {

					if (nc.getKey().getName().equals("CPSC313")  || nc.getValue().getName().equals("CPSC313")){
						Slot temp = factsArray.get(courseLabs.indexOf(nc.getKey()));
						Slot value = factsArray.get(courseLabs.indexOf(nc.getValue()));
						boolean keyCheck = temp.getDaySeries().equals(DaySeries.TU) && ((temp.getTime().getHours() == 18) || (temp.getTime().getHours() == 17 && temp.getSlotType() == SlotType.COURSE));
						boolean valueCheck = value.getDaySeries().equals(DaySeries.TU) && ((value.getTime().getHours() == 18) || (value.getTime().getHours() == 17 && value.getSlotType() == SlotType.COURSE));

						//if (temp.getDaySeries().equals(DaySeries.TU) && ((temp.getTime().getHours() == 18) || (temp.getTime().getHours() == 17 && temp.getSlotType() == SlotType.COURSE))) {
						if (keyCheck || valueCheck) { 
							if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
							{
								System.out.println("Impossible because one of the courses CPSC 313 in non-compatible must be assigned to a Time Slot that overlaps with overlaps with CPSC 813");
							}


							//if (temp.getDaySeries() == DaySeries.TU && ())

							//if (factsArray.get(courseLabs.indexOf(nc.getKey())).getDayAndTime() == "TU 18:00" ||
							//factsArray.get(courseLabs.indexOf(nc.getKey())).getDayAndTime() == "TU 17:00"
							//|| factsArray.get(courseLabs.indexOf(nc.getKey())).getDayAndTime() == "TU 18:30"){
							return false;
						}
					}
				}
			}
		}
		return true;

	}

	private static boolean check913And413DifferentTimes(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs,
			ArrayList<Pair<CourseLab, CourseLab>> noncompatible, int iterations){

		for (Slot s : factsArray){
			if (s.getSlotType() == SlotType.EMPTY)
			{
				continue;
			}
			if (courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC413")){

				//if (s.getDayAndTime() == "TU 18:00"  || s.getDayAndTime() == "TU 17:00" || s.getDayAndTime() == "TU 18:30"){
				if (s.getDaySeries().equals(DaySeries.TU) && ((s.getTime().getHours() == 18) || (s.getTime().getHours() == 17 && s.getSlotType() == SlotType.COURSE))) {
					if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
					{
						System.out.println("Impossible because CPSC 413 must be assigned to a Time slot that would overlap with CPSC 913");
						
					}
					return false;
				}


				for (Pair<CourseLab, CourseLab> nc : noncompatible){

					if (nc.getKey().getName().equals("CPSC413")  || nc.getValue().getName().equals("CPSC413")){
						Slot temp = factsArray.get(courseLabs.indexOf(nc.getKey()));
						Slot value = factsArray.get(courseLabs.indexOf(nc.getValue()));
						boolean keyCheck = temp.getDaySeries().equals(DaySeries.TU) && ((temp.getTime().getHours() == 18) || (temp.getTime().getHours() == 17 && temp.getSlotType() == SlotType.COURSE));
						boolean valueCheck = value.getDaySeries().equals(DaySeries.TU) && ((value.getTime().getHours() == 18) || (value.getTime().getHours() == 17 && value.getSlotType() == SlotType.COURSE));

						//if (temp.getDaySeries().equals(DaySeries.TU) && ((temp.getTime().getHours() == 18) || (temp.getTime().getHours() == 17 && temp.getSlotType() == SlotType.COURSE))) {
						if (keyCheck || valueCheck) { 
							if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
							{
								System.out.println("Impossible because one of the courses CPSC 413 in non-compatible must be assigned to a Time Slot that overlaps with overlaps with CPSC 913");
							}


							//if (temp.getDaySeries() == DaySeries.TU && ())

							//if (factsArray.get(courseLabs.indexOf(nc.getKey())).getDayAndTime() == "TU 18:00" ||
							//factsArray.get(courseLabs.indexOf(nc.getKey())).getDayAndTime() == "TU 17:00"
							//|| factsArray.get(courseLabs.indexOf(nc.getKey())).getDayAndTime() == "TU 18:30"){
							return false;
						}
					}
				}
			}
		}
		return true;

	}

	// Ensures that the partial assignmnt of the courses stay the same
	private static boolean partAssign(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs,
			ArrayList<Pair<CourseLab, Slot>> partAssigned, int iterations){

		for(Slot s : factsArray){
			if (s.getSlotType() == SlotType.EMPTY) {
				continue;
			}

			for (Pair<CourseLab, Slot> p : partAssigned){

				if (courseLabs.get(factsArray.indexOf(s)).getName().equals(p.getKey().getName())){

					if (s.getTime().getHours() != p.getValue().getTime().getHours() 
							|| s.getDaySeries() != p.getValue().getDaySeries()){
						
						if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
						{
							System.out.println("Impossible to create a valid answer that follows all the provided partial assignments");
						}
						return false;
					}
				}
			}
		}
		return true;
	}

	//Max course and Max Labs per time-slot
	private static boolean maxTimes(ArrayList<Slot> factsArray, ArrayList<Slot> slotsArray, ArrayList<CourseLab> courseLabArray, int iterations)
	{
		for(Slot s : slotsArray)
		{
			//This new Version causes errors as almost every test ends up Impossible to populate.
			//        	int courseMax = s.getCoursemax();
			//        	int labMax = s.getLabmax();
			//        	int courseCount = 0;
			//        	int labCount = 0;
			//        	
			//        	for(int i = 0; i < factsArray.size(); i++)
			//        	{
			//        		if(courseLabArray.get(i).getType().equals("COURSE"))
			//        		{
			//        			courseCount++;
			//        		}
			//        		else
			//        		{
			//        			labCount++;
			//        		}
			//        	}
			//        	if(courseCount > courseMax || labCount > labMax)
			//        	{
			//        		return false;
			//        	}
			// Get the max number of Courses or Labs for each time slot
			int max = s.getCoursemax();
			if(max == 0)
			{
				max = s.getLabmax();
			}
			//int labMax = s.getLabmax();
			// Checks how often that specific time slot appears in the facts array
			//System.out.print(Collections.frequency(factsArray, s));
			//System.out.println(" " + max);
			if(Collections.frequency(factsArray, s) > max)
			{
				
				if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
				{
					System.out.println("Impossible to populate because the number of provided course/lab slots are less than the number of provided courses/labs");
				}
				return false;
			}
		}

		return true;
	}

	// Courses must be differnt times than their labs/tutorials
	private static boolean differentTimes(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs, int iterations){

		for (Slot s : factsArray){
			if (s.getSlotType() == SlotType.EMPTY)
			{
				continue;
			}

			// Checks if the current s is a course
			if (s.getSlotType() == SlotType.COURSE){

				for (Slot c : factsArray) {
					//Testing
					//System.out.println(c.getDayAndTime());
					if (c.getSlotType() == SlotType.EMPTY) 
					{
						continue;
						
					}

					//System.out.println(factsArray.indexOf(c));
					//System.out.println(factsArray.size());
					//System.out.println(courseLabs.size());
					CourseLab cl = courseLabs.get(factsArray.indexOf(c));

					//System.out.println(s.getDayAndTime());

					String courseName = courseLabs.get(factsArray.indexOf(s)).getName();
					Time courseTime = s.getTime();

					/*if (factsArray.indexOf(c) == -1) {
                    	System.out.println("Error");
                    }*/

					if (courseName.equals(cl.getName()))
					{
						if (c.getSlotType() == SlotType.LAB)
						{
							if (c.getTime().getHours() == s.getTime().getHours())
							{
								if (c.getDaySeries() == s.getDaySeries()) {
									if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
									{
										System.out.println("Impossible to populate because the course must be at a time which overlaps with one of its labs/tutorials");
									}
									return false;
								}
							}
						}

					}

					// Checks if the name of the course is the same as s AND c is a lab/tutorial
					// if (courseLabs.get(factsArray.indexOf(c)) == (cl) && c.getSlotType() == SlotType.LAB){

					//     // Checks if the time of the lab/tutorial is the same as the courseTime
					//     if (c.getTime() ==   (courseTime)){
					//         return false;
					//     }
					// }
				}

			}
		}

		return true;
	}
	
	public static boolean noncompatibleClasses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabsArray,
			ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray, int iterations){

		// s is the fact to check
		for (Slot s : factsArray){
			if (s.getSlotType() == SlotType.EMPTY) {
				continue;
			}

			for (Pair<CourseLab, CourseLab> nc: noncompatibleArray){
				
				// Checks to see if both Key and Value are in the course list
				if (courseLabsArray.contains(nc.getKey()) 
						&& courseLabsArray.contains(nc.getValue())){

					// Checks to seeif both Key and Value are in the facts list
					if (courseLabsArray.indexOf(nc.getKey()) < factsArray.size() 
							&& courseLabsArray.indexOf(nc.getValue()) < factsArray.size()){

						// Checks to see if s is equal to either key or value
						if (courseLabsArray.get(factsArray.indexOf(s)).equals(nc.getKey())
								|| courseLabsArray.get(factsArray.indexOf(s)).equals(nc.getValue())){

							// If the course is a key, checks to see the value is in the facts array,
							// Then ensures their times are different
							if (courseLabsArray.indexOf(nc.getValue()) <= factsArray.size() 
									&& courseLabsArray.get(factsArray.indexOf(s)).equals(nc.getKey())){

								// Checks if the times are the same
								if (factsArray.get(courseLabsArray.indexOf(nc.getKey())).getTime() == 
										factsArray.get(courseLabsArray.indexOf(nc.getValue())).getTime()){
									//System.out.println("reached here");
									if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
									{
										System.out.println("Impossible to populate because one of the non compatible pairs must be assigned to the same time slot.");
									}
									return false;
								}
							}
							// If the course is a value, checks to see the key is in the facts array,
							// Then ensures their times are different
							if (courseLabsArray.indexOf(nc.getKey()) <= factsArray.size()
									&& courseLabsArray.get(factsArray.indexOf(s)).equals(nc.getValue())){

								// Checks if the times are the same
								if (factsArray.get(courseLabsArray.indexOf(nc.getValue())).getTime() == 
										factsArray.get(courseLabsArray.indexOf(nc.getKey())).getTime()){
									if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
									{
										System.out.println("Impossible to populate because one of the non compatible pairs must be assigned to the same time slot.");
									}
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	} 



	//private static boolean noncompatibleClasses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabsArray,
	//		ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray, int iterations){

	//	// s is the fact to check
	//	for (Slot s : factsArray){
	//		if (s.getSlotType() == SlotType.EMPTY)
	//		{
	//			continue;
	//		}

	//		for (Pair<CourseLab, CourseLab> nc: noncompatibleArray){

	//			// Checks to see if both Key and Value are in the course list
	//			if (courseLabsArray.contains(nc.getKey()) 
	//					&& courseLabsArray.contains(nc.getValue())){

	//				// Checks to seeif both Key and Value are in the facts list
	//				if (courseLabsArray.indexOf(nc.getKey()) < factsArray.size() 
	//						&& courseLabsArray.indexOf(nc.getValue()) < factsArray.size()){


	//					// Checks to see if s is equal to either key or value
	//					if (courseLabsArray.get(factsArray.indexOf(s)).equals(nc.getKey())
	//							|| courseLabsArray.get(factsArray.indexOf(s)).equals(nc.getValue())){


	//						// If the course is a key, checks to see the value is in the facts array,
	//						// Then ensures their times are different
	//						if (courseLabsArray.indexOf(nc.getValue()) <= factsArray.size() 
	//								&& courseLabsArray.get(factsArray.indexOf(s)).equals(nc.getKey())){


	//							// Checks if the times are the same
	//							if (factsArray.get(courseLabsArray.indexOf(nc.getKey())).getTime() == 
	//									factsArray.get(courseLabsArray.indexOf(nc.getValue())).getTime()){
	//								if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
	//								{
	//									System.out.println("Impossible to populate because one of the non compatible pairs must be assigned to the same time slot.");
	//								}

	//								return false;
	//							}
	//						}
	//						// If the course is a value, checks to see the key is in the facts array,
	//						// Then ensures their times are different
	//						if (courseLabsArray.indexOf(nc.getKey()) <= factsArray.size()
	//								&& courseLabsArray.get(factsArray.indexOf(s)).equals(nc.getValue())){

	//							// Checks if the times are the same
	//							if (factsArray.get(courseLabsArray.indexOf(nc.getValue())).getTime() == 
	//									factsArray.get(courseLabsArray.indexOf(nc.getKey())).getTime()){
	//								if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
	//								{
	//									System.out.println("Impossible to populate because one of the non compatible pairs must be assigned to the same time slot.");
	//								}
	//								return false;
	//							}
	//						}
	//					}
	//				}
	//			}
	//		}
	//	}
	//	return true;
	//} 

	// Checks to ensure that no Course/Lab/Tutorial is in an unwanted time slow
	private static boolean unwantedTimes(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabsArray,
			ArrayList<Pair<CourseLab, Slot>> unwantedArray, int iterations){

		// s is the fact to check
		for (Slot s : factsArray){

			for (Pair<CourseLab, Slot> uw: unwantedArray){

				if (uw.getKey().getName().equals("CPSC813") || uw.getKey().getName().equals("CPSC913"))
				{
					if ((uw.getValue().getDaySeries().equals(DaySeries.TU) && uw.getValue().getTime().getHours() == 18))
					{
						System.out.println("Unwanted time for CPSC 813 or 913 is at TU 18:00");
						System.exit(0);

					}
				}

				if (courseLabsArray.contains(uw.getKey()) && factsArray.contains(uw.getValue())) {

					if (courseLabsArray.indexOf(uw.getKey()) < factsArray.size()) {

						// Checks to see if s is equal to the unwanted time slot
						//if (s.equals(uw.getValue())){
						//System.out.println(s.getDayAndTime());
						//System.out.println(uw.getValue().getDayAndTime());


						if (factsArray.get(courseLabsArray.indexOf(uw.getKey())).getTime().getHours() == uw.getValue().getTime().getHours())
						{
							if (factsArray.get(courseLabsArray.indexOf(uw.getKey())).getDaySeries() == uw.getValue().getDaySeries()) {
								if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
								{
									System.out.println("Impossible to populate because a course/lab/tutorial must be assigned to one of its unwanted time slots.");
								}
								return false;
							}
						}



						//if (s.getTime().getHours() == uw.getValue().getTime().getHours() && s.getDaySeries() == uw.getValue().getDaySeries()) {
						//	System.out.println(courseLabsArray.get(factsArray.indexOf(s)).getName());
						//	System.out.println(uw.getKey().getName());

						//	CourseLab cl = courseLabsArray.get(factsArray.indexOf(s));

						//	if(courseLabsArray.get(factsArray.indexOf(s)).getName().compareTo((uw.getKey()).getName()) == 0){
						//		System.out.println("reached here");
						//		return false;
						//		}

						//	// Checks to see if key and s refer to the same thing
						//	//if (courseLabsArray.get(factsArray.indexOf(s)).equals(uw.getKey())){

						//	//	return false;
						//		//}
						//}
					}
				}

			}
		}
		return true;
	}

	// All courses that are LEC 9 or higher are in the evening
	private static boolean eveningCourses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs, int iterations){
		
		for (int i=0; i<factsArray.size(); i++)
		{
			if (factsArray.get(i).getSlotType() == SlotType.EMPTY)
			{
				continue;
			}
			if (courseLabs.get(i).getLectureNumber() >= 9)
			{
				if (factsArray.get(i).getTime().getHours() < 18 | factsArray.get(i).getTime().getHours() > 24)
				{
					if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
					{
						System.out.println("Impossible to populate because the course with a lecture number higher than 9 cannot be assigned to an evening time slot");
					}
					return false;
				}
			}


		}
		return true;

		
		
		
		//for (Slot s : factsArray) {
		//	if (s.getSlotType() == SlotType.EMPTY) {
		//		continue;
		//	}
		//	int i = factsArray.indexOf(s);
		//	if (courseLabs.get(i).getLectureNumber() >= 9)
		//	{
		//		if (factsArray.get(i).getTime().getHours() < 18 || factsArray.get(i).getTime().getHours() > 24)
		//		{
		//			if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
		//			{
		//				System.out.println("Impossible to populate because the course with a lecture number higher than 9 cannot be assigned to an evening time slot");
		//			}
		//			return false;
		//		}
		//	}
		//}
		//return true;


		//for (int i=0; i<factsArray.size(); i++)
		//{
		//	if (factsArray.get(i).getSlotType() == SlotType.EMPTY)
		//	{
		//		continue;
		//	}
		//	if (courseLabs.get(i).getLectureNumber() >= 9)
		//	{
		//		if (factsArray.get(i).getTime().getHours() < 18 | factsArray.get(i).getTime().getHours() > 24)
		//		{
		//			if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
		//			{
		//				System.out.println("Impossible to populate because the course with a lecture number higher than 9 cannot be assigned to an evening time slot");
		//			}
		//			return false;
		//		}
		//	}


		//}
		//return true;
	}

	// All 500-level courses are in differnt time slots
	private static boolean fiveHunderedLevelCourses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs, int iterations){

		// An array list of all times of 500-level courses
		ArrayList<Time> seenTimes = new ArrayList();

		for (Slot s : factsArray){
			if (s.getSlotType() == SlotType.EMPTY)
			{
				continue;
			}

			// Sees if the name of the course contains CPSC 5 or SENG 5
			if (courseLabs.get(factsArray.indexOf(s)).getName().charAt(4) == '5') {


				// Checks to make sure that the time of the 500-level course is no in array seenTimes
				// If it is not, then it will add that time to seenTimes
				if (seenTimes.contains(s.getTime())){
					if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
					{
						System.out.println("Impossible to populate because at least one of the 500 level courses are assigned to the same Time Slot");
					}
					return false;
				} else {
					seenTimes.add(s.getTime());
				}
			}
		}

		return true;
	}

	// No course at 11:00-12:30 on Tuesday/Thursday
	private static boolean noCourseScheduled(ArrayList<Slot> factsArray, int iterations){
		for(Slot s: factsArray){

			// Ensures that course is a lecture, and then checks whether the time of that course is 11:00 or not
			if (s.getSlotType() == SlotType.COURSE && s.getTime().toString().contains("11:00") && s.getDaySeries().toString().contains("TU")){
				if (iterations == SetbasedSearch.MAX_ITERATIONS_NO_IMPROVEMENT)
				{
					System.out.println("Impossible to populate because a course would be assigned to TU 11:00");
				}
				return false;
			}
		}
		return true;
	}

	// Ensures Monday MWF and TTH for Courses and MW and TTH and F for Labs
	private static boolean datesAndTimesConsistancy(ArrayList<Slot> factsArray){
		return true;
	}

	// All courses that are LEC 9 or higher are in the evening
	private static boolean eveningCourses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs){


		for (int i=0; i<factsArray.size(); i++)
		{
			if (courseLabs.get(i).getLectureNumber() >= 9)
			{
				if (factsArray.get(i).getTime().getHours() < 18 | factsArray.get(i).getTime().getHours() > 24)
				{
					return false;
				}
			}


		}
		return true;
	}

	// All 500-level courses are in differnt time slots
	private static boolean fiveHunderedLevelCourses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs){

		// An array list of all times of 500-level courses
		ArrayList<Time> seenTimes = new ArrayList<Time>();

		for (Slot s : factsArray){

			// Sees if the name of the course contains CPSC 5 or SENG 5
			if (courseLabs.get(factsArray.indexOf(s)).getName().charAt(4) == '5') {


				// Checks to make sure that the time of the 500-level course is no in array seenTimes
				// If it is not, then it will add that time to seenTimes
				if (seenTimes.contains(s.getTime())){
					return false;
				} else {
					seenTimes.add(s.getTime());
				}
			}
		}

		return true;
	}

	// No course at 11:00-12:30 on Tuesday/Thursday
	private static boolean noCourseScheduled(ArrayList<Slot> factsArray){
		for(Slot s: factsArray){

			// Ensures that course is a lecture, and then checks whether the time of that course is 11:00 or not
			if (s.getSlotType() == SlotType.COURSE && s.getTime().toString().contains("11:00") && s.getDaySeries().toString().contains("TU")){
				return false;
			}
		}
		return true;
	}

	/* Special Courses 813 and 913 must be at 18:00-19:00 on TTH AND
        813 and 313 Labs/Tutorials are scheduled at different times AND
        913 and 413 Labs/Tutorials are scheduled at different times*/
	private static boolean specialCourses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs){


		ArrayList<Time> seenTimes813 = new ArrayList<Time>();
		ArrayList<Time> seenTimes913 = new ArrayList<Time>();

		for (Slot s : factsArray){

			if (!(s.getDaySeries() == DaySeries.TU && s.getTime().getHours() == 18)
					&& (courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC813") 
							|| courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC913"))){
				return false;
			}


			// Checks if course is part of CPSC 813
			if (courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC813")
					|| courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC913")){

				// If the course is CPSC 813 or 913 Lecture it is TTH 18:00
				if (courseLabs.get(factsArray.indexOf(s)).getType().equals("LEC")){
					if (!(s.getDayAndTime().contains("18:00")) || !(s.getDaySeries().equals(DaySeries.TU))){

						return false;
					}
				} else {

					// If the course name contains CPSC 813 but is not a lecture add the time to seenTimes813
					// These would be all lab/tutorial times for course CPSC 813
					if (courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC813")){
						seenTimes813.add(s.getTime());
					}

					// If the course name contains CPSC 913 but is not a lecture add the time to seenTimes913
					// These would be all lab/tutorial times for course CPSC 813
					if (courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC913")){
						seenTimes913.add(s.getTime());
					}
				}
			}
		}

		// Reloops to recheck list for CPSC 313 and CPSC 413 reoccuring times
		for (Slot c : factsArray){

			if ((courseLabs.get(factsArray.indexOf(c)).getName().equals("CPSC313") || courseLabs.get(factsArray.indexOf(c)).getName().equals("CPSC413")) && (courseLabs.get(factsArray.indexOf(c)).getType().equals("LAB")))
			{
				if (courseLabs.get(factsArray.indexOf(c)).getName().equals("CPSC313")) {
					if (seenTimes813.contains(c.getTime())) {

						return false;
					}
				}
				if (courseLabs.get(factsArray.indexOf(c)).getName().equals("CPSC413")) {
					if (seenTimes913.contains(c.getTime())) {
						return false;
					}
				}

			}


			//Checks if course is either CPSC 313 or CPSC 413 tutorial/lab
			//    		if ((c.getName().equals("CPSC313") || c.getName().equals("CPSC413")) && c.getType().equals("LAB")){
			//
			//    			// If the course is CPSC 313 tutorial/lab
			//    			if (c.getName().equals("CPSC313")){
			//    				if (seenTimes813.contains(factsArray.get(courseLabs.indexOf(c)).getTime())){
			//    					return false;
			//    				}
			//    				}
			//
			//    			// If the course is CPSC 413 tutorial/lab
			//    			if (c.getName().equals("CPSC413")){
			//    				if (seenTimes913.contains(factsArray.get(courseLabs.indexOf(c)).getTime())){
			//    					return false;
			//    				}
			//    				}
			//
			//}
		}
		return true;
	}

	// Checks each course/lab and ensures that the time slot dedicated to it is the same as its slot type
	private static boolean accurateTimetoCourseAndLabs(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs,
			ArrayList<Slot> slotsArray){

		/*for (Slot s : factsArray){

            if (!(courseLabs.get(factsArray.indexOf(s)).getType().equals("LEC") && s.getSlotType() == SlotType.COURSE)){
                return false;
            }
            if (!((courseLabs.get(factsArray.indexOf(s)).getType().equals("TUT") || courseLabs.get(factsArray.indexOf(s)).getType().equals("LAB")) 
                    && s.getSlotType() == SlotType.LAB)){
                return false;
            }
        }*/


		return true;

	}
}

