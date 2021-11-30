package com.Main;

import java.io.*;
//import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

import com.DataStructures.CourseLab;
import com.DataStructures.DaySeries;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.SlotType;
import com.DataStructures.Triplet;
import com.Main.ParserError;
import com.Constants.ValidTimeSlots;

public class Parser {

    // Creating a CourseLab type from a string array
    public static CourseLab createCourseLab(String[] str, String errStr) {

        // ERROR Check: Invalid course or lab format
        // E.g. CPSC 433 433 LEC 01 TUT 01
        if (str.length > 6)
        {
            ParserError.invalidNumcourselab(errStr, str);
        }

        
        String name = str[0] + str[1];

        // Default -1
        int lectureNumber = -1;
        int labNumber = -1;
        String type;
        
        // Lab 
        if (str.length > 4) 
        {
            // E.g. CPSC 433 LEC 01 TUT 01
            lectureNumber = Integer.valueOf(str[3]);

            // ERROR Check: lectureNumber > 1 
            if (lectureNumber < 1)
            {
                ParserError.invalidCoursenum(lectureNumber, errStr);
            }

            labNumber = Integer.valueOf(str[5]);

            // ERROR Check: labNumber > 1
            if (labNumber < 1)
            {
                ParserError.invalidLabnum(labNumber, errStr);
            }

            CourseLab lab = new CourseLab(name, lectureNumber, labNumber, "LAB"); 
            return lab;
        } else if (str[2].equals("LEC")) 
        {
            // Course 
            // E.g. CPSC 433 LEC 01
            lectureNumber = Integer.valueOf(str[3]);

            // ERROR Check: lectureNumber > 1 
            if (lectureNumber < 1)
            {
                ParserError.invalidCoursenum(lectureNumber, errStr);
            }

            CourseLab course = new CourseLab(name, lectureNumber, labNumber, "LEC");
            return course;
        } else if (str[2].equals("TUT") || str[2].equals("LAB")) 
        {
            // Case where only TUT/LAB is provided and no LEC
            // E.g. CPSC 567 TUT 01
            labNumber = Integer.valueOf(str[3]);

            // ERROR Check: labNumber > 1
            if (labNumber < 1)
            {
                ParserError.invalidLabnum(labNumber, errStr);
            }

            CourseLab lab = new CourseLab(name, lectureNumber, labNumber, "LAB");
            return lab;
        } else 
        {
            // ERROR Check: "LEC", "TUT" or "LAB" needs to be provided in str[2]
            ParserError.invalidType(str[2], errStr);
            return null;
        }
    }

    // Creating a Slot type from a string array
    public static Slot createSlot(String[] str, String type, String errStr)
    {

        if (str.length != 2)
        {
            ParserError.invalidNumSlot(errStr, str);
        }


        DaySeries daySeries;
        if (str[0].equals("MO"))
        {
            daySeries = DaySeries.MO;
        } else if (str[0].equals("TU"))
        {
            daySeries = DaySeries.TU;
        } else 
        {
            daySeries = DaySeries.FR;
        }

        SlotType slotType;
        if (type.equals("LEC"))
        {
            slotType = SlotType.COURSE;
        } else 
        {
            slotType = SlotType.LAB;
        }

        // Splitting on :
        String[] time = str[1].split(":");
        int hours = Integer.valueOf(time[0]);
        int minutes = Integer.valueOf(time[1]);

        // ERROR Check: hour < 0 || hours > 24 
        if (hours < 0 || hours > 24) 
        {
            ParserError.invalidHour(hours, errStr);
        }

        // ERROR Check: minutes < 0 || minutes > 59
        if (minutes < 0 || minutes > 59)
        {
            ParserError.invalidMinute(minutes, errStr);
        }



        Slot slot = new Slot(daySeries, slotType, hours, minutes);

        boolean b = false;

        for (Slot s: ValidTimeSlots.SLOT_DATA)
        {
            if (s.equals(slot))
            {
                b = true;
                break;
            }
        }



        //        System.out.println(slot.getDaySeries() + " " + slot.getSlotType() + " " + slot.getTime());
        //
        //        boolean b = ValidTimeSlots.SLOT_DATA.contains(slot); 
        //        System.out.println(b);
        //
        //        boolean test = (ValidTimeSlots.SLOT_DATA.get(0).equals(slot));
        //        System.out.println(test);
        //        System.out.println(ValidTimeSlots.SLOT_DATA.get(0).getDaySeries());
        //        System.out.println(ValidTimeSlots.SLOT_DATA.get(0).getSlotType());
        //        System.out.println(ValidTimeSlots.SLOT_DATA.get(0).getTime());
        //
        //        for (Slot s: ValidTimeSlots.SLOT_DATA)
        //        {
        //            System.out.println(s.getDaySeries() + " "+ s.getSlotType() + " " + s.getTime());
        //        }
        //
                if (!b)
                {
                    if (errStr.equals("Preferences: "))
                    {
                        System.out.println("Warning: Slot Object " + slot.getDayAndTime() + " is an invalid time slot. Ignoring this for Preferences");
                        return null;
                    } else 
                    {
                    ParserError.invalidSlot(errStr, str);
                    }
                }

        return slot;

    }

