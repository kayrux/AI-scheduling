package com.Model;

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
		initDefaultCourseLabMax();
		initCourseLabCount();
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
		checkSlotType(slotType);
		if (daySeries == DaySeries.FR) {
			coursemax = 0;
		}
	}
	
	private void checkSlotType(SlotType st) {
		if (st == SlotType.COURSE) setLabMax(0);
		else if (st == SlotType.LAB) setCourseMax(0);
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
		return -1;
	}
	
	// Removes a lab from the slot. Returns 1 if the add was successful, -1 otherwise.
	public int removeLab() {
		if (labCount > 0) {
			labCount --;
			return 1;
		}
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
	private void initDefaultCourseLabMax() {
		this.coursemax = 0;
		this.labmax = 0;
	}
	
	private void initCourseLabCount() {
		this.courseCount = 0;
		this.labCount = 0;
	}
	
	// TO BE IMPLEMENTED
	public String toString() {
		return "";
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
