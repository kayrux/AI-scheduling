package com.Model;

public class Slot {

	private SlotType slotType;	// Might not need this (Keep two separate list for course slots and lab slots)
	private DaySeries daySeries;
	private Time time;
	private int coursemax;
	private int labmax;
	
	public Slot(DaySeries daySeries, int hours, int minutes) {
		this.daySeries = daySeries;
		this.time = new Time(hours, minutes);
		initDefaultCourseLabMax();
	}
	
	public Slot(DaySeries daySeries, int hours, int minutes, int coursemax, int labmax) {
		this.daySeries = daySeries;
		this.time = new Time(hours, minutes);
		this.coursemax = coursemax;
		this.labmax = labmax;
	}
	
	// If no coursemax or labmax is specified, the default values are zero
	private void initDefaultCourseLabMax() {
		this.coursemax = 0;
		this.labmax = 0;
	}
	
	// TO BE IMPLEMENTED
	public String toString() {
		return "";
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
}
