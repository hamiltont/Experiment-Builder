/**
 * 
 */
package edu.vanderbilt.psychology.model.properties;

import javax.swing.JComponent;

import edu.vanderbilt.psychology.gui.sideBar.Section;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.DataCapture;
import edu.vanderbilt.psychology.model.reactor.ActionListener;

/**
 * <p>
 * {@link Property}s are OO representations of properties that
 * {@link SlideElement} may contain. For example, a {@link SlideElement} may
 * have {@link Property}s for {@link Appearance}, {@link Movement}, and
 * {@link Position}. Some {@link SlideElement}s may have all of these
 * properties, while other {@link SlideElement}s may only have a subset
 * </p>
 * 
 * <p>
 * All {@link Property}s create their own {@link Section} of the GUI, and can
 * register for events that originate in that {@link Section}. This allows each
 * {@link Property} to create it's own part of the user interface.
 * </p>
 * 
 * <p>
 * Developers should strive for all {@link Property}s to be fully independent of
 * other {@link Property}s. This helps avoid complex edge case logic where a
 * {@link Property} has to check for another {@link Property}. For example,
 * having a Size {@link Property} and a Height {@link Property} is probably a
 * bad idea. Perhaps in the future {@link Property}s will be able to interface
 * and communicate, but for now they cannot. If a {@link Property} is not able
 * to be independent of another {@link Property}, the developer should consider
 * extending the existing {@link Property} so that any edge case logic is
 * contained internally
 * </p>
 * 
 * <h4>TODO - To implement</h4>
 * <ul>
 * <li>Property chaining</li>
 * <li>Adding public abstract String getName() method for logging</li>
 * </ul>
 * 
 * <p>
 * {@link Property}s can also register to listen for events, as all
 * {@link Property}s implement {@link ActionListener}. This allows the setting of
 * {@link Property} values to be chained. For example, if something happens in
 * Property A, then Property B can be set to react to this change. I am not sure
 * that this implementation is the best (aka having the Propertys be the
 * EventListeners), so for now this just serves as an indicator that this sort
 * of chaining behaviour should be possible
 * </p>
 * 
 * <p>
 * At some point I need to add a getName() method for logging. This is to be
 * used for the {@link DataCapture}, inside of the log field 'name'. Rather than
 * adding a method to the Property only though, there will likely be some sort
 * of "data sender" interface that all Propertys will inherit from. All
 * "data senders" must have a getName() method
 * </p>
 * 
 * <h5>Note</h5> If a {@link Property} takes up a lot of memory, then it would
 * be useful to make that property have an internal model that can be shared
 * amongst multiple instances of a {@link Property} class
 * 
 * <br>
 * <br>
 * 
 * @author Hamilton Turner
 * 
 */
// TODO - There is a mixing of the builder code and player code here. Some of
// these methods are only needed for the builder, some of them are only needed
// for the player
public abstract class Property implements ActionListener, Cloneable {

	/**
	 * Will be used in the logging. This returns the value that will go into the
	 * log field 'type'
	 * 
	 * @return "Property"
	 * 
	 * @see DataCapture
	 */
	public final String getType() {
		return "Property";
	}

	/**
	 * Requests the user interface {@link Section} for this {@link Property}.
	 * The {@link Property} can (and probably should) register for GUI events
	 * such as mouse clicks that originate from this {@link Section}. This
	 * allows each {@link Property} to create and react to it's own part of the
	 * user interface.
	 */
	public abstract Section getSection();
}
