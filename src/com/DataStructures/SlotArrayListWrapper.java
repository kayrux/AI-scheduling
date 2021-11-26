package com.DataStructures;

import java.util.ArrayList;

/**
 * Wrapper for add, and remove methods for an ArrayList of Slots.
 * Note that the constructor should be passed in an empty list of Slot.
 * @author skusj
 *
 */
public class SlotArrayListWrapper {
	ArrayList<Slot> slots;
	
	public SlotArrayListWrapper(ArrayList<Slot> slots) {
		if (slots.size() != 0) System.out.println("Error in SlotArrayListController. Contructor should be passed an empty list.");
		this.slots = slots;
	}
	
	/**
	 * Appends the specified element to the end of this list.
	 * @param s element to be appended to this list
	 * @param type a string which should be either "LEC" or "LAB" depending on whether a course or lab is to be added.
	 * @return true (as specified by Collection.add)
	 */
	public boolean add(Slot s, String type) {
		addCourseLab(s, type);
		return slots.add(s);
	}
	
	/** 
	 *  Replaces the first occurrence of the specified element from this list with EmptySlot, if it is present. 
	 *  If the list does not contain the element, it is unchanged.
	 * @param s The Slot to be replaced
	 * @param type A String which should be either "LEC" or "LAB" depending on whether a course or lab is to be removed.
	 * @return true if the list contains the specified Slot.
	 */
	public boolean remove(Slot s, String type) {
		int i = slots.indexOf(s);
		if (i != -1) {
			slots.set(i, new EmptySlot());
			removeCourseLab(s, type);
			return true;
		}
		return false;
	}
	
	/**
	 * Replaces the Slot at the specified position in this list with EmptySlot.
	 * @param index The index of the Slot to be removed.
	 * @param type The type of CourseLab to be removed.
	 * @return The Slot that was removed from the list.
	 */
	public Slot remove(int index, String type) {
		Slot s = slots.get(index);
		slots.set(index, new EmptySlot());
		slots.remove(index);
		removeCourseLab(s, type);
		return s;
	}
	
	/**
	 * Removes a CourseLab from the given Slot.
	 * @param s The Slot to which a course or lab will be removed from.
	 * @param type a string which should be either "LEC" or "LAB" depending on whether a course or lab is to be removed.
	 * @return true if successful
	 */
	private int removeCourseLab(Slot s, String type) {
		if (type.equals("LEC")) return s.removeCourse();
		else if (type.equals("LAB")) return s.removeLab();
		return -1;
	}
	
	/**
	 * Adds a CourseLab from the given Slot. Returns 1 if successful, -1 otherwise.
	 * @param s The Slot to which a course or lab will be added to.
	 * @param type a string which should be either "LEC" or "LAB" depending on whether a course or lab is to be added.
	 * @return true if successful
	 */
	private int addCourseLab(Slot s, String type) {
		if (type.equals("LEC")) return s.addCourse();
		else if (type.equals("LAB")) return s.addLab();
		return -1;
	}
	
 }
