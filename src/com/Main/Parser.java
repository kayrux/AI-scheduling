package com.Main;

import java.io.*;
//import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javafx.util.Pair;

import com.Model.CourseLab;

public class Parser {

    public static CourseLab createCourseLab(String[] str) 
    {
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


    public static ArrayList<CourseLab> parseCourseLab() {
        ArrayList<CourseLab> list = new ArrayList<CourseLab>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./com/Main/ShortExample.txt"));
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
        return list;
    }

    public static ArrayList<Pair<CourseLab, CourseLab>> parseNotCompatible(String path) {
        ArrayList<Pair<CourseLab, CourseLab>> list = new ArrayList<Pair<CourseLab, CourseLab>>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
            //reader = new BufferedReader(new FileReader("./com/Main/ShortExample.txt"));
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
                        String[] twoCourses = line.split(", ");

                        String[] course1Arr = twoCourses[0].split(" ");
                        String[] course2Arr = twoCourses[1].split(" ");

                        //System.out.println(course1[1]);

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



    // Testing (Will remove later)

    public static void main(String[] args) {
        System.out.println("Hello");

        ArrayList<CourseLab> list = parseCourseLab();

        ArrayList<Pair<CourseLab, CourseLab>> notCompatibleList = parseNotCompatible("./com/Main/ShortExample.txt");

        for (Pair<CourseLab, CourseLab> p: notCompatibleList)
        {
            CourseLab c1 = p.getKey();
            CourseLab c2 = p.getValue();
            System.out.println(c1.getName() + " " + c2.getName());
        }
            
       
        



        //for (CourseLab i: list)
        //{
        //    System.out.println(i.getName());
        //}

    }


}


