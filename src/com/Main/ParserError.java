package com.Main;

public class ParserError {

    // Standard exit message 
    public static void exitMessage()
    {
        System.out.println("Exiting Search");
        System.exit(0);
    }


    // Occurs when the DaySeries from "Course slots:" or "Lab slots:" are not MO, TU or FR
    // E.g: TH, 8:00, 3, 2
    public static void invalidDaySeries(String daySeries)
    {
        System.out.println("Error occurred while parsing \"Course slots:\" or \"Lab slots:\": " + daySeries + " is not MO, TU or FR");
        exitMessage();
    }

    // Occurs from "Course slots:" or "Lab slots:" when hour < 0 or hour > 24 in the time provided 
    public static void invalidHour(int hour, String str)
    {
            System.out.println("Error occurred while parsing " + str + ". Time (hour) needs to be between 0 and 24." + " A value of " + hour + " was provided.");
            exitMessage();
    }

    // Occurs from "Course slots:" or "Lab slots:" when minute < 0 or minute > 59 in the time provided 
    public static void invalidMinute(int minutes, String str)
    {
        System.out.println("Error occurred while parsing " + str + ". Time (minutes) needs to be between 0 and 59." + " A value of " + minutes + " was provided.");
        exitMessage();
    }

    // E.g. MO, 8:00, 1, 2
    public static void invalidCoursemaxmin(int coursemax, int coursemin)
    {
        System.out.println("Error occurred while parsing \"Course slots:\" or \"Lab slots:\": " + "Coursemax needs to be greater than coursemin." + " Parsed Coursemax: " + coursemax + " Parsed Coursemin: " + coursemin);
        exitMessage();
    }

    // E.g. MO, 8:00, 1, 2
    public static void invalidLabmaxmin(int labmax, int labmin)
    {
        System.out.println("Error occurred while parsing \"Course slots:\" or \"Lab slots:\": " + "Labmax needs to be greater than labmin." + " Parsed Labmax: " + labmax + " Parsed Labmin: " + labmin);
        exitMessage();
    }

    // E.g. CPSC 433 LEC -1
    public static void invalidCoursenum(int courseNum, String str)
    {
        System.out.println("Error occurred while parsing " + str + ". Lecture number needs to be greater than 1." + " Parsed Lecture Number: " + courseNum);
        exitMessage();

    }

    // E.g. CPSC 433 TUT -1
    public static void invalidLabnum(int labNum, String str)
    {
        System.out.println("Error occurred while parsing " + str + ". Lab number needs to be greater than 1." + " Parsed Lab Number: " + labNum);
        exitMessage();
    }

    // E.g. CPSC 433 INVALID 01
    public static void invalidType(String type, String name)
    {
        System.out.println("Error occurred while parsing " + name + "." + "Type needs to be LEC, LAB or TUT." + " Parsed Type: " + type);
        exitMessage();
    }

    // E.g (in Not compatible): CPSC 433 LEC 01, CPSC 433 LEC 02, CPSC 433 LEC 03
    public static void invalidNumofcourselab(int num)
    {
        System.out.println("Error occurred while parsing \"Not compatible:\": " + "Needs to have 2 courses or labs provided." + " Parsed number: " + num);
        exitMessage();
    }

    // E.g. (in Preferences): TU, 9:00, CPSC 433 LEC 01, 1100
    public static void invalidRanking(int ranking)
    {
        System.out.println("Error occurred while parsing \"Preferences:\": " + "Needs to have a ranking between 1 and 10. " + "Parsed ranking: " + ranking);
        exitMessage();
    }

    // E.g. TU, 9:00, hi
    public static void invalidNumSlot(String name, String[] str)
    {
        String temp = "";
        for (int i=0; i<str.length; i++)
        {
            temp += str[i] + " ";
        }
        System.out.println("Error occurred while parsing slots in " + name + "." + "Error here: " + temp);
        exitMessage();
    }

    // E.g. TU, 9:00, 4, 2, hi
    public static void invalidNumSlotcoursemaxmin(String name, String[] str)
    {
        String temp = "";
        for (int i=0; i<str.length; i++)
        {
            temp += str[i] + " ";
        }
        System.out.println("Error occurred while parsing slots in " + name + "." + "Error here: " + temp);
        exitMessage();
    }


    // E.g. CPSC 433 433 LEC 01 TUT 01
    public static void invalidNumcourselab(String name, String[] str)
    {
        String temp = "";
        for (int i=0; i<str.length; i++)
        {
            temp += str[i] + " ";
        }
        System.out.println("Error occurred while parsing courselab in " + name + "." + "Error here: " + temp);
        exitMessage();
    }

    // E.g. TU, 9:00, CPSC 433 LEC 01, 10, hi
    public static void invalidPreferences(String[] str)
    {
        String temp = "";
        for (int i=0; i<str.length; i++)
        {
            temp += str[i] + " ";
        }

        System.out.println("Error occurred while parsing \"Preferences:\": " + "Needs 4 elements in the comma delimited array, there are " + str.length + " in the array." + "Error here: " + temp);
        exitMessage();
    }

    // E.g. SENG 311 LEC 01, CPSC 567 LEC 01, CPSC 433 LEC 01
    public static void invalidPair(String[] str)
    {
        String temp = "";
        for (int i=0; i<str.length; i++)
        {
            temp += str[i] + " ";
        }

        System.out.println("Error occurred while parsing \"Pair:\": " + "Needs 2 elements in the comma delimited array, there are " + str.length + " in the array." + "Error here: " + temp);
        exitMessage();

    }

    // E.g. SENG 311 LEC 01, MO, 8:00, 9:00
public static void invalidPartAssign(String[] str)
    {
        String temp = "";
        for (int i=0; i<str.length; i++)
        {
            temp += str[i] + " ";
        }

        System.out.println("Error occurred while parsing \"Partial assignments:\": " + "Needs 3 elements in the comma delimited array, there are " + str.length + " in the array." + "Error here: " + temp);
        exitMessage();
    }



}
