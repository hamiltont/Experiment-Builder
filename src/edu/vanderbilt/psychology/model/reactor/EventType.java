package edu.vanderbilt.psychology.model.reactor;

/**
 * Stores a unique string that represents a type of event. The 'standard' event
 * identifiers have yet to be defined, but there is an attempt being made here
 * to remedy that
 * 
 * @author hamiltont
 * 
 */
public class EventType {

	public static final EventType TYPE_SLIDE_EVENTS = new EventType("slide");
	public static final EventType TYPE_SLEEP_EVENTS = new EventType("pause");
	public static final EventType TYPE_APPEARANCE_EVENTS = new EventType(
			"appearance");
	public static final EventType TYPE_LIST_EVENTS = new EventType("list");

	private String mUniqueId;

	public EventType(String uniqueID) {
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
		if (o instanceof EventType)
			if (((EventType) o).getUniqueID().equals(mUniqueId))
				return true;

		return false;
	}

}
