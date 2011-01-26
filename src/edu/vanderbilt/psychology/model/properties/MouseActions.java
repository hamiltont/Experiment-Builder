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
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.BuilderState;
import edu.vanderbilt.psychology.model.Slide;
import edu.vanderbilt.psychology.model.reactor.Action;
import edu.vanderbilt.psychology.model.reactor.Reactor;
import edu.vanderbilt.psychology.model.reactor.ActionType;
import edu.vanderbilt.psychology.model.reactor.Sleeper;

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

				// Build the Reactor
				SlideElement selection = SelectionManager.getInstance()
						.getRealSelection();
				Action ev1 = new Action(ActionType.TYPE_APPEARANCE_EVENTS,
						Appearance.ACTION_SHOW_BORDER, selection.getModel(),
						null);
				Action ev2 = new Action(ActionType.TYPE_SLEEP_EVENTS,
						Sleeper.ACTION_PAUSE_EXPERIMENT, selection.getModel(),
						null);

				Action ev3 = new Action(ActionType.TYPE_SLIDE_EVENTS,
						Slide.ACTION_ADVANCE_TO_NEXT_SLIDE, selection
								.getModel(), null);

				Reactor er = new Reactor(selection,
						Reactor.TRIGGER_ON_MOUSE_ENTER, ev1, ev2, ev3);

				// Add it to the current slide
				BuilderState.getInstance().getCurrentSlide()
						.addEventReactor(er);

			}
		}));
		return new Section("Mouse Actions", panel);
	}

	@Override
	public void receiveAction(Action e) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

}
