/**
 * 
 */
package edu.vanderbilt.psychology.model.properties;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.vanderbilt.psychology.gui.sideBar.Section;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.Slide;
import edu.vanderbilt.psychology.model.events.Event;

/**
 * Defines how and when a {@link SlideElement} is visible on the {@link Slide}.
 * This will likely be filled in to include entry/exit triggers, alpha
 * transformations, etc
 * 
 * @author Hamilton Turner
 * 
 */
public class Appearance extends Property {

	public Appearance() {
		// TODO Implement
	}

	@Override
	public Section getSection() {
		JPanel poo = new JPanel();
		poo.add(new JLabel("Hi there!!!"));
		return new Section("Appearance", poo);
	}

	@Override
	public void receiveEvent(Event e) {
		// TODO Auto-generated method stub

	}
}
