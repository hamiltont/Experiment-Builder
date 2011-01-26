package edu.vanderbilt.psychology.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import edu.vanderbilt.psychology.model.reactor.Action;
import edu.vanderbilt.psychology.model.reactor.ActionListener;
import edu.vanderbilt.psychology.model.reactor.ActionType;

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
	 * Contains the {@link ActionListener}s interested in various {@link Action}s.
	 * The keys are the type identifiers of the Events, and the values are an
	 * {@link ArrayList} of the {@link ActionListener}s that are interested in
	 * {@link Action}s with that type. Further filtering is left to the
	 * {@link ActionListener}s
	 */
	private HashMap<ActionType, HashSet<ActionListener>> mEventListeners = new HashMap<ActionType, HashSet<ActionListener>>();

	private HashSet<ActionListener> mAllEventListeners = new HashSet<ActionListener>(
			4);

	private EventManager() {
	}

	public static EventManager getInstance() {
		if (mInstance == null)
			mInstance = new EventManager();
		return mInstance;
	}

	/**
	 * Registers a new {@link ActionType}. Can be safely called repeatedly with
	 * the same {@link ActionType}
	 * 
	 * @param type
	 *            An {@link ActionType} you are interested in registering
	 * @return false if the {@link EventManager} already knew about this
	 *         {@link ActionType}, true if this {@link ActionType} was previously
	 *         unknown
	 */
	private boolean addEventType(ActionType newEventType) {
		if (mEventListeners.keySet().contains(newEventType))
			return false;

		mEventListeners.put(newEventType, null);
		return true;
	}

	/**
	 * Sends an {@link Action} to any interested subscribers.
	 * 
	 * @param e
	 * @return the number of {@link ActionListener}s this event was sent to (if
	 *         and {@link ActionListener}s is registered for both specific types
	 *         of events and for all events, it will be counted twice).
	 */
	public int sendEvent(Action e) {
		if (addEventType(e.getType()))
			return 0;

		HashSet<ActionListener> observers = mEventListeners.get(e.getType());

		if (observers == null)
			return 0;

		for (ActionListener el : observers)
			el.receiveAction(e);

		for (ActionListener el : mAllEventListeners)
			el.receiveAction(e);

		return (observers.size() + mAllEventListeners.size());
	}

	public void registerEventObserver(ActionType interestingEventTypes,
			ActionListener eventObserver) {
		addEventType(interestingEventTypes);

		if (mEventListeners.get(interestingEventTypes) == null)
			mEventListeners.put(interestingEventTypes,
					new HashSet<ActionListener>(5));

		mEventListeners.get(interestingEventTypes).add(eventObserver);
	}

	/**
	 * After calling this method, all {@link Action}s regardless of
	 * {@link ActionType} will be sent to the specified {@link ActionListener}. If
	 * you call this method and
	 * {@link EventManager#registerEventObserver(ActionType, ActionListener)} with
	 * the same {@link ActionListener}, that {@link ActionListener} will receive
	 * some {@link Action}s more than once
	 * 
	 * @param listener
	 */
	public void registerObserverForAllEvents(ActionListener listener) {
		mAllEventListeners.add(listener);
	}

}
