package edu.vanderbilt.psychology.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds an Experiment Builder list object, which uses an internal ArrayList
 * to hold elements and expands the features of an ArrayList in that each
 * EBList has its own name and a pointer that points to the current position
 * of the list. 
 * 
 * @author sethfri
 *
 * @param <T>
 */
// TODO - Implement the List interface
public class EBList<T> {
	
	private List<T> mInternalList = new ArrayList<T>();
	private int mCurrentListPos = 0;
	private String mName;
	
	@SuppressWarnings("unused")
	private EBList() {}
	
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
		if (index < 0 || index > (mInternalList.size() - 1))
			throw new IndexOutOfBoundsException("Unable to set a location of " + 
					index + ". List size is " + mInternalList.size());
		
		mCurrentListPos = index;
	}
	
	public void setName(String newName) {
		mName = newName;
	}
	
	public String getName() {
		return mName;
	}
	
	public T get() {
		return mInternalList.get(mCurrentListPos);
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
