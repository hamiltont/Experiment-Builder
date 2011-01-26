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
public interface ActionListener {

	/**
	 * Called when the {@link EventManager} wants to pass this {@link ActionListener} an {@link Action}
	 * 
	 * @param e
	 *            the action
	 */
	public void receiveAction(Action e);
}
