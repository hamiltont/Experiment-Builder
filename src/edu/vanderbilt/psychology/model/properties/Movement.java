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
 * Defines how and when a {@link SlideElement} is in motion on the {@link Slide}
 * . This will likely be filled in to include entry/exit triggers, tweening
 * transformations, etc
 * 
 * 
 * @author Hamilton Turner
 * 
 */
public class Movement extends Property {
	private static Movement defaultMovement_;

	private Movement() {
		// setup default values
	}

	public static Movement getDefaultMovement() {
		if (defaultMovement_ == null)
			defaultMovement_ = new Movement();
		return defaultMovement_;
	}

	@Override
	public Object clone() {
		return new Movement();
	}

	@Override
	public Section getSection() {
		JPanel poo = new JPanel();
		poo.add(new JLabel("Hi there!!!"));
		return new Section("Movement", poo);
	}

	@Override
	public void receiveEvent(Event e) {
		// TODO Auto-generated method stub
		
	}
}
