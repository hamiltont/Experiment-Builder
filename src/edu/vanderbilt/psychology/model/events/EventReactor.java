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
	}

}
