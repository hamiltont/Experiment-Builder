package edu.vanderbilt.psychology.model.reactor;

/**
 * Stores a unique string that represents a type of event. The 'standard' event
 * identifiers have yet to be defined, but there is an attempt being made here
 * to remedy that
 * 
 * @author hamiltont
 * 
 */
public class ActionType {

	public static final ActionType TYPE_SLIDE_EVENTS = new ActionType("slide");
	public static final ActionType TYPE_SLEEP_EVENTS = new ActionType("pause");
	public static final ActionType TYPE_APPEARANCE_EVENTS = new ActionType(
			"appearance");
	public static final ActionType TYPE_LIST_EVENTS = new ActionType("list");

	private String mUniqueId;

	public ActionType(String uniqueID) {
		mUniqueId = uniqueID;
	}

	public String getUniqueID() {
		return mUniqueId;
	}

	@Override
	public int hashCode() {
		return mUniqueId.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ActionType)
			if (((ActionType) o).getUniqueID().equals(mUniqueId))
				return true;

		return false;
	}

}
