package edu.vanderbilt.psychology.model;

public class MutableInt {
	
	private Integer mValue;
	
	public void setValue(int value) {
		mValue = new Integer(value); 
	}
	
	public Integer getValue() {
		return mValue;
	}
}
