package com.OrModel;
import java.util.ArrayList;

import com.DataStructures.CourseLab;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.Main.Constr;

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

//    public static ArrayList<Slot> PopulateOrTree (ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
//                                            ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
//                                            ArrayList<Pair<CourseLab, Slot>> unwantedArray,
//                                            ArrayList<Pair<CourseLab, Slot>> initialSlot) {
//        ArrayList<Slot> fact;
//        Constr constraints = new Constr();
//        if(initialSlot.size() == 0) {
//            fact = new ArrayList<Slot>();
//        } else {
//            fact = new ArrayList<Slot>();
//            for(int i = 0; i < initialSlot.size(); i++) {
//                System.out.println(fact.size());
//                //fact.add(initialSlot.get(i).getValue());
//            }
//        }
//        int randSlot = (int)(Math.random() * slotList.size());
//        NodeTemp curNode = new NodeTemp(slotList.get(randSlot), null);
//        NodeTemp root = new NodeTemp(curNode.value, null);
//        int child = 0;
//        while(fact.size() < courseLabs.size()) {
//            //System.out.println(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray));
//            randSlot = (int)(Math.random() * slotList.size());
//            curNode.addChild(slotList.get(randSlot));
//            child++;
//            if(1 == 1) {
//                fact.add(slotList.get(randSlot));
//                curNode = new NodeTemp(curNode.children.get(child).value, curNode.value);
//                System.out.println("Adding");
//            } else {
//                curNode.backTrack();
//                if(child > curNode.children.size()) {
//                    curNode.backTrack();
//                    child = curNode.children.size();
//                }
//            }
//            
//        }
//        return fact;
//    }
    
    public static ArrayList<Slot> populate (ArrayList<CourseLab> courseLabs, ArrayList<Slot> slotList,
                                            ArrayList<Pair<CourseLab, CourseLab>> noncompatibleArray,
                                            ArrayList<Pair<CourseLab, Slot>> unwantedArray,
                                            ArrayList<Pair<CourseLab, Slot>> initialSlot) {
        
        ArrayList<Slot> fact;
        Constr constraints = new Constr();
        if(initialSlot.size() == 0) {
            fact = new ArrayList<Slot>();
        } else {
            fact = new ArrayList<Slot>();
//            for(int i = 0; i < initialSlot.size(); i++) {
//                fact.add(initialSlot.get(i).getValue());
//            }
        }
        while(fact.size() < courseLabs.size()) {
            int randSlot = (int)(Math.random() * slotList.size());
            fact.add(slotList.get(randSlot));
        }
        if(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray)) {
            fact = new ArrayList<Slot>(populate(courseLabs, slotList, noncompatibleArray, unwantedArray, initialSlot));
        } else {
            System.out.println("Fact Size: " + fact.size());
            System.out.println("courseLabs Size: " + courseLabs.size());
            System.out.println(constraints.constr(fact, slotList, courseLabs, noncompatibleArray, unwantedArray));
            for(int i = 0; i < fact.size(); i++) {
                System.out.println("DayTime: " + fact.get(i).getDayAndTime());
                System.out.println("Slot Type: " + fact.get(i).getSlotType());
                System.out.println("Time: " + fact.get(i).getTime());
                System.out.println("Course Min: " + fact.get(i).getCoursemin());
                System.out.println("Course Max: " + fact.get(i).getCoursemax());
                System.out.println("Lab Min: " + fact.get(i).getLabmin());
                System.out.println("Lab Max: " + fact.get(i).getLabmax());
                System.out.println("------------");
            }
        }
        
        return fact;
    }
}