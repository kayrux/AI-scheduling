package com.Main;

import java.io.*;
//import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import com.Model.CourseLab;
import java.util.ArrayList;

// Testing branch push

public class Parser {

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
                        String name = temp[0] + temp[1];
                        // -1 for no tutorial
                        CourseLab course = new CourseLab(name, Integer.valueOf(temp[3]), -1, "LEC");
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
                        String name = temp[0] + temp[1];


                        // Two cases can occur:
                            // SENG 311 LEC 01 TUT 01 (Includes lectureNumber)
                            // CPSC 567 TUT 01 (Does not have lectureNumber)
                        // Checking size of array for which case
                        if (temp.length > 4) 
                        {
                            // First case
                            CourseLab lab = new CourseLab(name, Integer.valueOf(temp[3]), Integer.valueOf(temp[5]), "LAB");
                            list.add(lab);
                        } else 
                        {
                            // Second case
                            // -1 for no lecture 
                            CourseLab lab = new CourseLab(name, -1, Integer.valueOf(temp[3]), "TUT");
                            list.add(lab);
                        }
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
    
    //public static void main(String[] args) {
    //    System.out.println("Hello");

    //    ArrayList<CourseLab> list = parseCourseLab();

    //    for (CourseLab i: list)
    //    {
    //        System.out.println(i.getName());
    //    }

    //}


}


