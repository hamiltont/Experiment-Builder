package edu.vanderbilt.psychology.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sun.security.action.GetLongAction;

import edu.vanderbilt.psychology.model.reactor.Action;
import edu.vanderbilt.psychology.model.reactor.ActionListener;

/**
 * Creates the list database. This is the only location in the code that keeps
 * track of all of the EBLists. There are a few variants of EBLists, the file
 * reference list and the string list, and the database is able to call an
 * EBList by name, as well as give the names of all current EBLists.
 * 
 * @author sethfri
 * @contributor hamiltont
 * 
 */
public class ListDatabase implements ActionListener {
	private static ListDatabase instance_ = null;
	private ArrayList<EBList<String>> stringLists_ = new ArrayList<EBList<String>>();
	private ArrayList<EBList<File>> fileReferenceLists_ = new ArrayList<EBList<File>>();

	public static final int ACTION_ADVANCE_LIST_POSITION_ONE = 0;
	public static final int ACTION_REWIND_LIST_POSITION_ONE = 1;

	public static ListDatabase getInstance() {
		if (instance_ == null) {
			instance_ = new ListDatabase();
		}
		return instance_;
	}

	/**
	 * Searches all list types for an {@link EBList} that has the given name.
	 * 
	 * @param name
	 * @return The found list, or null if no list has that name. It is up to the
	 *         caller to know if the returned list is of type EBList<String> or
	 *         EBList<File>
	 */
	@SuppressWarnings("unchecked")
	public EBList getByName(String name) {

		for (EBList<String> list : stringLists_)
			if (list.getName().equals(name))
				return list;

		for (EBList<File> flist : fileReferenceLists_)
			if (flist.getName().equals(name))
				return flist;

		return null;
	}

	public List<String> getNames() {
		ArrayList<String> listOfNames = new ArrayList<String>();

		for (EBList<String> currentList : stringLists_)
			listOfNames.add(currentList.getName());

		for (EBList<File> currentList : fileReferenceLists_)
			listOfNames.add(currentList.getName());

		return listOfNames;
	}

	public void addStringList(EBList<String> list) {
		stringLists_.add(list);
	}

	public void addFileReferenceList(EBList<File> list) {
		fileReferenceLists_.add(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void receiveAction(Action e) {
		if (e.getData() != null && e.getData() instanceof String) {
			String listName = (String) e.getData();
			EBList<Object> list = getByName(listName);
			switch (e.getActionCode()) {
			case ACTION_ADVANCE_LIST_POSITION_ONE:
				list.incrementPosition();
				break;
			case ACTION_REWIND_LIST_POSITION_ONE:
				list.decrementPosition();
				break;
			}
		}

	}
}
