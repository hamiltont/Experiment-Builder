package edu.vanderbilt.psychology.gui.toolBar;

import java.util.ArrayList;

/**
 * Experiment Builder list object.
 * 
 * @author sethfri
 *
 * @param <T>
 */
public class EBList<T> extends ArrayList<T> {
	String listName;
	
	// Not sure if this constructor is necessary
	public EBList() {
	}
	
	public EBList(String name) {
		setName(name);
	}

	public int getLocation() {
		return this.indexOf(this.iterator());
	}
	
	public void setLocation(int index) {
		while (!this.iterator().next().equals(this.get(index))) {
			this.iterator().next();
		}
	}
	
	public void setName(String newName) {
		listName = newName;
	}
	
	public String getName() {
		return listName;
	}
	
	private static final long serialVersionUID = 6350624961037204882L;
}
