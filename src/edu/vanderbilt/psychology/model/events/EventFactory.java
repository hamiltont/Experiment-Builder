package edu.vanderbilt.psychology.model.events;

public class EventFactory {

	/**
	 * Given a type of desired event, this returns an event of that type
	 * 
	 * @param eventType
	 * @return
	 */
	public static Event buildEvent(String eventType) {
		EventType type = new EventType(eventType);
		Event e = new Event(type);
		return e;
	}
	
	public static Event buildEvent(EventType type) {
		Event e = new Event(type);
		return e;
	}
}
