package edu.vanderbilt.psychology.model.properties;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.gui.dialogs.DialogManager;
import edu.vanderbilt.psychology.gui.sideBar.Section;
import edu.vanderbilt.psychology.model.BuilderState;
import edu.vanderbilt.psychology.model.events.Event;
import edu.vanderbilt.psychology.model.events.EventFactory;
import edu.vanderbilt.psychology.model.events.EventReactor;
import edu.vanderbilt.psychology.model.events.EventType;

public class MouseActions extends Property {

	@SuppressWarnings("serial")
	@Override
	public Section getSection() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("On Mouse Click do "));
		panel.add(new JButton(new AbstractAction("Select Action") {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO do something with the dialog return value
				DialogManager.showActionDialog();
				
				// Build the EventReactor
				JPanel selection = SelectionManager.getInstance().getRealSelection();
				Event ev = EventFactory.buildEvent (EventType.TYPE_SLIDE_EVENTS);
				EventReactor er = new EventReactor(selection, ev);
				
				// Add it to the current slide
				BuilderState.getInstance().getCurrentSlide().addEventReactor(er);
				
			}
		}));
		return new Section("Mouse Actions", panel);
	}

	@Override
	public void receiveEvent(Event e) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

}
