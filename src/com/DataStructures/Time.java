package com.DataStructures;

public class Time {

	private final int maxHours = 20;
	private final int minHours = 0;
	private final int maxMinutes = 59;
	private final int minMinutes = 0;
	
	private int hours;
	private int minutes;
	
	public Time(int hours, int minutes) {
		initHours(hours);
		initMinutes(minutes);
	}
	
	private void initHours(int h) {
		if (h <= maxHours) {
			this.hours = h;
		} else {
			this.hours = 0;
			System.out.println("Invalid Time: Hours");
		}
	}
	
	private void initMinutes(int m) {
		if (m <= maxMinutes) {
			this.minutes = m;
		} else {
			this.minutes = 0;
			System.out.println("Invalid Time: Minutes");
		}
	}
	
	/**
	 * Returns the time as a String
	 */
	@Override
	public String toString() {
		String time = hours + ":";
		if (minutes < 10) time += "0";
		return time + minutes;
	}
	
	public int getHours() {
		return this.hours;
	}
	
	public int getMinutes() {
		return this.minutes;
	}
	
}


