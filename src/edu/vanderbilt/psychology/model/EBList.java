package edu.vanderbilt.psychology.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds an Experiment Builder list object, which uses an internal ArrayList
 * to hold elements and expands the features of an ArrayList in that each
 * EBList has its own name and a pointer that points to the current position
 * of the list. The class is data-based, and is thus used for the model.
 * 
 * @author sethfri
 *
 * @param <T>
 */
public class EBList<T> {
	 // TODO - Implement the List interface
	
	private List<T> mInternalList = new ArrayList<T>();
	private int mCurrentListPos = -1;
	private String mName;
	
	public EBList(String name) {
		setName(name);
	}
	
	public void add(T element) {
		mInternalList.add(element);
	}

	public int getLocation() {
		return mCurrentListPos;
	}
	
	public void setLocation(int index) {
		mCurrentListPos = index;
	}
	
	public void setName(String newName) {
		mName = newName;
	}
	
	public String getName() {
		return mName;
	}
	
	public void reset() {
		mCurrentListPos = 0;
	}
	
	public void incrementPosition() {
		mCurrentListPos++;
	}
	
	public void decrementPosition() {
		mCurrentListPos--;
	}
	
	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 6350624961037204882L;
}
