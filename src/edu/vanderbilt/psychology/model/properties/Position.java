/**
 * 
 */
package edu.vanderbilt.psychology.model.properties;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import edu.vanderbilt.psychology.gui.sideBar.Section;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.Slide;

/**
 * Defines where and when a {@link SlideElement} is positioned on the
 * {@link Slide}.
 * 
 * There are currently two major operations in mind here. The first,
 * "random position", indicates that an object can appear in one of multiple
 * locations. The second, "shuffle", indicates that multiple objects share a
 * group of locations and are randomly shuffled amongst the locations. An
 * example of "shuffle" would include 3 cards that must all be shown on the
 * screen, and each card has an equal chance to occupy each of the 3 open
 * locations on the screen.
 * 
 * @note The "random position" operation can be performed using the same code as
 *       the "shuffle" operation, if there are invisible containers in the other
 *       "shuffle" positions
 * 
 * @author Hamilton Turner
 * 
 */
// NOTE possible unit test for a plugin - checking the hell out of clone() and
// getDefault...()
public class Position extends Property {
	private static Position defaultPosition_;
	private static final String sectionTitle_ = "Position";
	private static final String[] options = {"Move To", "Choose Between"}; 
	
	private JPanel section_;

	private Position() {
		section_ = new JPanel();
		section_.setLayout(new BoxLayout(section_, BoxLayout.PAGE_AXIS));
		
		JButton addTriggerBtn = new JButton("Add Trigger");
		addTriggerBtn.setToolTipText("Add Trigger");
		addTriggerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		section_.add(addTriggerBtn);
		
		JComboBox type = new JComboBox(options);
		type.setAlignmentX(Component.CENTER_ALIGNMENT);
		section_.add(type);
		
		JButton chooseLocBtn = new JButton("Choose Location");
		chooseLocBtn.setToolTipText("Choose Location");
		chooseLocBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		section_.add(chooseLocBtn);
		
	}

	public static Position getDefaultPosition() {
		if (defaultPosition_ == null)
			defaultPosition_ = new Position();
		return defaultPosition_;
	}

	@Override
	public Object clone() {
		return new Position();
	}

	@Override
	public Section getSection() {
		return new Section(sectionTitle_, section_);
	}
}
