package com.Model;

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
	
	// TO BE IMPLEMENTED
	// This should print the time in the desired format
	public String toString() {
		return "";
	}
	
	public int getHours() {
		return this.hours;
	}
	
	public int getMinutes() {
		return this.minutes;
	}
}
