package com.DataStructures;

public class CourseLab {

	private String name;
	private int lectureNumber;
	private int labNumber;
	
    private String hash;
    private String type;
	
	public CourseLab(String name, int lectureNumber, int labNumber, String type) {
		this.name = name.toUpperCase();
		this.lectureNumber = lectureNumber;
		this.labNumber = labNumber;
		
        this.hash = name + Integer.toString(lectureNumber) + Integer.toString(labNumber);
        this.type = type;
	}
	
	public boolean equals(CourseLab cl) {
		if (this.hash.equals(cl.getHash())) return true;
		else return false;
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

    public String getHash() {
        return this.hash;
    }

    public String getType() {
        return this.type;
    }


}
