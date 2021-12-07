package com.Main;

import com.DataStructures.CourseLab;
import com.DataStructures.DaySeries;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.Triplet;

//Used for debugging/testing.
//import com.Main.Parser;


import java.util.*;

/**
 * Soft constraint evaluator.
 *
 * @author Jhy-An Chen (30071972)
 */
public class Eval {

    //Weights for soft constraints.
    private final int weightMinFilled;
    private final int weightPref;
    private final int weightPair;
    private final int weightSecDiff;

    public Eval(int weightMinFilled, int weightPref, int weightPair, int weightSecDiff)
    {
        this.weightMinFilled = weightMinFilled;
        this.weightPref = weightPref;
        this.weightPair = weightPair;
        this.weightSecDiff = weightSecDiff;
    }

    /**
     * Evaluates a fact for its fwert values.
     * @param fact Fact to be evaluated.
     * @param slotsArray List of all slots.
     * @param courseLabArray List of all courses and labs.
     * @param prefArray List of preferred placements for courses and labs.
     * @param pairArray List of courses and labs that would be preferred to be placed together.
     * @return fwert value.
     */
    public int eval(ArrayList<Slot> fact, ArrayList<Slot> slotsArray, ArrayList<CourseLab> courseLabArray,
                    ArrayList<Triplet<Slot, CourseLab, Integer>> prefArray,
                    ArrayList<Pair<CourseLab, CourseLab>> pairArray)
    {
    	//System.out.println("---------------Eval---------------");
        return evalMinFilled(fact, slotsArray) * this.weightMinFilled +
                evalPref(fact, courseLabArray, prefArray) * this.weightPref +
                evalPair(fact, courseLabArray, pairArray) * this.weightPair +
                evalSecDiff(fact, courseLabArray) * this.weightSecDiff;
        
    }

    /**
     * Evaluates a fact for slots that are under-filled.
     * @param fact Fact to be evaluated.
     * @param slotsArray List of all slots.
     * @return minfilled fwert value.
     */
    private static int evalMinFilled(ArrayList<Slot> fact, ArrayList<Slot> slotsArray)
    {
        int value = 0;
        for(Slot s : slotsArray)
        {
            //One of these should be 0 since slots only contain either labs or courses.
            int min = s.getCoursemin();
            if(min == 0)
            {
                min = s.getLabmin();
            }

            //Checks if slot appears more than the minimum.
            if(Collections.frequency(fact, s) < min)
            {
                value++;
            }
        }
        //System.out.println("evalMinFilled: " + value);
        return value;
    }

    /**
     * Evaluates a fact for unfulfilled preferences.
     * @param fact Fact to be evaluated.
     * @param courseLabsArray List of all courses and labs.
     * @param prefArray List of preferred placements for courses and labs.
     * @return pref fwert value.
     */
    private static int evalPref(ArrayList<Slot> fact, ArrayList<CourseLab> courseLabsArray,
                         ArrayList<Triplet<Slot, CourseLab, Integer>> prefArray)
    {
        int value = 0;

        for(Triplet<Slot, CourseLab, Integer> pref : prefArray)
        {
        	if (pref.getCourseLab().getName().equals("CPSC813") || pref.getCourseLab().getName().equals("CPSC913"))
        	{
        		if (!(pref.getSlot().getTime().getHours() == 18 && pref.getSlot().getDaySeries().equals(DaySeries.TU)))
        		{
        			value += pref.getRanking();
        		}
        	
        	} else if(!fact.get(courseLabsArray.indexOf(pref.getCourseLab())).equals(pref.getSlot()))
            {
                value += pref.getRanking();
            }
        }

//        for (Slot s : fact){
//
//            for(Triplet<Slot, CourseLab, Integer> pref : prefArray){
//
//                if (courseLabsArray.get(fact.indexOf(s)).equals(pref.getCourseLab())){
//
//                    if(!s.equals(pref.getSlot())){
//                        value += pref.getRanking();
//                    }
//                }
//            }
//        }

        //for(Triplet<Slot, CourseLab, Integer> pref : prefArray)
        //{
        //    if (courseLabsArray.indexOf(pref.getCourseLab()) != -1
        //        && ){
        //        //Checks if index of course or lab corresponds to the wanted slot.
        //        if(fact.get(courseLabsArray.indexOf(pref.getCourseLab())) != pref.getSlot())
        //        {
        //            value += pref.getRanking();
        //        }
        //    }   
        //}
        //System.out.println("evalPref: " + value);
        return value;
    }

