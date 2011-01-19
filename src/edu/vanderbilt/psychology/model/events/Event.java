package edu.vanderbilt.psychology.model.events;

public class Event {

	private EventType mType;

	protected Event(EventType type) {
		mType = type;
	}

	public EventType getType() {
		return mType;
	}
}
