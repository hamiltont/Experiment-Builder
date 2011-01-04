package edu.vanderbilt.psychology.model.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.lang.model.element.Element;
import javax.swing.JComponent;

import edu.vanderbilt.psychology.player.EventManager;

/**
 * An event reactor allows an {@link Element}
 * 
 * @author hamiltont
 * 
 */

// TODO - add in conditions. See issue #7
public class EventReactor implements EventListener {

	// ===================== CONTINUE HERE ============================
	// Add this event reactor to the current slide (inside of the mouse actions
	// action listener). Then test one experiment export to make sure it's all
	// working properly e.g. the reactor is being saved to the exp slide. Then
	// try to make the player controller listen for the next slide event and
	// automatically switch to the next slide. In this case, there is only one
	// slide so the player controller should recognize the end of the experiment
	// and react appropriately.
	public EventReactor(JComponent observed, final Event eventToFire) {
		observed.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				EventManager.getInstance().sendEvent(eventToFire);
			}
		});
	}

	@Override
	public void receiveEvent(Event e) {
		// TODO Auto-generated method stub

	}

}
