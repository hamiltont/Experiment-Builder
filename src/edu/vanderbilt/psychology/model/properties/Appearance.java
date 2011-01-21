/**
 * 
 */
package edu.vanderbilt.psychology.model.properties;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

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

	public static final int ACTION_SHOW_BORDER = 0;
	public static final int ACTION_HIDE_BORDER = 1;

	public Appearance() {
	}

	@Override
	public Section getSection() {
		JPanel poo = new JPanel();
		poo.add(new JLabel("Hi there!!!"));
		return new Section("Appearance", poo);
	}

	@Override
	public void receiveEvent(Event e) {
		switch (e.getActionCode()) {
		case ACTION_SHOW_BORDER:
			e.getSource().setBorder(
					BorderFactory.createLineBorder(Color.black, 5));
			e.getSource().revalidate();
			break;
		case ACTION_HIDE_BORDER:
			e.getSource().setBorder(BorderFactory.createEmptyBorder());
			e.getSource().revalidate();
			break;
		
		default:
			throw new IllegalArgumentException("Unknown action code!");
		}
	}
}