    /**
     * Evaluates a fact for unfulfilled pairs.
     * @param fact Fact to be evaluated.
     * @param courseLabsArray List of all courses and labs.
     * @param pairArray List of courses and labs that would be preferred to be placed together.
     * @return pair fwert value.
     */
    private static int evalPair(ArrayList<Slot> fact, ArrayList<CourseLab> courseLabsArray,
                                ArrayList<Pair<CourseLab, CourseLab>> pairArray)
    {
        int value = 0;
        for(Pair<CourseLab, CourseLab> pair : pairArray)
        {
            if (courseLabsArray.contains(pair.getKey())
                && courseLabsArray.contains(pair.getValue())){

                if(!Objects.equals(
                    fact.get(courseLabsArray.indexOf(pair.getKey())).getDayAndTime(),
                    fact.get(courseLabsArray.indexOf(pair.getValue())).getDayAndTime()))
                {
                	//System.out.println(pair.getKey().getStringFormatted() + " " + pair.getValue().getStringFormatted());
                    value++;
                }
            }
                
        }
        //System.out.println("evalPair: " + value);
        return value;
    }

    /**
     * Evaluates a fact for sections occurring at the same time.
     * @param fact Fact to be evaluated.
     * @param courseLabsArray List of all courses and labs.
     * @return secDiff fwert value.
     */
    private static int evalSecDiff(ArrayList<Slot> fact, ArrayList<CourseLab> courseLabsArray)
    {
        int value = 0;
        //Used for checking if time was seen before.
        Set<String> seenSlots = new HashSet<>();

        //Note slots are already sorted, and thus we can perform the following.
        for(int i = 0; i < courseLabsArray.size(); i++)
        {
            String courseName = courseLabsArray.get(i).getName();
            seenSlots.add(fact.get(i).getDayAndTime());

            while(i + 1 < courseLabsArray.size())
            {
                i++;
                //Checks if next element is part of the same course.
                if(Objects.equals(courseName, courseLabsArray.get(i).getName()))
                {
                    //Checks if this time has already been seen for some other section of the course
                    //and records the time.
                    if(!seenSlots.add(fact.get(i).getDayAndTime()))
                    {
                        value++;
                    }
                }
                else
                {
                    //Since we have sorted the courses, we know that no more sections of this course exist
                    //and thus can stop looking.
                    break;
                }
            }
            //Resets slots for next course.
            seenSlots.clear();
        }
        //System.out.println("evalSecDiff: " + value);
        return value;
    }

    //Used for debugging/testing.
    public static void main(String[] args) {
//        System.out.println("Testing Eval.");
//
//        String txtfile = "src/com/Main/ShortExample.txt";
//
//        ArrayList<CourseLab> testCourseLab = Parser.parseCourseLab(txtfile);
//        ArrayList<Slot> slots = Parser.parseCourseLabSlots(txtfile);
//
//        for(int i = 0; i < testCourseLab.size(); i++)
//        {
//            System.out.println(testCourseLab.get(i).getHash());
//        }
//        for(int i = 0; i < slots.size(); i++)
//        {
//            System.out.println(slots.get(i).);
//        }
//
//        ArrayList<Slot> testFact = new ArrayList<Slot>();
//        for(int i = 0; i < testCourseLab.size(); i++)
//        {
//            testFact.add(slots.get(0));
//        }
//
//        for(int i = 0; i < testCourseLab.size(); i++)
//        {
//            testFact.add(slots.get(0));
//        }
//
//        Eval e = new Eval(1, 3, 1, 1);
//        System.out.println(e.eval());
    }
}
