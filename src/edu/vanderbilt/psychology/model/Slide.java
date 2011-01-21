/**
 * 
 */
package edu.vanderbilt.psychology.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import com.sun.tools.javac.util.Pair;

import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.elements.ModelElement;
import edu.vanderbilt.psychology.model.events.EventReactor;
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
	Set<ModelElement> elements_ = new HashSet<ModelElement>();
	List<EventReactor> reactors_ = new ArrayList<EventReactor>();

	public void saveElement(ModelElement me) {
		elements_.add(me);
	}

	public void addEventReactor(EventReactor reactor) {
		reactors_.add(reactor);
	}

	public List<EventReactor> getEventReactors() {
		return reactors_;
	}

	public void setEventReactors(List<EventReactor> reactors) {
		reactors_ = reactors;
	}

	/**
	 * Given a {@link SlideElement}, this returns all of the
	 * {@link EventReactor}s that are listening for some type of event on that
	 * {@link SlideElement}
	 * 
	 * @param element
	 * @return
	 */
	public List<EventReactor> getReactorsReferencing(SlideElement element) {
		ArrayList<EventReactor> result = new ArrayList<EventReactor>(1);
		for (EventReactor er : reactors_)
			if (er.getSlideElement() == element)
				result.add(er);

		return result;
	}

	private List<EventReactor> getReactorsReferencing(ModelElement element) {
		ArrayList<EventReactor> result = new ArrayList<EventReactor>(1);
		for (EventReactor er : reactors_)
			if (er.getModelElement() == element)
				result.add(er);

		return result;
	}

	public void clearElements() {
		elements_.clear();
	}

	public Set<ModelElement> getModelElements() {
		return elements_;
	}

	/**
	 * Converts the internal model of elements into a series of
	 * {@link JComponent}s that can then be added to the player's GUI. As it
	 * builds the list of {@link JComponent}s, it also registers any triggers as
	 * specified by {@link EventReactor}s
	 * 
	 * @return A list of {@link JComponent}s that represent various
	 *         {@link ModelElement}s. Each list item is a {@link Pair}
	 *         containing the {@link JComponent} and an {@link Integer}
	 *         representing the layer it should be placed on in a
	 *         {@link JLayeredPane}
	 */
	public List<Pair<JComponent, Integer>> getGui() {
		ArrayList<Pair<JComponent, Integer>> components = new ArrayList<Pair<JComponent, Integer>>(
				elements_.size());

		Object[] elements = elements_.toArray();
		for (int i = 0; i < elements.length; i++) {

			MutableInt result = new MutableInt();
			JComponent output = ((ModelElement) elements[i])
					.getJComponent(result);

			// For each reactor that references this model element, add the
			// triggers to the component
			for (EventReactor reactor : getReactorsReferencing((ModelElement) elements[i]))
				reactor.addTriggerToJComponent(output);

			components.add(new Pair<JComponent, Integer>(output, result
					.getValue()));
		}

		return components;
	}
}
