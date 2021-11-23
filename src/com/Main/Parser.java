package com.Main;

import java.io.*;
//import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
//import javafx.util.Pair;
import com.Model.Pair;
import com.Model.CourseLab;
import com.Model.Slot;
import com.Model.DaySeries;
import com.Model.SlotType;
import com.Model.Triplet;

public class Parser {

    // Creating a CourseLab type from a string array
    public static CourseLab createCourseLab(String[] str) {
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
            labNumber = Integer.valueOf(str[5]);
            CourseLab lab = new CourseLab(name, lectureNumber, labNumber, "LAB"); 
            return lab;
        } else if (str[2].equals("LEC")) 
        {
            // Course 
            // E.g. CPSC 433 LEC 01
            lectureNumber = Integer.valueOf(str[3]);
            CourseLab course = new CourseLab(name, lectureNumber, labNumber, "LEC");
            return course;
        } else 
        {
            // Case where only TUT/LAB is provided and no LEC
            // E.g. CPSC 567 TUT 01
            labNumber = Integer.valueOf(str[3]);
            CourseLab lab = new CourseLab(name, lectureNumber, labNumber, "LAB");
            return lab;
        }
    }

    // Creating a Slot type from a string array
    public static Slot createSlot(String[] str, String type)
    {
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

        Slot slot = new Slot(daySeries, slotType, hours, minutes);

        return slot;

    }

    public static Slot createSlotMinMax(String[] str, String type)
    {
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
        int coursemax=-1, coursemin=-1, labmax=-1, labmin=-1;
        if (type.equals("LEC"))
        {
            slotType = SlotType.COURSE;
            coursemax = Integer.valueOf(str[2].replaceAll("\\s+",""));
            coursemin = Integer.valueOf(str[3].replaceAll("\\s+",""));
        } else 
        {
            slotType = SlotType.LAB;
            labmax = Integer.valueOf(str[2].replaceAll("\\s+",""));
            labmin = Integer.valueOf(str[3].replaceAll("\\s+",""));
        }

        String[] time = str[1].replaceAll("\\s+","").split(":");
        int hours = Integer.valueOf(time[0]);
        int minutes = Integer.valueOf(time[1]);

        Slot slot = new Slot(daySeries, slotType, hours, minutes, coursemin, coursemax, labmin, labmax); 

        return slot;
    }

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

                        Slot slot = createSlotMinMax(temp, "LEC");
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

                        Slot slot = createSlotMinMax(temp, "LAB");
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

                        CourseLab course = createCourseLab(temp);
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

                        CourseLab lab = createCourseLab(temp);
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
                        String[] twoCourses = line.split(",\\s+");

                        String[] course1Arr = twoCourses[0].split("\\s+");
                        String[] course2Arr = twoCourses[1].split("\\s+");

                        // Creating course1 and course 2
                        CourseLab course1 = createCourseLab(course1Arr);
                        CourseLab course2 = createCourseLab(course2Arr);

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
                        String[] delimited = line.split(",\\s+", 2);

                        String[] courseArr = delimited[0].split("\\s+");
                        String[] slotArr = delimited[1].split(",\\s+");

                        CourseLab courselab = createCourseLab(courseArr);
                        Slot slot = createSlot(slotArr, courselab.getType());

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
                        String[] delimited = line.split(",\\s+");

                        String[] slotArr = {delimited[0], delimited[1]};
                        String[] courseArr = delimited[2].split("\\s+");
                        String rankingStr = delimited[3];


                        CourseLab courselab = createCourseLab(courseArr);
                        Slot slot = createSlot(slotArr, courselab.getType()); 
                        int ranking = Integer.valueOf(rankingStr);

                        Triplet<Slot, CourseLab, Integer> t = new Triplet<Slot, CourseLab, Integer>(slot, courselab, ranking);
                        list.add(t);

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
                        String[] twoCourses = line.split(",\\s+");

                        String[] course1Arr = twoCourses[0].split("\\s+");
                        String[] course2Arr = twoCourses[1].split("\\s+");

                        CourseLab course1 = createCourseLab(course1Arr);
                        CourseLab course2 = createCourseLab(course2Arr);

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
                        // Reached EOF
                        if (line==null) break;

                        // Delimiting by comma (only first instance)
                        String[] delimited = line.split(",\\s+", 2);

                        String[] courseArr = delimited[0].split("\\s+");
                        String[] slotArr = delimited[1].split(",\\s+");

                        CourseLab courselab = createCourseLab(courseArr);
                        Slot slot = createSlot(slotArr, courselab.getType());

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



    // Testing (Will remove later)

    public static void main(String[] args) {

        String txtfile = "./com/Main/ShortExample.txt";

        ArrayList<Slot> slotList = parseCourseLabSlots(txtfile);

        ArrayList<CourseLab> list = parseCourseLab(txtfile);

        ArrayList<Pair<CourseLab, CourseLab>> notCompatibleList = parseNotCompatible(txtfile);

        ArrayList<Pair<CourseLab, Slot>> unwantedList = parseUnwanted(txtfile);

        ArrayList<Triplet<Slot, CourseLab, Integer>> preferencesList = parsePreferences(txtfile);

        ArrayList<Pair<CourseLab, CourseLab>> pairList = parsePair(txtfile);

        ArrayList<Pair<CourseLab, Slot>> partialAssignList = parsePartialAssignments(txtfile);
        String string[] = {"CPSC 433 LEC 01 TUT 01"};
        createCourseLab(string);

        //for (Slot s: slotList)
        //{
        //    System.out.println(s.getDaySeries());
        //}


//        for (CourseLab i: list)
//        {
//            System.out.println(i.getName());
//        }

        //for (Pair<CourseLab, CourseLab> p: notCompatibleList)
        //{
        //    CourseLab c1 = p.getKey();
        //    CourseLab c2 = p.getValue();

        //    System.out.println(c1.getName() + " " + c2.getName());
        //}


        //for (Pair<CourseLab, Slot> p: unwantedList)
        //{
        //    CourseLab c1 = p.getKey();
        //    Slot c2 = p.getValue();
        //    System.out.println(c1.getName() + " " + c2.getDaySeries());
        //}

        //for (Triplet<Slot, CourseLab, Integer> t: preferencesList)
        //{
        //    Slot s = t.getSlot();
        //    CourseLab c = t.getCourseLab();
        //    int r = t.getRanking();
        //    System.out.println(s.getDaySeries() + c.getName() + r);
        //}

        //for (Pair<CourseLab, CourseLab> p: pairList)
        //{
        //    CourseLab c1 = p.getKey();
        //    CourseLab c2 = p.getValue();

        //    System.out.println(c1.getName() + " " + c2.getName());
        //}

        //for (Pair<CourseLab, Slot> p: partialAssignList)
        //{
        //    CourseLab c1 = p.getKey();
        //    Slot c2 = p.getValue();
        //    System.out.println(c1.getName() + " " + c2.getDaySeries());
        //}


    }


}


