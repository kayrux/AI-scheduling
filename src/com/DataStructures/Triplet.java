package com.DataStructures;

public class Triplet<Slot, CourseLab, Integer> {

    private Slot slot;
    private CourseLab courselab;
    private int ranking;

        public Triplet(Slot slot, CourseLab courselab, int ranking) 
        {
            this.slot = slot;
            this.courselab = courselab;
            this.ranking = ranking;
        }

        public Slot getSlot() 
        {
            return slot;
        }
        
        public CourseLab getCourseLab()
        {
            return courselab;
        }

        public int getRanking()
        {
            return ranking;
        }



}