    public static Slot createSlotMinMax(String[] str, String type, String errStr)
    {
        if (str.length != 4)
        {
            ParserError.invalidNumSlotcoursemaxmin(errStr, str);

        }

        DaySeries daySeries;
        if (str[0].equals("MO"))
        {
            daySeries = DaySeries.MO;
        } else if (str[0].equals("TU"))
        {
            daySeries = DaySeries.TU;
        } else if (str[0].equals("FR")) 
        {
            daySeries = DaySeries.FR;
        } else 
        {
            // ERROR Check: Invalid day series
            daySeries = null;
            ParserError.invalidDaySeries(str[0]);
        }

        SlotType slotType;
        int coursemax=-1, coursemin=-1, labmax=-1, labmin=-1;
        if (type.equals("LEC"))
        {
            slotType = SlotType.COURSE;
            coursemax = Integer.valueOf(str[2].replaceAll("\\s+",""));
            coursemin = Integer.valueOf(str[3].replaceAll("\\s+",""));

            // ERROR Check: coursemax > coursemin
            if (coursemax < coursemin)
            {
                ParserError.invalidCoursemaxmin(coursemax, coursemin);
            }
        } else 
        {
            slotType = SlotType.LAB;
            labmax = Integer.valueOf(str[2].replaceAll("\\s+",""));
            labmin = Integer.valueOf(str[3].replaceAll("\\s+",""));

            // ERROR Check: labmax > labmin
            if (labmax < labmin)
            {
                ParserError.invalidLabmaxmin(labmax, labmin);
            }
        }

        String[] time = str[1].replaceAll("\\s+","").split(":");
        int hours = Integer.valueOf(time[0]);
        int minutes = Integer.valueOf(time[1]);

        // ERROR Check: hour < 0 || hours > 24 
        if (hours < 0 || hours > 24) 
        {
            ParserError.invalidHour(hours, errStr);
        }

        // ERROR Check: minutes < 0 || minutes > 59
        if (minutes < 0 || minutes > 59)
        {
            ParserError.invalidMinute(minutes, errStr);
        }

        // Checking if slot exists in SLOT_DATA
        Slot checkSlot = new Slot(daySeries, slotType, hours, minutes);
        boolean b = false;

        for (Slot s: ValidTimeSlots.SLOT_DATA)
        {
            if (s.equals(checkSlot))
            {
                b = true;
                break;
            }
        }

        if (!b) 
        {
            ParserError.invalidSlot(errStr, str);
        }


        Slot slot = new Slot(daySeries, slotType, hours, minutes, coursemin, coursemax, labmin, labmax); 

        return slot;
    }

