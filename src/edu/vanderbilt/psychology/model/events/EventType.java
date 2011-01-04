package edu.vanderbilt.psychology.model.events;

/**
 * Stores a unique string that represents a type of event. The 'standard' event
 * identifiers have yet to be defined, but there is an attempt being made here
 * to remedy that
 * 
 * @author hamiltont
 * 
 */
public class EventType {

	public static final String TYPE_SLIDE_EVENTS = "slide";
	
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
