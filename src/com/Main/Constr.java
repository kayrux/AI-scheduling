package com.Main;

import com.Model.*;
import java.util.*;


public class Constr {

    public Constr(){

    }

    public boolean constr(ArrayList<Slot> factsArray, ArrayList<Slot> slotsArray, ArrayList<CourseLab> courseLabs,
        ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray, ArrayList<Pair<CourseLab, Slot>> unwantedArray)
    {
        return maxTimes(factsArray, slotsArray) && differentTimes(factsArray, courseLabs)
            && noncompatibleClasses(factsArray, courseLabs, noncompatibleArray) 
            && unwantedTimes(factsArray, courseLabs, unwantedArray) && eveningCourses(factsArray, courseLabs)
            && fiveHunderedLevelCourses(factsArray, courseLabs) && noCourseScheduled(factsArray)
            && specialCourses(factsArray, courseLabs);
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

            // Checks how often that specific time slot appears in the facts array
            if(Collections.frequency(factsArray, s) > max)
            {
                return false;
            }
        }

        return true;
    }

    // Courses must be differnt times than their labs/tutorials
    private static boolean differentTimes(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs){

        for (Slot s : factsArray){
            
            // Checks if the current s is a course
            if (s.getSlotType() == SlotType.COURSE){

                for (Slot c : factsArray) {

                    String courseName = courseLabs.get(factsArray.indexOf(s)).getName();
                    Time courseTime = s.getTime();

                    // Checks if the name of the course is the same as s AND c is a lab/tutorial
                    if (courseLabs.get(factsArray.indexOf(c)).getName().equals(courseName) && c.getSlotType() == SlotType.LAB){

                        // Checks if the time of the lab/tutorial is the same as the courseTime
                        if (c.getTime().equals(courseTime)){
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

        // s is the fact to check
        for (Slot s : factsArray){
            
            for (Pair<CourseLab, CourseLab> nc: noncompatibleArray){

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
                            return false;
                        }
                    }
                }
                
            }
        }
        return true;
    }

    // Checks to ensure that no Course/Lab/Tutorial is in an unwanted time slow
    private static boolean unwantedTimes(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabsArray,
        ArrayList<Pair<CourseLab, Slot>> unwantedArray){

        // s is the fact to check
        for (Slot s : factsArray){
            
            for (Pair<CourseLab, Slot> uw: unwantedArray){

                // Checks to see if s is equal to the unwanted time slot
                if (s.equals(uw.getValue())){

                    // Checks if key course is part of the facts array
                    if (courseLabsArray.indexOf(uw.getKey()) <= factsArray.size()){

                        // Checks to see if key and s refer to the same thing
                        if (courseLabsArray.get(factsArray.indexOf(s)).equals(uw.getKey())){

                            return false;
                        }
                    }
                }
                
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
        for (Slot s: factsArray){

            // Checks whether the course lecture number is greater than 9
            if (courseLabs.get(factsArray.indexOf(s)).getLectureNumber() >= 9){

                // Checks the hour of the timeslow and whether the time is less than 18 or greater than 24
                if (s.getTime().getHours() < 18 && s.getTime().getHours() > 24){
                    return false;
                }
            }
        }
        return true;
    }

    // All 500-level courses are in differnt time slots
    private static boolean fiveHunderedLevelCourses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs){
        
        // An array list of all times of 500-level courses
        ArrayList<Time> seenTimes = new ArrayList();

        for (Slot s : factsArray){

            // Sees if the name of the course contains CPSC 5 or SENG 5
            if (courseLabs.get(factsArray.indexOf(s)).getName().contains(" 5")){


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
            if (s.getSlotType() == SlotType.COURSE && s.getDayAndTime().contains("11:00")){
                return false;
            }
        }
        return true;
    }

    /* Special Courses 813 and 913 must be at 18:00-19:00 on TTH AND
        813 and 313 Labs/Tutorials are sceduled at differnt times AND
        913 and 413 Labs/Tutorials are sceduled at differnt times*/
    private static boolean specialCourses(ArrayList<Slot> factsArray, ArrayList<CourseLab> courseLabs){

        ArrayList<Time> seenTimes813 = new ArrayList();
         ArrayList<Time> seenTimes913 = new ArrayList();
        
        for (Slot s : factsArray){

            // Checks if course is part of CPSC 813
            if (courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC 813")
                || courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC 913")){

                // If the course is CPSC 813 or 913 Lecture it is TTH 18:00
                if (courseLabs.get(factsArray.indexOf(s)).getType().equals("LEC")){
                    if (!(s.getDayAndTime().contains("18:00")) || !(s.getDaySeries().equals(DaySeries.TU))){
                        return false;
                    }
                } else {

                    // If the course name contains CPSC 813 but is not a lecture add the time to seenTimes813
                    // These would be all lab/tutorial times for course CPSC 813
                    if (courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC 813")){
                        seenTimes813.add(s.getTime());
                    }

                    // If the course name contains CPSC 913 but is not a lecture add the time to seenTimes913
                    // These would be all lab/tutorial times for course CPSC 813
                    if (courseLabs.get(factsArray.indexOf(s)).getName().equals("CPSC 913")){
                        seenTimes913.add(s.getTime());
                    }
                }
            }
        }

        // Reloops to recheck list for CPSC 313 and CPSC 413 reoccuring times
        for (CourseLab c : courseLabs){
            
            //Checks if course is either CPSC 313 or CPSC 413 tutorial/lab
            if ((c.getName().equals("CPSC 313") || c.getName().equals("CPSC 413")) && c.getType().equals("LAB")){

                // If the course is CPSC 313 tutorial/lab
                if (c.getName().equals("CPSC 313")){
                    if (seenTimes813.contains(factsArray.get(courseLabs.indexOf(c)).getTime())){
                        return false;
                    }
                }
                
                // If the course is CPSC 413 tutorial/lab
                if (c.getName().equals("CPSC 413")){
                    if (seenTimes913.contains(factsArray.get(courseLabs.indexOf(c)).getTime())){
                        return false;
                    }
                }

            }
        }
        return true;
    }
}

