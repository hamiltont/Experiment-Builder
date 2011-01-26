package edu.vanderbilt.psychology.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import edu.vanderbilt.psychology.model.reactor.Event;
import edu.vanderbilt.psychology.model.reactor.EventListener;
import edu.vanderbilt.psychology.model.reactor.EventType;

/**
 * Implements a publisher / subscriber system for various events
 * 
 * @author hamiltont
 * 
 */
// TODO implement a better unique identifier method
public class EventManager {

	private static EventManager mInstance;

	/**
	 * Contains the {@link EventListener}s interested in various {@link Event}s.
	 * The keys are the type identifiers of the Events, and the values are an
	 * {@link ArrayList} of the {@link EventListener}s that are interested in
	 * {@link Event}s with that type. Further filtering is left to the
	 * {@link EventListener}s
	 */
	private HashMap<EventType, HashSet<EventListener>> mEventListeners = new HashMap<EventType, HashSet<EventListener>>();

	private HashSet<EventListener> mAllEventListeners = new HashSet<EventListener>(
			4);

	private EventManager() {
	}

	public static EventManager getInstance() {
		if (mInstance == null)
			mInstance = new EventManager();
		return mInstance;
	}

	/**
	 * Registers a new {@link EventType}. Can be safely called repeatedly with
	 * the same {@link EventType}
	 * 
	 * @param type
	 *            An {@link EventType} you are interested in registering
	 * @return false if the {@link EventManager} already knew about this
	 *         {@link EventType}, true if this {@link EventType} was previously
	 *         unknown
	 */
	private boolean addEventType(EventType newEventType) {
		if (mEventListeners.keySet().contains(newEventType))
			return false;

		mEventListeners.put(newEventType, null);
		return true;
	}

	/**
	 * Sends an {@link Event} to any interested subscribers.
	 * 
	 * @param e
	 * @return the number of {@link EventListener}s this event was sent to (if
	 *         and {@link EventListener}s is registered for both specific types
	 *         of events and for all events, it will be counted twice).
	 */
	public int sendEvent(Event e) {
		if (addEventType(e.getType()))
			return 0;

		HashSet<EventListener> observers = mEventListeners.get(e.getType());

		if (observers == null)
			return 0;

		for (EventListener el : observers)
			el.receiveEvent(e);

		for (EventListener el : mAllEventListeners)
			el.receiveEvent(e);

		return (observers.size() + mAllEventListeners.size());
	}

	public void registerEventObserver(EventType interestingEventTypes,
			EventListener eventObserver) {
		addEventType(interestingEventTypes);

		if (mEventListeners.get(interestingEventTypes) == null)
			mEventListeners.put(interestingEventTypes,
					new HashSet<EventListener>(5));

		mEventListeners.get(interestingEventTypes).add(eventObserver);
	}

	/**
	 * After calling this method, all {@link Event}s regardless of
	 * {@link EventType} will be sent to the specified {@link EventListener}. If
	 * you call this method and
	 * {@link EventManager#registerEventObserver(EventType, EventListener)} with
	 * the same {@link EventListener}, that {@link EventListener} will receive
	 * some {@link Event}s more than once
	 * 
	 * @param listener
	 */
	public void registerObserverForAllEvents(EventListener listener) {
		mAllEventListeners.add(listener);
	}

}
