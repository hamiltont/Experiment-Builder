package edu.vanderbilt.psychology.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates the list database. This is the only location in the code that keeps track 
 * of all of the EBLists. There are a few variants of EBLists, the file reference list 
 * and the string list, and the database is able to call an EBList by name, as well as
 * give the names of all current EBLists.
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
	
	@SuppressWarnings("unchecked")
	public EBList getByName(String name) {
		for (int i = 0; i < stringLists_.size() - 1; i++) {
			if (stringLists_.get(i).getName().equals(name)) {
				return stringLists_.get(i);
			}
		}
		throw new IllegalArgumentException("There is no EBList with the name " + name);
	}
	
	@SuppressWarnings("unchecked")
	public List getNames() {
		ArrayList listOfNames = new ArrayList();
		
		for (EBList<String> currentList : stringLists_) {
			listOfNames.add(currentList.getName());
		}
		for (EBList<File> currentList : fileReferenceLists_) {
			listOfNames.add(currentList.getName());
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
