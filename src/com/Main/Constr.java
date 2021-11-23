package com.Main;

import com.Model.*;
import java.util.*;

/* Questions:
    What does the elements of the array courseLab look like?
    For dayseries why is it only Monday and Tuesday?
*/


public class Constr {
    public Constr(){

    }

    public boolean constr(ArrayList<Slot> factsArray, ArrayList<Slot> slotsArray, ArrayList<CourseLab> courseLabs,
        ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray, ArrayList<Pair<CourseLab, Slot>> unwantedArray)
    {
        return maxTimes(factsArray, slotsArray) && differentTimes(factsArray, courseLabs)
            && noncompatibleClasses(factsArray, courseLabs, noncompatibleArray) 
            && unwantedTimes(factsArray, courseLabs, unwantedArray) && noCourseScheduled(factsArray);
    }

    //Max course and Max Labs per time-slot
    private static boolean maxTimes(ArrayList<Slot> factsArray, ArrayList<Slot> slotsArray)
    {
        for(Slot s : slotsArray)
        {
            // Get the max number of Courses or Labs for each time slot
            int max = s.getCoursemax();
            if(max == 0)
            {
                max = s.getLabmax();
            }

            if(Collections.frequency(factsArray, s) > max)
            {
                return false;
            }
        }

        return true;
    }

    // Courses must be differnt times than their labs/tutorials
    private static boolean differentTimes(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs){
        for (int i = 0; i < factsArray.size(); i ++){

            if (factsArray.get(i).getSlotType() == SlotType.COURSE){

                String courseName = courseLabs.get(i).getName();
                Time courseTime = factsArray.get(i).getTime();

                for (int j = 0; j < factsArray.size(); j ++){
                    if (courseLabs.get(j).getName().equals(courseName) && factsArray.get(j).getSlotType() == SlotType.LAB){
                        if (courseTime.equals(factsArray.get(j).getTime())){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    // Courses and labs/tutorial noncompatibility
    private static boolean noncompatibleClasses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabsArray,
        ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray){
        for (Pair<CourseLab, CourseLab> nc: noncompatibleArray){

            CourseLab key = nc.getKey();
            CourseLab value = nc.getValue();

            if(factsArray.get(courseLabsArray.indexOf(key)).getTime() == 
                factsArray.get(courseLabsArray.indexOf(value)).getTime()){
                return false;
            }
        }
        return true;
    }

    // Checks to ensure that no Course/Lab/Tutorial is in an unwanted time slow
    private static boolean unwantedTimes(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabsArray,
        ArrayList<Pair<CourseLab, Slot>> unwantedArray){
        for (Pair<CourseLab, Slot> uw: unwantedArray){

            CourseLab key = uw.getKey();
            Slot value = uw.getValue();
    
            if(factsArray.get(courseLabsArray.indexOf(key)).getTime() == value.getTime()){
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
        return true;
    }

    // All 500-level courses are in differnt time slots
    private static boolean fiveHunderedLevelCourses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs){
        return true;
    }

    // No course at 11:00-12:30 on Tuesday/Thursday
    private static boolean noCourseScheduled(ArrayList<Slot> factsArray){
        for(Slot s: factsArray){
            if (s.getSlotType() == SlotType.COURSE && s.getTime().equals("11:00")){
                return false;
            }
        }
        return true;
    }

    /* Special Courses 813 and 913 must be at 18:00-19:00 on TTH AND
        813 and 313 Labs/Tutorials are sceduled at differnt times AND
        913 and 413 Labs/Tutorials are sceduled at differnt times*/
    private static boolean specialCourses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs){

        return true;
    }
}

