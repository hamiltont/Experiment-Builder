/**
 * 
 */
package edu.vanderbilt.psychology.model;

import java.util.ArrayList;
import java.util.List;

import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.elements.ModelElement;
import edu.vanderbilt.psychology.model.inputs.Input;
import edu.vanderbilt.psychology.model.properties.Property;

/**
 * {@link Slide}s do not directly keep track of any visual information. The GUI
 * keeps track of all {@link SlideElement}s and their associated
 * {@link Property}s. When an event occurs that will cause those GUI objects to
 * be destroyed, such as switching to the next slide or the application exiting,
 * the relevant {@link Property} information from those GUI objects is written
 * into the {@link Slide} object for storage. (Actually, a reference to
 * SlideElements are saved. Memory intensive, but it works for now)
 * 
 * <p>
 * A {@link Slide} can also define events that {@link Input}s and
 * {@link Property}s can listen for and react to. This allows, for example, the
 * mouse to be disabled when a {@link Slide} starts or data to be written to
 * file when a {@link Slide} completes
 * </p>
 * 
 * @author Hamilton Turner
 * 
 */
// TODO Add some visual info the Slide, particularly the z-ordering of the
// elements on screen (or add that to the Appearance element somehow)
public class Slide {
	List<ModelElement> elements_ = new ArrayList<ModelElement>();
	List<EventReactor> reactors_ = new ArrayList<EventReactor>();

	public void saveElement(ModelElement me) {
		elements_.add(me);
	}

	public List<ModelElement> getModelElements() {
		return elements_;
	}

}