    // Parses Course slots and Lab slots
    public static ArrayList<Slot> parseCourseLabSlots(String path) {
        ArrayList<Slot> list = new ArrayList<Slot>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) 
            {

                // Jump to Course slots:
                if (line.equals("Course slots:"))
                {
                    while (true)
                    {
                        line = reader.readLine();
                        if (line.equals("")) break;


                        // Delimiting by comma
                        String[] temp = line.split(",");

                        Slot slot = createSlotMinMax(temp, "LEC", "Course slots: or Lab slots: ");
                        list.add(slot);

                    }
                }

                // Jump to Lab slots:
                if (line.equals("Lab slots:"))
                {
                    while (true)
                    {
                        line = reader.readLine();
                        if (line.equals("")) break;

                        // Delimiting by comma
                        String[] temp = line.split(",");

                        Slot slot = createSlotMinMax(temp, "LAB", "course slots: or Lab slots: ");
                        list.add(slot);
                    }
                }
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return list;
    }



    // Parses Courses and Labs from text file
    public static ArrayList<CourseLab> parseCourseLab(String path) {
        ArrayList<CourseLab> list = new ArrayList<CourseLab>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) 
            {
                // Jump to Courses:
                if (line.equals("Courses:"))
                {
                    while (true)
                    {
                        line = reader.readLine();
                        if (line.equals("")) break;

                        // Delimiting by space
                        String[] temp = line.split("\\s+");

                        CourseLab course = createCourseLab(temp, "Courses: or Labs:");
                        list.add(course);
                    }
                    line = reader.readLine();
                }

                // Jump to Labs:
                if (line.equals("Labs:"))
                {
                    while (true)
                    {
                        line = reader.readLine();
                        if (line.equals("")) break;

                        // Delimiting by space
                        String[] temp = line.split("\\s+");

                        CourseLab lab = createCourseLab(temp, "Courses: or Labs:");
                        list.add(lab);
                    }
                    line = reader.readLine();
                }
                line = reader.readLine();
            }
            reader.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        list.sort(Comparator.comparing(CourseLab::getHash));

