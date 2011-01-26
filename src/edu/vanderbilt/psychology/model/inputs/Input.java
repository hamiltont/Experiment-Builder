/**
 * 
 */
package edu.vanderbilt.psychology.model.inputs;

import edu.vanderbilt.psychology.model.DataCapture;
import edu.vanderbilt.psychology.model.Experiment;
import edu.vanderbilt.psychology.model.properties.Property;
import edu.vanderbilt.psychology.model.reactor.EventListener;

/**
 * Defines a superclass for any sort of user input sources that we do not have
 * absolute control over. {@link Input}s are on continually throughout the
 * course of an {@link Experiment}, while {@link Property}s are sometimes
 * available and sometimes not
 * 
 * An {@link Input} can listen for events, in a manner similar to
 * {@link Property}s, and react accordingly
 * 
 * <p>
 * At some point I need to add a getName() method for logging. This is to be
 * used for the {@link DataCapture}, inside of the log field 'name'. Rather than
 * adding a method to the Input only though, there will likely be some sort
 * of "data sender" interface that all Inputs will inherit from. All
 * "data senders" must have a getName() method
 * </p>
 * 
 * @author Hamilton Turner
 * 
 */
public abstract class Input implements EventListener {

	public final String getType() {
		return "Input";
	}
}
