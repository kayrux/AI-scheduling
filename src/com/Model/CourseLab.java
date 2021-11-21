package com.Model;

public class CourseLab {

	private String name;
	private int lectureNumber;
	private int labNumber;
	
	public CourseLab(String name, int lectureNumber, int labNumber) {
		this.name = name.toUpperCase();
		this.lectureNumber = lectureNumber;
		this.labNumber = labNumber;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getLectureNumber() {
		return this.lectureNumber;
	}
	
	public int getLabNumber() {
		return this.labNumber;
	}
}
