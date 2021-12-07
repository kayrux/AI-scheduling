package com.Main;

import java.util.ArrayList;

import com.DataStructures.CourseLab;
import com.DataStructures.Pair;
import com.DataStructures.Slot;
import com.DataStructures.Triplet;
import com.Model.SetbasedSearch;

public class Main {
	private static final boolean USE_COMMAND_LINE_ARGS = false;
	/*
	 * Command line input: filename minfilled pref pair secdiff pen_coursemin pen_labsmin pen_notpaired pen_section
	 * 
	 */
	public static void main(String[] args) {
		Parser parser = new Parser();
		SetbasedSearch setBasedSearch;
		
		int minfilled = 0;
		int pref = 0;
		int pair = 0;
		int secdiff = 0;
		int pen_coursemin = 0;
		int pen_labsmin = 0;
		int pen_notpaired = 0;
		int pen_section = 0;
		
		if (args.length != 9) {
			System.out.println("Invalid command line input!");
			System.out.println("Expected: filename minfilled pref pair secdiff pen_coursemin pen_labsmin pen_notpaired pen_section");
			return;
		}
		//String txtfile = "res/test2.txt"; // Example file
		String txtfile = args[0];
		try  {
			minfilled = Integer.parseInt(args[1]);
			pref = Integer.parseInt(args[2]);
			pair = Integer.parseInt(args[3]);
			secdiff = Integer.parseInt(args[4]);
			pen_coursemin = Integer.parseInt(args[5]);
			pen_labsmin = Integer.parseInt(args[6]);
			pen_notpaired = Integer.parseInt(args[7]);
			pen_section = Integer.parseInt(args[8]);
		} catch (Exception e) {
			System.out.println("Invalid command line input!");
			System.out.println("Expected: filename minfilled pref pair secdiff pen_coursemin pen_labsmin pen_notpaired pen_section");
		}
		
		
		
		

		
		//String txtfile = "res/test2.txt"; // Example file


		


        ArrayList<Slot> slotArray = parser.parseCourseLabSlots(txtfile);
        ArrayList<CourseLab> courseLabArray = parser.parseCourseLab(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> notCompatibleArray = parser.parseNotCompatible(txtfile);
        ArrayList<Pair<CourseLab, Slot>> unwantedArray = parser.parseUnwanted(txtfile);
        ArrayList<Triplet<Slot, CourseLab, Integer>> preferencesArray = parser.parsePreferences(txtfile);
        ArrayList<Pair<CourseLab, CourseLab>> pairArray = parser.parsePair(txtfile);
        ArrayList<Pair<CourseLab, Slot>> partialAssignArray = parser.parsePartialAssignments(txtfile);
        
        //int weightMinFilled, int weightPref, int weightPair, int weightSecDiff
        Eval eval = new Eval(minfilled, pref, pair, secdiff, pen_coursemin, pen_labsmin, pen_notpaired, pen_section);
        
        setBasedSearch = new SetbasedSearch(slotArray, courseLabArray, notCompatibleArray, unwantedArray, preferencesArray, pairArray,
        		partialAssignArray, eval);
        

        ArrayList<Slot> sol = setBasedSearch.search();

        int i = 0;
		String format = "%-25s%s%n";
		
		System.out.println("Eval: " + eval.eval(sol, slotArray, courseLabArray, preferencesArray, pairArray));
		System.out.println("----------");
		for (Slot s : sol) {
			System.out.printf(format, courseLabArray.get(i).getStringFormatted(), s.getStringFormatted());
			i++;
		}
        
		/*
		int i = 0;
		
		System.out.println("Eval: " + eval.eval(sol, slotArray, courseLabArray, preferencesArray, pairArray));
		System.out.println("----------");
		for (Slot s : sol) {
			courseLabArray.get(i).printCourse();
			s.printSlot();
			i++;
		}
		*/
	}
	

	
}
