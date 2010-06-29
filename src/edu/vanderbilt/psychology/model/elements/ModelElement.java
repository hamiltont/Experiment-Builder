/**
 * 
 */
package edu.vanderbilt.psychology.model.elements;

import java.awt.Point;

import edu.vanderbilt.psychology.gui.slideElements.SlideElement;

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
	
	public abstract SlideElement getInitializedSlideElement();
	
	
}
