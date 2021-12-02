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
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() == CourseLab.class) {
			CourseLab c = (CourseLab) o;
			if (this.hash.equals(c.getHash())) return true;
			else return false;
		}
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