        return list;
    }

    // Parses not compatible from text file
    public static ArrayList<Pair<CourseLab, CourseLab>> parseNotCompatible(String path) {
        ArrayList<Pair<CourseLab, CourseLab>> list = new ArrayList<Pair<CourseLab, CourseLab>>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) 
            {
                // Jump to Not Compatible:
                if (line.equals("Not compatible:"))
                {
                    while (true)
                    {
                        line = reader.readLine();
                        if (line.equals("")) break;

                        // Delimiting by comma, splitting the two courses
                        String[] twoCourses = line.split(",\\s*");

                        // ERROR Check: 2 courses/labs are provided
                        if (twoCourses.length != 2)
                        {
                            ParserError.invalidNumofcourselab(twoCourses.length);
                        }

                        String[] course1Arr = twoCourses[0].split("\\s+");
                        String[] course2Arr = twoCourses[1].split("\\s+");

                        // Creating course1 and course 2
                        CourseLab course1 = createCourseLab(course1Arr, "Not compatible: ");
                        CourseLab course2 = createCourseLab(course2Arr, "Not compatible: ");

                        Pair<CourseLab, CourseLab> p = new Pair<>(course1, course2);
                        list.add(p);
                    }
                    line = reader.readLine();
                }

                line = reader.readLine();
            }
            reader.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    // Parsing unwanted from textfile
    public static ArrayList<Pair<CourseLab, Slot>> parseUnwanted(String path) {
        ArrayList<Pair<CourseLab, Slot>> list = new ArrayList<Pair<CourseLab, Slot>>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) 
            {
                // Jump to Unwanted 
                if (line.equals("Unwanted:"))
                {
                    while (true)
                    {
                        line = reader.readLine();
                        if (line.equals("")) break;

                        // Delimiting by comma (only first instance)
                        String[] delimited = line.split(",\\s*", 2);

                        String[] courseArr = delimited[0].split("\\s+");
                        String[] slotArr = delimited[1].split(",\\s+");

                        CourseLab courselab = createCourseLab(courseArr, "Unwanted: ");
                        Slot slot = createSlot(slotArr, courselab.getType(), "Unwanted: ");

                        Pair<CourseLab, Slot> p = new Pair<CourseLab, Slot>(courselab, slot);

                        list.add(p);

                    }
                }
                line = reader.readLine(); 
            }
            reader.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    // Parsing preferences from textfile
    public static ArrayList<Triplet<Slot, CourseLab, Integer>> parsePreferences(String path) {
        ArrayList<Triplet<Slot, CourseLab, Integer>> list = new ArrayList<Triplet<Slot, CourseLab, Integer>>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null)
            {
                // Jump to Preferences:
                if (line.equals("Preferences:"))
                {
                    while (true)
                    {
                        line = reader.readLine();
                        if (line.equals("")) break;

                        // Delimiting by comma
                        String[] delimited = line.split(",\\s*");

                        if (delimited.length != 4)
                        {
                            ParserError.invalidPreferences(delimited);
                        }

                        String[] slotArr = {delimited[0], delimited[1]};
                        String[] courseArr = delimited[2].split("\\s+");
                        String rankingStr = delimited[3];


                        CourseLab courselab = createCourseLab(courseArr, "Preferences: ");
                        Slot slot = createSlot(slotArr, courselab.getType(), "Preferences: "); 
                        if (slot != null) 
                        {
                            int ranking = Integer.valueOf(rankingStr);

                            // ERROR Check: Ranking must be between 1 and 10
                            if (ranking < 1 || ranking > 10)
                            {
                                ParserError.invalidRanking(ranking);
                            }

                            Triplet<Slot, CourseLab, Integer> t = new Triplet<Slot, CourseLab, Integer>(slot, courselab, ranking);
                            list.add(t);
                        }

                    }
                }
                line = reader.readLine();
            }

        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    // Parsing pair from text file
    public static ArrayList<Pair<CourseLab, CourseLab>> parsePair(String path) {
        ArrayList<Pair<CourseLab, CourseLab>> list = new ArrayList<Pair<CourseLab, CourseLab>>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) 
            {
                // Jump to Pair:
                if (line.equals("Pair:"))
                {
                    while (true)
                    {
                        line = reader.readLine();
                        if (line.equals("")) break;

                        // Delimiting by comma, splitting the two courses
                        String[] twoCourses = line.split(",\\s*");

                        // ERROR Check: Needs to only have 2 courselabs per line
                        if (twoCourses.length != 2)
                        {
                            ParserError.invalidPair(twoCourses);
                        }

                        String[] course1Arr = twoCourses[0].split("\\s+");
                        String[] course2Arr = twoCourses[1].split("\\s+");

                        CourseLab course1 = createCourseLab(course1Arr, "Pair: ");
                        CourseLab course2 = createCourseLab(course2Arr, "Pair: ");

                        Pair<CourseLab, CourseLab> p = new Pair<>(course1, course2);
                        list.add(p);
                    }
                    line = reader.readLine();
                }

                line = reader.readLine();
            }
            reader.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    // Parsing partial assignments from text file
    public static ArrayList<Pair<CourseLab, Slot>> parsePartialAssignments(String path) {
        ArrayList<Pair<CourseLab, Slot>> list = new ArrayList<Pair<CourseLab, Slot>>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) 
            {
                // Jump to partial assignments 
                if (line.equals("Partial assignments:"))
                {
                    while (true)
                    {
                        line = reader.readLine();
                        if (line.equals("")) break;

                        // Delimiting by comma (only first instance)
                        //String[] delimited = line.split(",\\s+", 2);
                        String[] delimited = line.split(",\\s+");

                        // ERROR Check: Only 3 elements in comma delimited array 
                        if (delimited.length != 3)
                        {
                            ParserError.invalidPartAssign(delimited);
                        }

                        String[] courseArr = delimited[0].split("\\s+");
                        String[] slotArr = {delimited[1], delimited[2]};

                        CourseLab courselab = createCourseLab(courseArr, "Partial assignments: ");
                        Slot slot = createSlot(slotArr, courselab.getType(), "Partial assignments: ");

                        Pair<CourseLab, Slot> p = new Pair<CourseLab, Slot>(courselab, slot);

                        list.add(p);

                    }
                    line = reader.readLine();
                }
                line = reader.readLine(); 
            }
            reader.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }



    // Testing (Will remove later)

    public static void main(String[] args) {

        //String txtfile = "../../com/Main/ShortExample.txt";
        String txtfile = "../res/test6.txt";

        ArrayList<Slot> slotList = parseCourseLabSlots(txtfile);

        ArrayList<CourseLab> courselabList = parseCourseLab(txtfile);

        ArrayList<Pair<CourseLab, CourseLab>> notCompatibleList = parseNotCompatible(txtfile);

        ArrayList<Pair<CourseLab, Slot>> unwantedList = parseUnwanted(txtfile);

        ArrayList<Triplet<Slot, CourseLab, Integer>> preferencesList = parsePreferences(txtfile);

        ArrayList<Pair<CourseLab, CourseLab>> pairList = parsePair(txtfile);

        ArrayList<Pair<CourseLab, Slot>> partialAssignList = parsePartialAssignments(txtfile);
        String string[] = {"CPSC 433 LEC 01 TUT 01"};


        //Slot newSlot = new Slot(DaySeries.MO, SlotType.COURSE, 8, 0);

        //boolean test = ValidTimeSlots.SLOT_DATA.contains(newSlot);
        //System.out.println(test);

        //for (Slot s: ValidTimeSlots.SLOT_DATA)
        //{
        //    if (s.equals(newSlot))
        //    {
        //        System.out.println("reached here");
        //        break;
        //    }
        //}


        //if (ValidTimeSlots.SLOT_DATA.contains(newSlot))
        //{
        //    System.out.println("reached here");
        //}




        //createCourseLab(string);

                for (Slot s: slotList)
                {
                    System.out.println(s.getDaySeries() + " "  + s.getTime().toString() + " " + s.getCoursemax() +  " " + s.getCoursemin() + " " + s.getLabmax() + " " + s.getLabmin() + " " + s.getSlotType());
                }


        for (CourseLab i: courselabList)
        {
            System.out.println(i.getName() + " " + i.getLectureNumber() + " " + i.getLabNumber() + " " + i.getType());
        }

        for (Pair<CourseLab, CourseLab> p: notCompatibleList)
        {
            CourseLab c1 = p.getKey();
            CourseLab c2 = p.getValue();

            System.out.println(c1.getName() + " " + c2.getName());
        }


        for (Pair<CourseLab, Slot> p: unwantedList)
        {
            CourseLab c1 = p.getKey();
            Slot c2 = p.getValue();
            System.out.println(c1.getName() + " " + c2.getDaySeries());
        }

        for (Triplet<Slot, CourseLab, Integer> t: preferencesList)
        {
            Slot s = t.getSlot();
            CourseLab c = t.getCourseLab();
            int r = t.getRanking();
            //System.out.println(s.getDayAndTime());
            System.out.println(s.getDaySeries() + c.getName() + r);
        }

        for (Pair<CourseLab, CourseLab> p: pairList)
        {
            CourseLab c1 = p.getKey();
            CourseLab c2 = p.getValue();

            System.out.println(c1.getName() + " " + c2.getName());
        }

        for (Pair<CourseLab, Slot> p: partialAssignList)
        {
            CourseLab c1 = p.getKey();
            Slot c2 = p.getValue();
            System.out.println(c1.getName() + " " + c2.getDaySeries());
        }


    }


}


