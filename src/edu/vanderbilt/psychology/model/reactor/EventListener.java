/**
 * 
 */
package edu.vanderbilt.psychology.model.reactor;

import edu.vanderbilt.psychology.player.EventManager;

/**
 * Defines an abstract interface for listening to Events. May change later if we
 * need to have multiple defined types of EventListeners.
 * 
 * @author Hamilton Turner
 * 
 */
public interface EventListener {

	/**
	 * Called when the {@link EventManager} wants to pass this {@link EventListener} an {@link Event}
	 * 
	 * @param e
	 *            the event
	 */
	public void receiveEvent(Event e);
}
