/**
 * 
 */
package edu.vanderbilt.psychology.model.elements;

import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.MutableInt;

/**
 * Not really useful for anything right now but allowing the
 * {@link SlideElement} superclass to enforce all of the subclasses supporting a
 * getModel() operation
 * 
 * {@link ModelElement}s are kind of weird right now because they are
 * essentially controller objects. They take a GUI element, parse it, and save
 * it's properties into themselves.
 * 
 * @author Hamilton Turner
 * 
 */
// TODO Does it really make sense for the model to have methods to generate the
// slide element (a gui component for the builder) and a JComponent (a gui
// element to be used in the player). Should I have a controller interface here?
public abstract class ModelElement {
	private Point location_;
	private Integer layer_;

	public final Point getLocation() {
		return location_;
	}

	public final Integer getLayer() {
		return layer_;
	}

	public final void addGuiProperties(int layer, Point location) {
		location_ = location;
		layer_ = layer;
	}

	/**
	 * Gets a {@link SlideElement} that is ready for use in the builder
	 * 
	 * @return
	 */
	public abstract SlideElement getInitializedSlideElement();

	/**
	 * Gets a {@link JComponent} that is ready for use in the player
	 * 
	 * @param outputLayer
	 *            An integer that represents the layer this {@link ModelElement}
	 *            would be in a {@link JLayeredPane}. This cannot be null, the
	 *            caller needs to minimally pass in "new MutableInt()"
	 * 
	 * @return The JComponent generated from this {@link ModelElement}
	 */
	public abstract JComponent getInitializedJComponent(MutableInt outputLayer);

}
