package com.DataStructures;

public class Slot {

	private SlotType slotType;
	private DaySeries daySeries;
	private Time time;
	private int courseCount;
	private int labCount;
	
	private int coursemax;
	private int labmax;
	private int coursemin;
	private int labmin;
	
	public Slot(DaySeries daySeries, SlotType slotType, int hours, int minutes) {
		this.daySeries = daySeries;
		this.slotType = slotType;
		this.time = new Time(hours, minutes);
		initDefaults();
	}
	
	public Slot(DaySeries daySeries, SlotType slotType, int hours, int minutes, int coursemin, int coursemax, int labmin, int labmax) {
		this.daySeries = daySeries;
		this.slotType = slotType;
		this.time = new Time(hours, minutes);
		this.coursemin = coursemin;
		this.coursemax = coursemax;
		this.labmin = labmin;
		this.labmax = labmax;
		initCourseLabCount();
		checkSlotType();
		if (daySeries == DaySeries.FR && coursemax > 0) {
			System.out.println("DaySeries: FR can only contain LABs");
			coursemax = 0;
		}
	}
	
	
	/**
	 * Checks the slot type and makes sure the labmax and coursemax are set accordingly
	 */
	private void checkSlotType() {
		if ((this.slotType == SlotType.COURSE) && (labmax > 0)) {
			System.out.println("labmax should be 0 when slotType == COURSE");
			setLabMax(0);
		}
		else if ((this.slotType == SlotType.LAB) && (coursemax > 0)) {
			System.out.println("coursemax should be 0 when slotType == LAB");
			setCourseMax(0);
		}
	}
	
	// Adds a course to the slot. Returns 1 if the add was successful, -1 otherwise.
	public int addCourse() {
		if (courseSlotsAvailable() == true) {
			courseCount++;
			return 1;
		}
		return -1;
	}
	
	// Adds a lab to the slot. Returns 1 if the add was successful, -1 otherwise.
	public int addLab() {
		if (labSlotsAvailable() == true) {
			labCount++;
			return 1;
		}
		return -1;
	}
	
	// Removes a course from the slot. Returns 1 if the add was successful, -1 otherwise.
	public int removeCourse() {
		if (courseCount > 0) {
			courseCount --;
			return 1;
		} 
		//System.out.println("Error in Slot.java! Removed a course from empty list");
		return -1;
	}
	
	// Removes a lab from the slot. Returns 1 if the add was successful, -1 otherwise.
	public int removeLab() {
		if (labCount > 0) {
			labCount --;
			return 1;
		}
		//System.out.println("Error in Slot.java! Removed a lab from empty list");
		return -1;
	}
	// Returns true if there is a course slot available
	public boolean courseSlotsAvailable() {
		if (courseCount < coursemax) return true;
		else return false;
	}
	
	// Returns true if there is a lab slot available
	public boolean labSlotsAvailable() {
		if (labCount < labmax) return true;
		else return false;
	}
	
	// If no coursemax or labmax is specified, the default values are zero
	private void initDefaults() {
		initCourseLabCount();
		this.coursemax = 0;
		this.labmax = 0;
		this.coursemin = 0;
		this.labmin = 0;
	}
	
	private void initCourseLabCount() {
		this.courseCount = 0;
		this.labCount = 0;
	}
	
	// TO BE IMPLEMENTED
	public String toString() {
		return "";
	}
	
	/**
	 * Returns a String consisting of the Day and Time of the Slot
	 * @return a String in the format: "Day Time"
	 */
	public String getDayAndTime() {
		return daySeries.toString() + " " + time.toString();
	}
	
	public void setLabMax(int max) {
		if (max >= 0) this.labmax = max;
	}
	
	public void setCourseMax(int max) {
		if (max >= 0) this.coursemax = max;
	}
	
	public void setCoursemin(int coursemin) {
		this.coursemin = coursemin;
	}

	public void setLabmin(int labmin) {
		this.labmin = labmin;
	}
	
	public DaySeries getDaySeries() {
		return daySeries;
	}

	public Time getTime() {
		return time;
	}

	public int getCoursemax() {
		return coursemax;
	}

	public int getLabmax() {
		return labmax;
	}
	
	public int getCoursemin() {
		return coursemin;
	}

	public int getLabmin() {
		return labmin;
	}

	public SlotType getSlotType() {
		return slotType;
	}
}
