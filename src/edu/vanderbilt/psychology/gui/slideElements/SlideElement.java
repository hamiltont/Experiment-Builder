/**
 * 
 */
package edu.vanderbilt.psychology.gui.slideElements;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.java.swingfx.jdraggable.Draggable;
import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.model.elements.ModelElement;
import edu.vanderbilt.psychology.model.properties.Property;

/**
 * When created, a SlideElement is automatically entered into the current Slide.
 * Also, the SelectionManager will automatically become a listener on any click
 * elements that the SlideElement may receive, so that it can automatically
 * select that element
 * 
 * All subclasses of SlideElement are draggable, as SlideElement implements
 * {@link Draggable}. If a subclass implements the Resizable interface, then
 * SlideElement will also manage resizing the element informing the element to
 * redraw itself when appropriate
 * 
 * {@link SlideElement}s contain {@link Property}s that they can act upon. Each
 * {@link Property} has default settings, and a {@link SlideElement} should hold
 * a pointer to the default {@link Property} unless it must change the default
 * values in some way, in order to prevent having multiple copies of a
 * {@link Property} that have the same values
 * 
 * @author Hamilton Turner
 * 
 */
// TODO - This does nothing right now, and all of the subclasses are actually
// copies of this code, with their specific type information added. This is
// because I really want anything extending this class to 1) automatically be
// added to the SelectionManager, and 2) be laid out properly. I don't
// immediately have a solution for the layout issue - I think one could easily
// be found, but right now it is more worth my time to keep implementing
// features and revisit this issue later

// TODO Create Resizable interface, add reflection code to the constructor that
// checks to see if a class implements resizable and adds the appropriate
// listeners if it does

// TODO Find a way to force SlideElements to use the Prototype pattern for
// Propertys (even if someone else developed the SlideElements or Propertys).

// TODO Right now all subclasses must call setSize() and setBounds() in their
// ctor in order to show up. I wish I knew why this was or how to fix it
public class SlideElement extends JPanel implements Draggable {
	List<Property> properties_ = new ArrayList<Property>();

	public SlideElement() {
		super();
		// superCalled_ = true;

		SelectionManager.getInstance().add(this);
	}

	/** Must be overridden */
	public String getElementName() {
		throw new UnsupportedOperationException(
				"Must override this method in a subclass!");
	}

	public Component getComponent() {
		return this;
	}

	public ModelElement getModel() {
		throw new UnsupportedOperationException(
				"Must override this method in a subclass!");
	}

	public List<Property> getProperties() {
		return properties_;
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -1692942789307278424L;
}
