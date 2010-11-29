package edu.vanderbilt.psychology.model;

import java.io.File;
import java.util.ArrayList;
import edu.vanderbilt.psychology.gui.toolBar.EBList;

/**
 * Creates the list database.
 * 
 * @author sethfri
 * @contributor hamiltont
 *
 */
public class ListDatabase {
	private static ListDatabase instance_ = null;
	private ArrayList<EBList<String>> stringLists_ = new ArrayList<EBList<String>>();
	private ArrayList<EBList<File>> fileReferenceLists_ = new ArrayList<EBList<File>>();
	
	public static ListDatabase getInstance() {
		if (instance_ == null) {
			instance_ = new ListDatabase();
		}
		return instance_;
	}
	
	public EBList<String> getByName(String name) {
		int j = 0;
		
		for (int i = 0; i < stringLists_.size() - 1; i++) {
			if (stringLists_.get(i).getName().equals(name)) {
				j = i;
			}
		}
		return stringLists_.get(j); // tried to return in the if statement - wouldn't work - can't remember if it should
	}
	
	public EBList<String> getNames() {
		EBList<String> listOfNames = new EBList<String>();
		
		for (int i = 0; i < stringLists_.size() - 1; i++) {
			listOfNames.add(stringLists_.get(i).getName());
		}
		return listOfNames;
	}
	
	public void addStringList(EBList<String> list) {
		stringLists_.add(list);
	}
	
	public void addFileReferenceList(EBList<File> list) {
		fileReferenceLists_.add(list);
	}
}
