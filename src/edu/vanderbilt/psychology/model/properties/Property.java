/**
 * 
 */
package edu.vanderbilt.psychology.model.properties;

import edu.vanderbilt.psychology.gui.sideBar.Section;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.DataCapture;
import edu.vanderbilt.psychology.model.EventListener;

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
 * <li>Cloning the default property</li>
 * <li>Re-setting a property if it equals the default property</li>
 * <li>Adding public abstract String getName() method for logging</li>
 * </ul>
 * 
 * <p>
 * {@link Property}s can also register to listen for events, as all
 * {@link Property}s implement {@link EventListener}. This allows the setting of
 * {@link Property} values to be chained. For example, if something happens in
 * Property A, then Property B can be set to react to this change. I am not sure
 * that this implementation is the best (aka having the Propertys be the
 * EventListeners), so for now this just serves as an indicator that this sort
 * of chaining behaviour should be possible
 * </p>
 * 
 * <p>
 * {@link Property}s should all be clonable by calling the
 * {@link Property#clone()} method. This is intended to save memory in the
 * experiment player component by reusing the default {@link Property} as much
 * as possible. (This technique is similar to reference counting). If 15
 * {@link SlideElement}s all have an {@link Appearance} property, but all of the
 * {@link SlideElement}s are using the default {@link Appearance} values, then
 * there should only be 1 {@link Appearance} object in memory. If one of the
 * {@link SlideElement}s changes a value of its {@link Appearance} property, the
 * default {@link Appearance} object is cloned and the newly requested value is
 * set on that new object.
 * </p>
 * 
 * <p>
 * Conversely, if a non-default {@link Property} object has some value set, then
 * that {@link Property} should check if it now equals the default
 * {@link Property}. If it does, then it should be destroyed and replaced with
 * the default.
 * </p>
 * 
 * <p>
 * There is likely some way to do all of this quasi-ref counting stuff within
 * the {@link Property} superclass and make life a lot simpler for any of the
 * subclasses, but it is currently not a priority
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
 * @author Hamilton Turner
 * 
 */
public abstract class Property implements EventListener, Cloneable {

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
	 * This should return a deep copy of the {@link Property} in preparation for
	 * changing some of the default {@link Property} values.
	 */
	public abstract Object clone();

	/**
	 * Requests the user interface {@link Section} for this {@link Property}.
	 * The {@link Property} can (and probably should) register for GUI events
	 * such as mouse clicks that originate from this {@link Section}. This
	 * allows each {@link Property} to create and react to it's own part of the
	 * user interface.
	 */
	public abstract Section getSection();
}
