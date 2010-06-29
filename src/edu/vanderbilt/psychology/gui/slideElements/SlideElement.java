/**
 * 
 */
package edu.vanderbilt.psychology.gui.slideElements;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import net.java.swingfx.jdraggable.Draggable;
import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.model.elements.ModelElement;
import edu.vanderbilt.psychology.model.properties.Property;

/**
 * When created, a {@link SlideElement} is automatically entered into the
 * current Slide. Also, the {@link SelectionManager} will automatically become a
 * listener on any click elements that the {@link SlideElement} may receive, so
 * that it can automatically select that element
 * 
 * All subclasses of {@link SlideElement} are draggable, as {@link SlideElement}
 * implements {@link Draggable}.
 * 
 * TODO If a subclass implements the Resizable interface, then the
 * {@link SlideElement} superclass will also manage resizing the element, and
 * will inform the element to redraw itself when appropriate
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

// TODO Create Resizable interface, add reflection code to the constructor that
// checks to see if a class implements resizable and adds the appropriate
// listeners if it does

// TODO Find a way to force SlideElements to use the Prototype pattern for
// Propertys (even if someone else developed the SlideElements or Propertys).

// TODO Right now all subclasses must call setSize() and setBounds() in their
// ctor in order to show up. I wish I knew why this was or how to fix it.
// Perhaps I could add something to the SlideElement ctor and fix this problem
public abstract class SlideElement extends JPanel implements Draggable {

	public SlideElement() {
		super();

		SelectionManager.getInstance().add(this);
	}

	/**
	 * Given a {@link ModelElement} of the appropriate type, initialize this
	 * objects values with the values found in that {@link ModelElement}
	 * 
	 * @param model
	 * @return <b>this</b> after model values have been copied
	 */
	public abstract void initializeWithModel(ModelElement model);

	/**
	 * Get the Name of this Element. This will likely be used in the User
	 * interface and the logs, so return an appropriate string.
	 * 
	 * @return
	 */
	public abstract String getElementName();

	/**
	 * Needed to allow implementation of {@link Draggable}
	 */
	public final Component getComponent() {
		return this;
	}

	/**
	 * Should be called to get the {@link ModelElement} that represents a given
	 * {@link SlideElement}
	 * 
	 * @return
	 */
	public abstract ModelElement getModel();

	public abstract List<Property> getProperties();

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -1692942789307278424L;
}
