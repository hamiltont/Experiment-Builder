/**
 * 
 */
package edu.vanderbilt.psychology.model.properties;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.vanderbilt.psychology.gui.sideBar.Section;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.Slide;
import edu.vanderbilt.psychology.model.reactor.Action;

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

	public Movement() {
		// TODO Setup default values
	}

	@Override
	public Section getSection() {
		JPanel poo = new JPanel();
		poo.add(new JLabel("Hi there!!!"));
		return new Section("Movement", poo);
	}

	@Override
	public void receiveAction(Action e) {
		// TODO Auto-generated method stub
	}
}
