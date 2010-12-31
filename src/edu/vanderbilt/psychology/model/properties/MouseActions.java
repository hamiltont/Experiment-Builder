package edu.vanderbilt.psychology.model.properties;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import edu.vanderbilt.psychology.gui.dialogs.DialogManager;
import edu.vanderbilt.psychology.gui.sideBar.Section;
import edu.vanderbilt.psychology.model.events.Event;

public class MouseActions extends Property {

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	@SuppressWarnings("serial")
	@Override
	public Section getSection() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("On Mouse Click do "));
		panel.add(new JButton(new AbstractAction("Select Action") {

			@Override
			public void actionPerformed(ActionEvent e) {
				DialogManager.showActionDialog();
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
